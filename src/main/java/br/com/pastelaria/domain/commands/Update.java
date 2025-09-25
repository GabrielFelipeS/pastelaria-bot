package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.context.CommandsFactory;
import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.models.CommandContent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Update implements iCommand {
    @Override
    public void execute(iEventBot event) {
        if(!event.getUser().getName().equalsIgnoreCase("kaizen_gab")) {
            event.reply("⚠\uFE0F Você não tem permissão para executar esse comando");
            return;
        }

        var commands = Objects.requireNonNull(event.getEvent().getOption("commands"), () -> {
            String message = "⚠\uFE0F Erro deveria ser passado ao menos um comando";

            event.reply(message);

            return message;
        });

        List<String> params = Stream.of(
                commands.getAsString().split(" ")
        ).map(String::trim).toList();

        CommandsFactory.getCommands()
                .stream()
                .filter(c -> params.contains(c.commandName()))
                .forEach(c -> {
                    event.getGuild().upsertCommand(c.commandName(), c.describe()).queue();
                });

        event.getGuild().updateCommands().queue();

        event.reply("✅ Comando atualizado!");
    }
}
