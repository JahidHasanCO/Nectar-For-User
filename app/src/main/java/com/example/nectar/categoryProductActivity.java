package com.example.nectar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class categoryProductActivity extends AppCompatActivity {

    private TextView ctx;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        categoryName = getIntent().getStringExtra("categoryName");

        ctx = findViewById(R.id.ctx);
        ctx.setText(categoryName);
    }
}