package com.example.felixarpa.citymatch.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.felixarpa.citymatch.R;

public class SplashController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_veiw);

        SharedPreferences sp = getSharedPreferences("sp-citymatch", Context.MODE_PRIVATE);
        if (sp.getBoolean("logged", false)) {
            startActivity(new Intent(getApplicationContext(), MatcherVeiwController.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginViewController.class));
            finish();
        }
    }
}
