package br.com.pastelaria.domain.commands;


import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler implements iCommand {
    @Override
    public void execute(iEventBot event) {
        MessageChannel channel = event.getChannel();

        int seconds = event.getEvent().getOption("seconds").getAsInt();

        String message = String.format("✅ Comando agendado para executar em %d segundos", seconds);

        event.reply(message);

        try( ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            scheduler.schedule(() -> {
                String schedulerCommand = event.getEvent().getOption("text").getAsString();

                channel.sendMessage(schedulerCommand).queue();
            }, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
