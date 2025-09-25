package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.FileUtils;
import br.com.pastelaria.domain.utils.TimeUtils;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Gossip implements iCommand {
    private static final String IMG = "https://images-ext-1.discordapp.net/external/uZ76GNcLhBArNnUUNHiwfddRm7WEb2-MGe02TETNNQI/https/media.tenor.com/bGvmw1RTg_QAAAPo/pastel-olha.mp4";

    @Override
    public void execute(iEventBot event) {
        var slashEvent = event.getEvent();
        String sub = slashEvent.getSubcommandName();

        AtomicInteger secondsAtomicInteger = new AtomicInteger(3600);

        if(sub != null) {
            int time = slashEvent.getOption("time").getAsInt();
            int seconds = TimeUtils.toSeconds(sub, time);

            secondsAtomicInteger.set(seconds);
        }

        event.getGuild().createTextChannel("Hora do pastel!")
                .queue(channel -> {
            System.out.println("Canal de voz criado: " + channel.getName());

            channel.sendMessage("Hora da pastel!!").queue();
            channel.sendMessage(IMG).queue();

            String message = String.format("⚠\uFE0F Canal será apagado em %s", TimeUtils.formatSeconds(secondsAtomicInteger.get()));

            channel.sendMessage(message).queue();

            event.reply("✅ Chat da hora do pastel criada!");

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
