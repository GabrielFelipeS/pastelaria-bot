package br.com.pastelaria.domain.context;

import br.com.pastelaria.domain.commands.*;
import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.models.CommandContent;

import java.util.HashMap;

public class CommandsStrategy {
    private final HashMap<String, iCommand> commands;
    private final iCommand defaultCommand = new NoCommand();

    public CommandsStrategy() {
        this.commands = new HashMap<>();

        for(CommandContent c : CommandsFactory.getCommands()) {
            this.commands.put(c.commandName(), c.command());
        }
    }

    public iCommand getCommand(String command) {
        return this.commands.getOrDefault(command, defaultCommand);
    }
}
