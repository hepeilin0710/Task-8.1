package com.example.a81c.net;

import java.util.ArrayList;
import java.util.List;

public class ChatRequest {
    public String model;
    public List<MessageObj> messages;
    public boolean stream = false;

    public static class MessageObj {
        public String role;
        public String content;
        public MessageObj(String role, String content){
            this.role = role; this.content = content;
        }
    }

    public ChatRequest(String modelTag, String userMsg){
        this.model = modelTag;
        this.messages = new ArrayList<>();
        this.messages.add(new MessageObj("user", userMsg));
    }
}
