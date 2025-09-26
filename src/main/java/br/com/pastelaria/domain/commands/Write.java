package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

import java.util.concurrent.TimeUnit;

public class Write implements iCommand {
    @Override
    public void execute(iEventBot event) {
        MessageChannel channel = event.getChannel();

        String message = "";
        int number = 1;

        if(event.firstParamIsNumber()) {
            boolean noAsInt = event.falsePredictParamAsInt(1, (param) -> param > 0 && param <= 5);

            if(noAsInt) {
                channel.sendMessage("⚠\uFE0F O valor númerico deve estar entre 0 e 50!").queue(msg -> {
                    msg.delete().queueAfter(5, TimeUnit.SECONDS);
                });

                return;
            }

            number = event.getParamAsInt(1);
            message = String.join(" ", event.getParams(2));
        } else {
            message = String.join(" ", event.getParams());
        }

        for(int i = 1; i <= number; i++) {
            channel.sendMessage(message).queue();
        }
    }
}
