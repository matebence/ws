package com.bence.mate.encoders;

import com.bence.mate.models.Message;

import javax.websocket.EndpointConfig;
import javax.websocket.Encoder;
import javax.json.Json;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(final Message message) {
        return Json.createObjectBuilder()
                .add("message", message.getContent())
                .add("sender", message.getSender())
                .add("received", "")
                .build()
                .toString();
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}
}
