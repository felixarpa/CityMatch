package com.citymatch.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.citymatch.R;

public class SplashController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_veiw);

        SharedPreferences sp = getSharedPreferences("sp-citymatch", Context.MODE_PRIVATE);
        if (sp.getBoolean("logged0", false)) {
            startActivity(new Intent(getApplicationContext(), MatcherViewController.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginViewController.class));
            finish();
        }
    }
}
