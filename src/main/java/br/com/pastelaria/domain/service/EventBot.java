package br.com.pastelaria.domain.service;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.function.Predicate;

public class EventBot {
    private MessageReceivedEvent event;
    private List<String> parts;

    public EventBot(MessageReceivedEvent event) {
        String[] parts = event.getMessage().getContentRaw().split(" ");

        this.event = event;
        this.parts = List.of(parts);
    }

    public void deleteCommandMessage() {
       this.event.getMessage().delete().queue();
    }

    public boolean firstParamIsNumber() {
        if(this.hasNoParams()) return false;

        return this.getParam(1).matches("^[0-9]+$");
    }

    public boolean falsePredictParamAsInt(int index, Predicate<Integer> predicate) {
        return this.predictParamAsInt(index, predicate.negate());
    }

    public boolean predictParamAsInt(int index, Predicate<Integer> predicate) {
        int param = this.getParamAsInt(index);

        return predicate.test(param);
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

    public int getParamAsInt(int index) {
        if(this.notExistParam(index)) return 0;

        return Integer.parseInt(this.getParam(index));
    }

    public boolean existParam(int index) {
        int length = parts.size();
        return index >= 0 && index < length;
    }

    public boolean notExistParam(int index) {
        return !this.existParam(index);
    }

    public boolean hasParams() {
        return this.parts.size() > 1;
    }

    public boolean hasNoParams() {
        return !this.hasParams();
    }

    public String getCommand() {
        return parts.getFirst().toLowerCase();
    }

    public List<String> getParts() {
        return parts;
    }

    public MessageChannel getChannel() {
        return event.getChannel();
    }

    public MessageReceivedEvent getEvent() {
        return event;
    }
}
