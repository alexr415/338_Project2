package com.example.cst338project2.DB;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = AppDatabase.MESSAGE_TABLE, foreignKeys = {@ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "senderId"), @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "receiverId")})
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int messageId;
    private int senderId;
    private int receiverId;
    private String messageBody;
    private String subject;

    public Message(int senderId, int receiverId, String messageBody, String subject) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageBody = messageBody;
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;
        return getMessageId() == message.getMessageId() && getSenderId() == message.getSenderId() && getReceiverId() == message.getReceiverId() && Objects.equals(getMessageBody(), message.getMessageBody()) && Objects.equals(getSubject(), message.getSubject());
    }

    @Override
    public int hashCode() {
        int result = getMessageId();
        result = 31 * result + getSenderId();
        result = 31 * result + getReceiverId();
        result = 31 * result + Objects.hashCode(getMessageBody());
        result = 31 * result + Objects.hashCode(getSubject());
        return result;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
