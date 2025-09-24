package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.service.EventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Gossip implements iCommand {
    @Override
    public void execute(EventBot event) {
        event.deleteCommandMessage();

        MessageChannel channel = event.getChannel();

        int seconds = 3600;

        StringBuilder stringBuilder = new StringBuilder("Hora da fofoca!!");

        if(event.hasParams()) {
            seconds = Integer.parseInt(event.getParam(1));
            String addMessage = String.format(" Mensagens apagadas em %d segundos", seconds);

            stringBuilder.append(addMessage);
        }

        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.set(seconds);

        channel.sendMessage(stringBuilder.toString()).queue(msg -> {
            msg.delete().queueAfter(atomicInteger.get(), TimeUnit.SECONDS);
        });

        this.executeScheduler(channel, seconds);
    }

    private void executeScheduler(MessageChannel channel, long seconds) {
        try( ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1)) {
            scheduler.schedule(() -> {
                channel.getHistory().retrievePast(100).queue(messages -> {
                    channel.purgeMessages(messages);
                    channel.sendMessage("✅ Apaguei " + messages.size() + " mensagens!").queue(msg -> {
                        msg.delete().queueAfter(5, TimeUnit.SECONDS);
                    });
                });
            }, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
