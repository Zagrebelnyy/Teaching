package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;

public class FormActivity extends AppCompatActivity {
    private Button btnAuth;
    private Button btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        btnAuth = findViewById(R.id.btnAuth);
        btnReg = findViewById(R.id.btnReg);
    }

    public void clickAuth(View view){
        startActivity(new Intent(this,AuthActivity.class));
    }

    public void clickReg(View view){
        startActivity(new Intent(this,RegActivity.class));
    }
}
