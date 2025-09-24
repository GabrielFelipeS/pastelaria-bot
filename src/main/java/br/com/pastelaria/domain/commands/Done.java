package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.service.EventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;


public class Done implements iCommand {
    private static final String already_done_image = "https://media.discordapp.net/attachments/1420084043847827676/1420109496612819105/deu.png?ex=68d43388&is=68d2e208&hm=6094bf5ec7833cba1d77b3029d64b4371cafdeeb6cf82a5b801af6bb4e89ed3a&=&format=webp&quality=lossless";
    @Override
    public void execute(EventBot event) {
        event.deleteCommandMessage();

        MessageChannel channel = event.getChannel();

        channel.sendMessage(already_done_image).queue();
    }
}
