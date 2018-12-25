package com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages;


import com.ruben.woldhuis.androideindopdrachtapp.Constants;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.Models.User;

public class AudioMessage implements IMessage {
    private MessageType messageType = MessageType.Audio_Message;
    private String fireBaseToken;
    private String base64EncodedAudio;
    private User target;

    public AudioMessage(String fireBaseToken, String base64EncodedAudio, User target) {
        this.fireBaseToken = fireBaseToken;
        this.base64EncodedAudio = base64EncodedAudio;
        this.target = target;
    }

    public static AudioMessage deserialize(String json) {
        return Constants.GSON.fromJson(json, AudioMessage.class);
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public String getBase64EncodedAudio() {
        return base64EncodedAudio;
    }

    public User getTarget() {
        return target;
    }

    @Override
    public String toJson() {
        return Constants.GSON.toJson(this);
    }
}
