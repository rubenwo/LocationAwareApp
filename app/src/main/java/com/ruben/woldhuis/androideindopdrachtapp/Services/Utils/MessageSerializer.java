package com.ruben.woldhuis.androideindopdrachtapp.Services.Utils;

import android.util.Log;

import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.IMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.MessageType;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.FriendsReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadAudioMessageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Replies.UploadImageReply;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.FriendsRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadAudioMessageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Requests.UploadImageRequest;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.AuthenticationFailedMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.IdentificationMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.LocationUpdateMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.SignOutMessage;
import com.ruben.woldhuis.androideindopdrachtapp.MessagingProtocol.Messages.Updates.TextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.DataFormatException;

//TODO: Implement compression success/failed flag to byte[]
public class MessageSerializer {
    /**
     * @param value
     * @return
     */
    private static byte[] toByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    /**
     * @param message
     * @return
     */
    public static byte[] serialize(IMessage message) {
        String msg = message.toJson();
        Log.d("MESSAGE_TAG", msg);
        byte[] compressedData = null;

        try {
            compressedData = CompressionUtil.compress(msg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] prefix = toByteArray(compressedData.length);
        for (byte b : prefix) {
            Log.d("SIZE_TAG", "" + b);

        }
        byte[] buffer = new byte[prefix.length + compressedData.length];
        System.arraycopy(prefix, 0, buffer, 0, prefix.length);
        System.arraycopy(compressedData, 0, buffer, prefix.length, compressedData.length);
        return buffer;
    }

    /**
     * @param data
     * @param compressed
     * @return
     */
    public static IMessage deserialize(byte[] data, boolean compressed) {
        String json;
        if (compressed) {
            try {
                byte[] decompressedData = CompressionUtil.decompress(data);
                json = new String(decompressedData, 0, decompressedData.length, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (DataFormatException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            try {
                json = new String(data, 0, data.length, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        System.out.println("SERIALIZED:" + json);
        if (!json.isEmpty()) {
            try {
                JSONObject obj = new JSONObject(json);
                String messageType = obj.getString("messageType");

                System.out.println(messageType);
                MessageType type = MessageType.valueOf(messageType);
                switch (type) {
                    case Identification_Message:
                        return IdentificationMessage.fromJson(json);
                    case Text_Message:
                        return TextMessage.fromJson(json);
                    case SignOut_Message:
                        return SignOutMessage.fromJson(json);
                    case LocationUpdate_Message:
                        return LocationUpdateMessage.fromJson(json);
                    case FriendReply_Message:
                        return FriendReply.fromJson(json);
                    case FriendRequest_Message:
                        return FriendRequest.fromJson(json);
                    case UploadAudioReply_Message:
                        return UploadAudioMessageReply.fromJson(json);
                    case UploadImageReply_Message:
                        return UploadImageReply.fromJson(json);
                    case UploadAudioRequest_Message:
                        return UploadAudioMessageRequest.fromJson(json);
                    case UploadImageRequest_Message:
                        return UploadImageRequest.fromJson(json);
                    case FriendsReply_Message:
                        return FriendsReply.fromJson(json);
                    case FriendsRequest_Message:
                        return FriendsRequest.fromJson(json);
                    case AuthenticationFailed_Message:
                        return AuthenticationFailedMessage.fromJson(json);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
