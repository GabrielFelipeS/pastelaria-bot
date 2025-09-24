package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.model.EventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Write implements iCommand {
    @Override
    public void execute(EventBot event) {
        MessageChannel channel = event.getChannel();

        String message = String.join(" ", event.getParams());
        channel.sendMessage(message).queue();
    }
}
