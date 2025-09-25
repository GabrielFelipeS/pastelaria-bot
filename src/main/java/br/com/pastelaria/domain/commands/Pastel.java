package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Pastel implements iCommand {
    private static final String IMG = "https://images-ext-1.discordapp.net/external/uZ76GNcLhBArNnUUNHiwfddRm7WEb2-MGe02TETNNQI/https/media.tenor.com/bGvmw1RTg_QAAAPo/pastel-olha.mp4";

    @Override
    public void execute(iEventBot event) {
        MessageChannel channel = event.getChannel();

        channel.sendMessage("Hora da pastel!!").queue();
        channel.sendMessage(IMG).queue();
    }
}
