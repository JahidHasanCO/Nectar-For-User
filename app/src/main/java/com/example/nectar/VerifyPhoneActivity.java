package com.example.nectar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {

    private TextInputLayout textNameTI;
    private Button verifyBtn;
    private ProgressBar progressBarVer;
    private String verificationCodeBySystem;
    FirebaseAuth firebaseAuth;
    String verificationid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        textNameTI = findViewById(R.id.textNameTI);
        verifyBtn = findViewById(R.id.verifyBtn);
        progressBarVer = findViewById(R.id.progressBarVer);
        firebaseAuth = FirebaseAuth.getInstance();


    }



}