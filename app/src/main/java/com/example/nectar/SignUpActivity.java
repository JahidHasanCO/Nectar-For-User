package com.example.nectar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jaeger.library.StatusBarUtil;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private TextView signInTV;
    private TextInputLayout textNameTI,textPhoneTI,textPassTI,textCPassTI;
    private Button signUpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        StatusBarUtil.setTransparent(this);

        signInTV = findViewById(R.id.signInTV);
        textNameTI = findViewById(R.id.textNameTI);
        textPhoneTI = findViewById(R.id.textPhoneTI);
        textPassTI = findViewById(R.id.textPassTI);
        textCPassTI = findViewById(R.id.textCPassTI);
        signUpBtn = findViewById(R.id.signUpBtn);


        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInput();
            }
        });
    }

    String name,phone,password,cPassword;

    private boolean validateName(){
        name = textNameTI.getEditText().getText().toString().trim();
        if (name.isEmpty()){
            textNameTI.setError("Name can't be empty");
            return false;
        }
        else {
            textNameTI.setError(null);
            return true;
        }
    }

    private boolean validatePhone(){
        phone = textPhoneTI.getEditText().getText().toString().trim();
        if (!Patterns.PHONE.matcher(phone).matches()){
            textPhoneTI.setError("Phone Number not valid");
            return false;
        }
        else {
            textPhoneTI.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        password = textPassTI.getEditText().getText().toString().trim();
        cPassword = textCPassTI.getEditText().getText().toString().trim();
        if (password.isEmpty()) {
            textPassTI.setError("Phone can't be empty");
            return false;
        } else {
            textPassTI.setError(null);
            if (!password.equals(cPassword)) {
                textPassTI.setError("Password not match");
                return false;
            } else {
                textCPassTI.setError(null);
                return true;
            }
        }
    }

    private void confirmInput() {
        if (!validateName() | !validatePhone() | !validatePassword()){
            return;
        }
        else {
            String Vphone = textPhoneTI.getEditText().getText().toString().trim();
            Intent intent = new Intent(SignUpActivity.this,VerifyPhoneActivity.class);
            intent.putExtra("Name",name);
            intent.putExtra("Phone",Vphone);
            intent.putExtra("Password",password);
            startActivity(intent);
        }
    }
}