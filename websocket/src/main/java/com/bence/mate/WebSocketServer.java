package com.bence.mate;

import com.bence.mate.endpoints.ChatServerEndpoint;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.util.Scanner;

public class WebSocketServer {

    public static void main(String[] args) {
        Server server = new Server("localhost", 8025, "", null, ChatServerEndpoint.class);
        try {
            server.start();
            System.out.println("Press any key to stop the server..");
            new Scanner(System.in).nextLine();
        } catch (DeploymentException e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
