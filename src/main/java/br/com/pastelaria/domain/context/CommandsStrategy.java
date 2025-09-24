package br.com.pastelaria.domain.context;

import br.com.pastelaria.domain.commands.*;
import br.com.pastelaria.domain.interfaces.iCommand;

import java.util.HashMap;

public class CommandsStrategy {
    private final HashMap<String, iCommand> commands;
    private final iCommand defaultCommand = new NoCommand();

    public CommandsStrategy() {
        this.commands = new HashMap<>();
        this.commands.put("!clear", new Clear());
        this.commands.put("!write", new Write());
        this.commands.put("!scheduler", new Scheduler());
        this.commands.put("!gossip", new Gossip());
        this.commands.put("!done", new Done());
    }

    public iCommand getCommand(String command) {
        return this.commands.getOrDefault(command, defaultCommand);
    }
}
