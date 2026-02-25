package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.FileUtils;
import br.com.pastelaria.domain.utils.TimeUtils;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.EnumSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Gossip implements iCommand {
    private static final String IMG = "https://images-ext-1.discordapp.net/external/uZ76GNcLhBArNnUUNHiwfddRm7WEb2-MGe02TETNNQI/https/media.tenor.com/bGvmw1RTg_QAAAPo/pastel-olha.mp4";

    @Override
    public void execute(iEventBot event) {
        var slashEvent = event.getEvent();
        var guild = event.getGuild();

        String sub = slashEvent.getSubcommandName();

        Role role = slashEvent.getOption("role") != null
                ? slashEvent.getOption("role").getAsRole()
                : guild.getRolesByName("Estagiário", true).get(0);

        AtomicInteger secondsAtomicInteger = new AtomicInteger(28800);

        if(sub != null && !sub.equalsIgnoreCase("default")) {
            int time = slashEvent.getOption("time").getAsInt();
            int seconds = TimeUtils.toSeconds(sub, time);

            secondsAtomicInteger.set(seconds);
        }

        guild.createTextChannel("Hora do pastel!")
                .addMemberPermissionOverride(
                        slashEvent.getJDA().getSelfUser().getIdLong(),
                        EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND),
                        null
                )
                .addRolePermissionOverride(
                        role.getIdLong(),
                        EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND),
                        EnumSet.noneOf(Permission.class)
                )
                .addRolePermissionOverride(
                        guild.getPublicRole().getIdLong(),
                        null,
                        EnumSet.of(Permission.VIEW_CHANNEL)
                )
                .queue(channel -> {
            System.out.println("Canal de voz criado: " + channel.getName());

//            channel.sendMessage(IMG).queue();

            String message = String.format("Hora da pastel!!\n⚠\uFE0F Canal será apagado em %s", TimeUtils.formatSeconds(secondsAtomicInteger.get()));

            channel.sendMessage(message).queue();

            event.replyWithQueue("✅ Chat da hora do pastel criada!").queue(msg -> {
                msg.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
            });

            this.executeScheduler(channel, secondsAtomicInteger.get());
        });
    }

    private void executeScheduler(MessageChannel channel, long seconds) {
        try( ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            scheduler.schedule(() -> {
                channel.delete().queue();
            }, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
