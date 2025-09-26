package br.com.pastelaria.domain.models;

import br.com.pastelaria.domain.interfaces.iCommand;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandContent {
    private final SlashCommandData slashCommand;
    private final iCommand command;

    public CommandContent(String commandName, String description, iCommand command) {
        this(Commands.slash(commandName, description), command);
    }

    public CommandContent(SlashCommandData slashCommandData, iCommand command) {
        this.slashCommand = slashCommandData;
        this.command = command;
    }

    public SlashCommandData getSlashCommandData() {
        return this.slashCommand;
    }

    public String commandName() {
        return slashCommand.getName();
    }

    public String describe() {
        return slashCommand.getDescription();
    }

    public iCommand command() {
        return command;
    }
}
