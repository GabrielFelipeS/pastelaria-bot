package br.com.pastelaria.domain.context;

import br.com.pastelaria.domain.commands.*;
import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.models.CommandContent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.ArrayList;
import java.util.List;

public class CommandsFactory {
    private static final List<CommandContent> commandsContent = new ArrayList<CommandContent>();

    static {
        add(
            new Clear(),
            Commands.slash("clear", "Apaga as 100 últimas mensagens ou as X últimas mensagens")
                    .addOption(OptionType.INTEGER, "quantity", "Quantidade de mensagens para apagar", false)
        );

        add(
            new Update(),
            Commands.slash("update", "Atualiza os comandos")
                    .addOption(OptionType.STRING,"commands", "Atualiza o comando definido", true)
        );
        add(
            new Scheduler(),
            Commands.slash("scheduler", "Executa um agendamento para X segundos")
                    .addOption(OptionType.INTEGER, "seconds", "Quantidade de segundos para agendamento", false)
        );

        add(
            new Gossip(),
            Commands.slash("gossip", "Cria um canal de texto que é apagado automaticamente em 1 hora ou em X segundos, caso definitdo")
                    .addSubcommands(
                            new SubcommandData("seconds", "Cria um canal em minutos")
                                    .addOption(OptionType.INTEGER, "time", "Tempo para apagar o canal", true),
                            new SubcommandData("minutes", "Cria um canal em minutos")
                                    .addOption(OptionType.INTEGER, "time", "Tempo para apagar o canal", true),
                            new SubcommandData("hours", "Cria um canal em minutos")
                                    .addOption(OptionType.INTEGER, "time", "Tempo para apagar o canal", true)
                    )
        );

        add("write", "Manda a mensagem em seguida do comando uma vez ou pela quantidade determinada de vezes", new Write());
        add("done", "Já deu né", new Done());
        add("help", "Mostra os comandos disponiveis e suas descrições", new Done());
        add("pastel", "Olha o pastellll!!!", new Pastel());
        add("ping", "Verifica se o bot está online", new Ping());
        add(
            new Daily(),
            Commands.slash("daily", "Incio da daily")
                    .addSubcommands(
                            new SubcommandData("start", "Inicio da daily"),
                            new SubcommandData("end", "Fim da daily")
                    )
        );
        add("reload", "Recarrega os comandos", new Reload());
        add(new Sextou(), Commands.slash("sextou", "Sextou")
                .addOption(OptionType.INTEGER, "index", "Index da imagem", false)
        );
    }

    public static List<CommandContent> getCommands() {
        return commandsContent;
    }

    private static void add(String commandName, String description, iCommand command) {
        var newCommand = new CommandContent(commandName, description, command);
        commandsContent.add(newCommand);
    }

    private static void add(iCommand command, SlashCommandData commands) {
        var newCommand = new CommandContent(commands, command);
        commandsContent.add(newCommand);
    }
}
