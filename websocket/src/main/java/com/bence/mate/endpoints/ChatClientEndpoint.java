package com.bence.mate.endpoints;

import com.bence.mate.decoders.MessageDecoder;
import com.bence.mate.encoders.MessageEncoder;
import com.bence.mate.models.Message;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;

import java.text.SimpleDateFormat;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatClientEndpoint {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnMessage
    public void onMessage(Message message) {
        System.out.println(String.format("[%s:%s] %s", simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
    }
}
