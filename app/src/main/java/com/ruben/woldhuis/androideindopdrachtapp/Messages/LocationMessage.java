package com.ruben.woldhuis.androideindopdrachtapp.Messages;

import com.google.gson.Gson;
import com.ruben.woldhuis.androideindopdrachtapp.Models.Location;

import java.util.Date;

public class LocationMessage implements IMessage {
    private MessageType messageType = MessageType.Location_Message;
    private String sender;
    private Date timeSend;
    private String message;
    private Location location;

    public LocationMessage(String sender, Date timeSend, String message, Location location) {
        this.sender = sender;
        this.timeSend = timeSend;
        this.message = message;
        this.location = location;
    }

    public static LocationMessage deserialize(String serialized) {
        Gson gson = new Gson();
        return gson.fromJson(serialized, LocationMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return this.messageType;
    }

    public String getSender() {
        return this.sender;
    }

    public Date getTimeSend() {
        return this.timeSend;
    }

    public String getMessage() {
        return this.message;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "messageType=" + messageType +
                ", sender='" + sender + '\'' +
                ", timeSend=" + timeSend +
                ", message='" + message + '\'' +
                ", location=" + location +
                '}';
    }
}
