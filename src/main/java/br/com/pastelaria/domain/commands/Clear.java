package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.model.EventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.TimeUnit;

public class Clear implements iCommand {
    @Override
    public void execute(EventBot event) {
        MessageChannel channel = event.getChannel();

        int amount = event.hasParams()? Integer.parseInt(event.getParam(1)) : 100;

        channel.getHistory().retrievePast(amount).queue(messages -> {
            channel.purgeMessages(messages);
            channel.sendMessage("✅ Apaguei " + messages.size() + " mensagens!").queue(msg -> {
                msg.delete().queueAfter(5, TimeUnit.SECONDS);
            });
        });
    }
}
