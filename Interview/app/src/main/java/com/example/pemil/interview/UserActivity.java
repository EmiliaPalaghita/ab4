package com.example.pemil.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by pemil on 11.03.2018.
 */

public class UserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
//        String user = intent.getStringExtra("test");
        Log.d("GOT USER", user.toString());
    }
}
