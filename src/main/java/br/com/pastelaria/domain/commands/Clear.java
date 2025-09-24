package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.service.EventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.TimeUnit;

public class Clear implements iCommand {
    @Override
    public void execute(EventBot event) {
        event.deleteCommandMessage();

        MessageChannel channel = event.getChannel();

        int amount = event.hasParams()? event.getParamAsInt(1) : 100;

        channel.getHistory().retrievePast(amount).queue(messages -> {
            channel.purgeMessages(messages);
            channel.sendMessage("✅ Apaguei " + messages.size() + " mensagens!").queue(msg -> {
                msg.delete().queueAfter(5, TimeUnit.SECONDS);
            });
        });
    }
}
