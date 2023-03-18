package com.seeksolution.joy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class My_Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_splash_screen);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(My_Splash_Screen.this,MainActivity.class);
            startActivity(intent);
            finish();
        },2000);
    }
}