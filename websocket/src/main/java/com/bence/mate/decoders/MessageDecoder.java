package com.bence.mate.decoders;

import com.bence.mate.models.Message;

import javax.websocket.EndpointConfig;
import javax.websocket.Decoder;
import javax.json.JsonObject;
import java.io.StringReader;
import java.time.LocalTime;
import javax.json.Json;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String textMessage) {
        JsonObject jsonObject = Json.createReader(new StringReader(textMessage)).readObject();
        return new Message(jsonObject.getString("content"), jsonObject.getString("sender"), LocalTime.now().toString());
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}
}
