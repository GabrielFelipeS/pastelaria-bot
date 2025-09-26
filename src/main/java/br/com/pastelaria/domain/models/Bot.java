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

        staticLog(eventBot, eventBot.getCommand());

       try {
           command.execute(eventBot);
       } catch (Exception e) {
           this.exceptionHandler(eventBot, e);
       }
    }

    private static void staticLog(EventBot event, String command) {
        logger.debug("{} - executou {} - {}", event.getUser().getName(), command, event.getEvent().getOptions());
        System.out.format("%s - executou %s - %s", event.getUser().getName(), command, event.getEvent().getOptions());
    }

    private void exceptionHandler(EventBot eventBot,Exception exception) {
        if(exception instanceof ArithmeticException) {
            eventBot.replyWarning("O valor passado ultrapassa o limite de um inteiro");
            return;
        }
    }
}
