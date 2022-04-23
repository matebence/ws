package com.bence.mate.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;

import java.util.Date;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String sender;

    @Getter
    @Setter
    private Date received;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(content, message.content) &&
                Objects.equals(sender, message.sender) &&
                Objects.equals(received, message.received);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, sender, received);
    }
}
