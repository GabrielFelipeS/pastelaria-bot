package br.com.pastelaria;

import br.com.pastelaria.domain.context.CommandsStrategy;
import br.com.pastelaria.domain.model.EventBot;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("DISCORD_TOKEN");

        JDABuilder.createDefault(token,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new Bot())
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] parts = event.getMessage().getContentRaw().split(" ");

        EventBot eventBot = new EventBot(parts, event);
        CommandsStrategy commandsStrategy = new CommandsStrategy();

        var command = commandsStrategy.getCommand(eventBot.getCommand());

        command.execute(eventBot);
    }
}