package com.example.a81c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUser = findViewById(R.id.etUsername);
        Button btnGo   = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(v -> {
            String name = etUser.getText().toString().trim();
            if(name.isEmpty()){ etUser.setError("Required"); return; }

            Intent it = new Intent(this, ChatActivity.class);
            it.putExtra("username", name);
            startActivity(it);
            finish();
        });
    }
}
