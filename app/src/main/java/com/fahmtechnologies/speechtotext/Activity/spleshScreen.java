package com.fahmtechnologies.speechtotext.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fahmtechnologies.speechtotext.R;

public class spleshScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(spleshScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}
