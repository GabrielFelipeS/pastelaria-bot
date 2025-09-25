package br.com.pastelaria.domain.service;

import br.com.pastelaria.domain.interfaces.iEventBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;

import java.util.List;

public class EventBot implements iEventBot {
    private SlashCommandInteractionEvent event;
    private List<String> parts;

    public EventBot(SlashCommandInteractionEvent event) {
        this.event = event;
        this.parts = List.of(this.event.getCommandString().split(" "));
        System.out.println(this.parts);
    }

    public List<String> getParams() {
        return parts.subList(1, parts.size());
    }

    public String getParam(int index) {
        return parts.get(index);
    }

    public List<String> getParams(int fromIndex) {
        return parts.subList(fromIndex, parts.size());
    }

    public List<String> getParams(int fromIndex, int toIndex) {
        return parts.subList(fromIndex, toIndex);
    }

    public boolean existParam(int index) {
        int length = parts.size();
        return index >= 0 && index < length;
    }

    public boolean hasParams() {
        return this.parts.size() > 1;
    }

    public String getCommand() {
        return this.event.getName();
    }

    public List<String> getParts() {
        return parts;
    }

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public Guild getGuild() {
        return event.getGuild();
    }

    @Override
    public SlashCommandInteractionEvent getEvent() {
        return this.event;
    }

    @Override
    public User getUser() {
        return this.event.getUser();
    }

    @Override
    public void reply(String message) {
        event.reply(message).queue();
    }

    @Override
    public ReplyCallbackAction replyWithQueue(String message) {
        return event.reply(message);
    }

    @Override
    public void reply(String message, FileUpload... fileUploads) {
        event.reply(message).addFiles(fileUploads).queue();
    }
}
