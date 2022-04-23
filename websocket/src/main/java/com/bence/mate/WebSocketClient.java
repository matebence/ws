package com.bence.mate;

import com.bence.mate.endpoints.ChatClientEndpoint;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.Session;
import java.util.Scanner;
import java.net.URI;

import static com.bence.mate.utils.JsonUtil.formatMessage;

public class WebSocketClient {

    public static void main(String[] args) throws Exception {

        ClientManager client = ClientManager.createClient();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tiny Chat!");
        System.out.println("What's your name?");
        String user = scanner.nextLine();

        Session session = client.connectToServer(
                ChatClientEndpoint.class,
                new URI("ws://localhost:8025/chat"));

        System.out.println("You are logged in as: " + user);

        String message;
        do {
            message = scanner.nextLine();
            session.getBasicRemote().sendText(formatMessage(message, user));
        } while (!message.equalsIgnoreCase("quit"));
    }
}
