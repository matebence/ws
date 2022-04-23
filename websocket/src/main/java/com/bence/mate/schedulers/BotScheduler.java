package com.bence.mate.schedulers;

import com.bence.mate.endpoints.ChatServerEndpoint;
import com.bence.mate.utils.Messages;

import javax.ejb.Stateless;
import javax.ejb.Schedule;

@Stateless
public class BotScheduler {

    @Schedule(minute = "*/20", hour = "*")
    private void interrupt() {
        ChatServerEndpoint.getRooms().forEach((s, room) -> room.sendMessage(Messages.objectify("Hello from Bot")));
    }
}
