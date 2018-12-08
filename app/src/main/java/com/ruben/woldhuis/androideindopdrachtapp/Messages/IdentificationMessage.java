package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import java.time.LocalDateTime;

public class IdentificationMessage implements IMessage {
    private MessageType messageType = MessageType.Identification_Message;
    private String sender;
    private LocalDateTime timeSend;
    private String message;

    /**
     * @param sender
     * @param timeSend
     * @param message
     */
    public IdentificationMessage(String sender, LocalDateTime timeSend, String message) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
    }

    public static IdentificationMessage deserialize(String serialized) {
        String[] items = serialized.split(";");

        return new IdentificationMessage(items[0], LocalDateTime.parse(items[1]), items[3]);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getTimeSend() {
        return timeSend;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String serialize() {
        return messageType + ";" + sender + ";" + timeSend.toString() + ";" + message;
    }
}
