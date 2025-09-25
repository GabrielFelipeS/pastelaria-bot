package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.FileUtils;


public class Done implements iCommand {
    @Override
    public void execute(iEventBot event) {
        event.reply("", FileUtils.getFile("assets/deu.png"));
    }
}
