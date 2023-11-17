package com.luv2code.shopme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.luv2code.shopme.Activity.MainActivity;
import com.luv2code.shopme.Activity.WelcomeActivity;
import com.luv2code.shopme.Utils.LocalStorage;

public class SplashActivity extends AppCompatActivity {
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!localStorage.getInstance(getApplicationContext()).isFirstTimeLaunch()) {
                    Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
                else {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }

            }
        },3000);
    }
}