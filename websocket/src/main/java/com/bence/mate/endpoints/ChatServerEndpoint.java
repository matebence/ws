package com.bence.mate.endpoints;

import com.bence.mate.decoders.MessageDecoder;
import com.bence.mate.encoders.MessageEncoder;
import com.bence.mate.models.Message;
import com.bence.mate.models.Room;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.PathParam;
import javax.annotation.PostConstruct;
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;

import javax.websocket.EndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.PongMessage;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import java.util.logging.Logger;
import java.util.Collections;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;

import static com.bence.mate.utils.Messages.WELCOME_MESSAGE;
import static com.bence.mate.utils.Messages.objectify;

@ServerEndpoint(value = "/chat/{roomName}/{userName}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatServerEndpoint {

    private final static Logger log = Logger.getLogger(ChatServerEndpoint.class.getSimpleName());

    private static final Map<String, Room> rooms = Collections.synchronizedMap(new HashMap<>());

    private static final String[] roomNames = {"Java EE 7", "Java SE 8", "Websockets", "JSON"};

    @PostConstruct
    public void initialise() {
        Arrays.stream(roomNames).forEach(roomName -> rooms.computeIfAbsent(roomName, new Room(roomName)));
    }

    @OnOpen
    public void onOpen(final Session session, @PathParam("roomName") final String roomName, @PathParam("userName") final String userName, EndpointConfig conf) throws IOException, EncodeException {
        session.getUserProperties().putIfAbsent("roomName", roomName);
        session.getUserProperties().putIfAbsent("userName", userName);

        session.setMaxIdleTimeout(5 * 60 * 1000);

        Room room = rooms.get(roomName);
        room.join(session);

        session.getBasicRemote().sendObject(objectify(WELCOME_MESSAGE));
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        rooms.get(extractRoomFrom(session)).sendMessage(message);
    }

    @OnMessage
    public void onBinaryMessage(ByteBuffer message, Session session) {}

    @OnMessage
    public void onPongMessage(PongMessage message, Session session) {}

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info(reason::getReasonPhrase);
        rooms.get(extractRoomFrom(session)).leave(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info(error::getMessage);
    }

    private String extractRoomFrom(Session session) {
        return ((String) session.getUserProperties().get("roomName"));
    }

    public static Map<String, Room> getRooms() {
        return rooms;
    }
}
