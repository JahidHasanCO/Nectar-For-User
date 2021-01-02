package com.example.nectar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jaeger.library.StatusBarUtil;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTransparent(this);
    }
}