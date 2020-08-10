package com.example.test1212.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.test1212.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1023;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //SplashScreenContext = getApplicationContext();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Main = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(Main);
            }
        },SPLASH_TIME_OUT);
    }




}
