package com.example.test1212.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.example.test1212.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1023;

    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        icon = findViewById(R.id.appiconsplash);

        //SplashScreenContext = getApplicationContext();
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                //Intent Main = new Intent(SplashScreenActivity.this, MainActivity.class);
                Intent newMain = new Intent(SplashScreenActivity.this,NewMainActivity.class);

               // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this,new Pair<View, String>(icon,"icontransition"));

                startActivity(newMain);//,options.toBundle());
            }
        },SPLASH_TIME_OUT);
    }




}
