package com.bence.mate.utils;

import com.bence.mate.models.Message;

import java.time.LocalTime;

import static java.lang.String.format;

public class Messages {

    public static final String WELCOME_MESSAGE = "Welcome to Websocket Chat";

    public static String personlize(String message, String... args){
        return format(message, (Object) args);
    }

    public static Message objectify(String content, String... args){
        return objectify(content, "Bot", LocalTime.now().toString(), args);
    }

    public static Message objectify(String content, String sender, String... args){
        return objectify(content, sender, LocalTime.now().toString(), args);
    }

    public static Message objectify(String content, String sender, String received, String... args){
        return new Message(personlize(content, args), sender, received);
    }
}
