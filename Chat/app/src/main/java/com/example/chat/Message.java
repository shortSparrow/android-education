package com.example.chat;

public class Message {
    private String text;
    private String senderName;
    private String imageUrl;
    private String createdAt;
    private String sender;
    private String recipient;

    public Message() {
    }

    public Message(String text, String senderName, String imageUrl, String createdAt, String sender, String recipient) {
        this.text = text;
        this.senderName = senderName;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
