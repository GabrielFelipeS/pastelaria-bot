package br.com.pastelaria.domain.interfaces;



@FunctionalInterface
public interface iCommand {
    void execute(iEventBot event);
}