package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.FileUtils;
import net.dv8tion.jda.api.entities.Message;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Sextou implements iCommand {
    private static final List<String> list = List.of(
            "assets/sextou/trabalho-sbado.webp",
            "assets/sextou/o-que-for-beber.jpg",
            "assets/sextou/sextou-papai.png"
    );

    @Override
    public void execute(iEventBot event) {
        var indexOption = event.getEvent().getOption("index");

        int size = list.size();
        int index = ThreadLocalRandom.current().nextInt(size);

        if(indexOption != null) {
            index = indexOption.getAsInt();
        }

        if(index < 0 || index >= size) {
            event.replyWarning("Você deve inserir um index entre 0 e " +  size);
            return;
        }

        String url =  list.get(index);

        event.reply("", FileUtils.getFile(url));
    }
}
