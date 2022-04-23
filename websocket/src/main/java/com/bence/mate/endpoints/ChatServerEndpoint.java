package com.bence.mate.endpoints;

import com.bence.mate.decoders.MessageDecoder;
import com.bence.mate.encoders.MessageEncoder;
import com.bence.mate.models.Message;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import java.util.Collections;
import java.io.IOException;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;

import static java.lang.String.format;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatServerEndpoint {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(format("%s joined the chat room.", session.getId()));
        peers.add(session);
    }

    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException {
        for (Session peer : peers) {
            if (!session.getId().equals(peer.getId())) {
                peer.getBasicRemote().sendObject(message);
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println(format("%s left the chat room.", session.getId()));
        peers.remove(session);

        for (Session peer : peers) {
            Message message = new Message(format("%s left the chat room", (String) session.getUserProperties().get("user")), "ChatServer", new Date());
            peer.getBasicRemote().sendObject(message);
        }
    }
}
