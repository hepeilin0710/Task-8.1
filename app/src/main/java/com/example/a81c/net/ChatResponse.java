package com.example.a81c.net;

public class ChatResponse {
    private MessageObj message;
    public MessageObj getMessage() { return message; }

    public static class MessageObj {
        private String role;
        private String content;
        public String getContent(){ return content; }
    }
}
