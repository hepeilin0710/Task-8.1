package com.example.a81c.model;

public class Message {
    public enum Sender { USER, BOT }
    private final Sender sender;
    private final String content;

    public Message(Sender sender, String content) {
        this.sender = sender;
        this.content = content;
    }
    public Sender getSender() { return sender; }
    public String getContent() { return content; }
}
