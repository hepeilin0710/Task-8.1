package com.example.a81c;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a81c.adapter.MessageAdapter;
import com.example.a81c.model.Message;
import com.example.a81c.net.ChatService;
import com.example.a81c.net.ChatRequest;
import com.example.a81c.net.ChatResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:11434/";  // ← change
    private static final String MODEL_TAG = "llama3.2:latest";

    private List<Message> data = new ArrayList<>();
    private MessageAdapter adapter;
    private ChatService api;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // RecyclerView
        RecyclerView rv = findViewById(R.id.rvChat);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(data);
        rv.setAdapter(adapter);

        // Retrofit
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .readTimeout(120, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(ChatService.class);

        // input and send
        EditText etMsg = findViewById(R.id.etMessage);
        ImageButton btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(v -> {
            String txt = etMsg.getText().toString().trim();
            if(txt.isEmpty()) return;
            append(Message.Sender.USER, txt);
            etMsg.setText("");
            queryBot(txt);
        });

        // welcome
        String user = getIntent().getStringExtra("username");
        append(Message.Sender.BOT, "Welcome "+user+"!");
    }

    private void append(Message.Sender sender, String content){
        data.add(new Message(sender, content));
        adapter.notifyItemInserted(data.size()-1);
        ((RecyclerView)findViewById(R.id.rvChat))
                .scrollToPosition(data.size()-1);
    }

    private void queryBot(String userMsg){
        ChatRequest body = new ChatRequest(MODEL_TAG, userMsg);
        api.chat(body).enqueue(new Callback<ChatResponse>() {
            @Override public void onResponse(Call<ChatResponse> call, Response<ChatResponse> res) {
                if(res.isSuccessful() && res.body()!=null){
                    String reply = res.body().getMessage().getContent();
                    append(Message.Sender.BOT, reply.trim());
                }else{
                    append(Message.Sender.BOT, "[Error] "+res.code());
                }
            }
            @Override public void onFailure(Call<ChatResponse> call, Throwable t) {
                append(Message.Sender.BOT, "[Network] "+t.getMessage());
            }
        });
    }
}
