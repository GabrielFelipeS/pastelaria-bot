package br.com.pastelaria.domain.commands;

import br.com.pastelaria.domain.interfaces.iCommand;
import br.com.pastelaria.domain.interfaces.iEventBot;
import br.com.pastelaria.domain.utils.FileUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class Sextou implements iCommand {
    @Override
    public void execute(iEventBot event) {
        List<String> list = List.of(
                "assets/trabalho-sbado.webp",
                "assets/o-que-for-beber.jpg"
        );

        int randomIndex = ThreadLocalRandom.current().nextInt(list.size());
        String url =  list.get(randomIndex);

        event.reply("", FileUtils.getFile(url));
    }
}
