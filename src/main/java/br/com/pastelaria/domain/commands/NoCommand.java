package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;

public class NoCommand implements iCommand {
    @Override
    public void execute(iEventBot event) {}
}
