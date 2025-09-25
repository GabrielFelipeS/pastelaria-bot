package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;


public class Reload implements iCommand {
    @Override
    public void execute(iEventBot event) {
        event.getGuild().updateCommands().queue();

        event.reply("Recarregado!");
    }
}
