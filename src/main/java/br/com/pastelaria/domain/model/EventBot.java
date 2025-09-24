package br.com.pastelaria.domain.model;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class EventBot {
    private List<String> parts;
    private MessageReceivedEvent event;

    public EventBot(MessageReceivedEvent event) {
        String[] parts = event.getMessage().getContentRaw().split(" ");

        this.event = event;
        this.parts = List.of(parts);
    }

    public boolean hasParams() {
        return this.parts.size() > 1;
    }

    public String getCommand() {
        return parts.getFirst().toLowerCase();
    }

    public List<String> getParts() {
        return parts;
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

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
