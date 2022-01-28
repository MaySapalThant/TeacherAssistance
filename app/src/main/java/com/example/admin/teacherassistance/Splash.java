package com.example.admin.teacherassistance;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

public class Splash extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    SharedPreferences prf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prf = getSharedPreferences("user_detail",MODE_PRIVATE);
        //SharedPreferences.Editor editor = prf.edit();
        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                if(prf.getString("username","").equals("")){
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
                }
                else{
                    startActivity(new Intent(Splash.this, MainDrawer.class));
                    finish();

                }
            }
        }, secondsDelayed * SPLASH_DISPLAY_LENGTH);
    }
}
