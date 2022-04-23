package com.bence.mate.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

import javax.websocket.EncodeException;
import java.util.function.Function;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Function<String, Room> {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<Session> sessions = new ArrayList<>();

    @Override
    public Room apply(String name) {
        return new Room(name);
    }

    public Room(String name) {
        this.name = name;
    }

    public synchronized void join(Session session) {
        sessions.add(session);
    }

    public synchronized void leave(Session session) {
        sendMessage(new Message("Bot", format("%s has left the room", (String) session.getUserProperties().get("userName"))));
        sessions.remove(session);
    }

    public synchronized void sendMessage(Message message) {
        sessions.parallelStream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessage(message, session));
    }

    private void sendMessage(Message message, Session session) {
        try {
            session.getBasicRemote().sendObject(message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
