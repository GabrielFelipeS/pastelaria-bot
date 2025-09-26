package br.com.pastelaria.domain.interfaces;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import net.dv8tion.jda.api.utils.FileUpload;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public interface iEventBot {

    default boolean firstParamIsNumber()  {
        if(this.hasNoParams()) return false;

        return this.getParam(1).matches("^[0-9]+$");
    }

    default boolean falsePredictParamAsInt(int index, Predicate<Integer> predicate) {
        return this.predictParamAsInt(index, predicate.negate());
    }

    default boolean predictParamAsInt(int index, Predicate<Integer> predicate) {
        int param = this.getParamAsInt(index);

        return predicate.test(param);
    }

    List<String> getParams();

    String getParam(int index);

    List<String> getParams(int fromIndex);

    List<String> getParams(int fromIndex, int toIndex);

    default int getParamAsInt(int index) {
        if(this.notExistParam(index)) return 0;

        return Integer.parseInt(this.getParam(index));
    }

    boolean existParam(int index);

    default boolean notExistParam(int index) {
        return !this.existParam(index);
    }

    boolean hasParams();

    default boolean hasNoParams() {
        return !this.hasParams();
    }

    String getCommand();

    List<String> getParts();

    MessageChannel getChannel();

    Guild getGuild();

    User getUser();

    SlashCommandInteractionEvent getEvent();

    void reply(String message);
    void reply(String message, FileUpload... fileUploads);
    ReplyCallbackAction replyWithQueue(String message);

    default void replyInfo(String message) {
        replyWithQueue(message).queue(msg -> {
            msg.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
        });
    }

    default void replyWarning(String message) {
        replyInfo("⚠\uFE0F " + message);
    }

    default void replySuccess(String message) {
        replyInfo("✅ " + message);
    }
}
