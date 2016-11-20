package com.citymatch.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.citymatch.ApiService.Service;
import com.citymatch.R;

public class SplashController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_veiw);

        if (Service.isLogged(this)) {
            startActivity(new Intent(getApplicationContext(), MatcherViewController.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), LoginViewController.class));
            finish();
        }
    }
}
