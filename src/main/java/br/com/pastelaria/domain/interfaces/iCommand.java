package br.com.pastelaria.domain.interfaces;


import br.com.pastelaria.domain.service.EventBot;

@FunctionalInterface
public interface iCommand {
    void execute(EventBot event);
}