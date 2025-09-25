package br.com.pastelaria;

import br.com.pastelaria.domain.context.CommandsFactory;
import br.com.pastelaria.domain.context.CommandsStrategy;
import br.com.pastelaria.domain.models.CommandContent;
import br.com.pastelaria.domain.service.EventBot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bot extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        JDA jda = JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Bot())
                .build();

        Bot.jda(jda);
    }

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

        staticLog(event.getUser(), eventBot.getCommand());

        command.execute(eventBot);
    }

    private static void staticLog(User user, String command) {
        logger.debug("{} - executou {}", user.getName(), command);
    }
}