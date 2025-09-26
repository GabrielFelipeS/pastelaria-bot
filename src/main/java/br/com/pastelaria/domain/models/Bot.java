package br.com.pastelaria.domain.models;

import br.com.pastelaria.domain.context.CommandsFactory;
import br.com.pastelaria.domain.context.CommandsStrategy;
import br.com.pastelaria.domain.service.EventBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void jda(JDA jda) {
        List<CommandData> commands = new ArrayList<>(Collections.emptyList());

        for(CommandContent c : CommandsFactory.getCommands()) {
            commands.add(c.getSlashCommandData() );
        }

        jda.updateCommands().addCommands(commands).queue();
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        EventBot eventBot = new EventBot(event);

        CommandsStrategy commandsStrategy = new CommandsStrategy();

        var command = commandsStrategy.getCommand(eventBot.getCommand());

       try {
           command.execute(eventBot);
           staticLog(eventBot, eventBot.getCommand());
       } catch (Exception e) {
           this.exceptionHandler(eventBot, e);
       }
    }

    private static void staticLog(EventBot event, String command) {
        logger.debug("{} - executou {} - {}", event.getUser().getName(), command, event.getEvent().getOptions());
        System.err.format("%s - executou %s - %s", event.getUser().getName(), command, event.getEvent().getOptions());
    }

    private void exceptionHandler(EventBot event, Exception exception) {
        logger.debug("Exception: {} - executou {} - {} - {}", event.getUser().getName(), event.getCommand(), event.getEvent().getOptions(), exception.getMessage());
        System.err.format("Exception: %s - executou %s - %s - %s", event.getUser().getName(), event.getCommand(), event.getEvent().getOptions(), exception.getMessage());

        if(exception instanceof ArithmeticException) {
            event.replyWarning("O valor passado ultrapassa o limite de um inteiro");
            return;
        }
    }
}
