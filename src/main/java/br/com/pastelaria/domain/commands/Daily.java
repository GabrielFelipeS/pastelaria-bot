package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.TimeUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Daily implements iCommand {
    private HashMap<String, Instant> hash = new HashMap<>();

    @Override
    public void execute(iEventBot event) {
        var slashEvent = event.getEvent();
        String guildId = event.getGuild().getId();
        String sub = slashEvent.getSubcommandName();

        Instant date = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")
                .withZone(ZoneId.of("America/Sao_Paulo"));

        String message;

        if(sub == null || sub.equals("start")) {
            message = String.format("A Daily iniciou às: %s", formatter.format(date));
            this.hash.put(guildId, date);
        } else {
            var instant = this.hash.getOrDefault(guildId, Instant.now().minusSeconds(1));
            Duration duration = Duration.between(instant, date);

            message = String.format("A Daily finalizou às: %s\nDurou: %s",
                    formatter.format(date),
                    TimeUtils.formatSeconds(duration.getSeconds())
            );

            this.hash.remove(guildId);
        }

        event.reply(message);
    }
}
