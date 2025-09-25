package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.TimeUnit;

public class Clear implements iCommand {
    @Override
    public void execute(iEventBot event) {
        MessageChannel channel = event.getChannel();

        var option = event.getEvent().getOption("quantity");

        int amount = option != null? option.getAsInt() : 10;

        if(amount < 1 || amount > 100) {
            event.reply("⚠\uFE0F Você tem que passar um valor entre 1 e 100");
            return;
        }

        channel.getHistory().retrievePast(amount).queue(messages -> {
            channel.purgeMessages(messages);

            event.replyWithQueue("✅ Apaguei " + messages.size() + " mensagens!").queue(msg -> {
                msg.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
            });
        });
    }
}
