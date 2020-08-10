package com.example.test1212.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.test1212.R;

public class NewMainActivity extends AppCompatActivity {


    CardView statuscard;
    CardView energycard;
    CardView detailscard;

    ImageView aboutbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);


        statuscard = findViewById(R.id.statuscard);
        energycard = findViewById(R.id.energycard);
        detailscard = findViewById(R.id.detailscard);

        aboutbutton = findViewById(R.id.about_button_main);



        aboutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        // for opening status fragment in main activity
        statuscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                intent.putExtra("fragmentname","status");
                startActivity(intent);
            }
        });

        // for opening energy fragment in main activity
        energycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                intent.putExtra("fragmentname","energy");
                startActivity(intent);
            }
        });

        // for opening details fragment in main activity
        detailscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewMainActivity.this,MainActivity.class);
                intent.putExtra("fragmentname","details");
                startActivity(intent);
            }
        });

    }
}