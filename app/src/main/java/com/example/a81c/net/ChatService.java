package com.example.a81c.net;

import com.example.a81c.net.ChatRequest;
import com.example.a81c.net.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatService {
    @POST("api/chat")
    Call<ChatResponse> chat(@Body ChatRequest body);
}
