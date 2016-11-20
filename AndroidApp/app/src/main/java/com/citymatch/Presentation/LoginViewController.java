package com.citymatch.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.User;
import com.citymatch.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewController extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private TextInputLayout passwordTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        email = (EditText) findViewById(R.id.emial_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        passwordTIL = (TextInputLayout) findViewById(R.id.password_input_layout);

        LinearLayout login = (LinearLayout) findViewById(R.id.button);
        LinearLayout register = (LinearLayout) findViewById(R.id.register_layout);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Map<String, String> map = new HashMap<>();
        map.put("email", "pau@outlook.com");
        Service.getApiService().login(map).enqueue(
                new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        SharedPreferences.Editor editor = getSharedPreferences("sp-citymatch", Context.MODE_PRIVATE).edit();
                        editor.putString("user_id", response.body().getId());
                        editor.putBoolean("logged", true);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), MatcherViewController.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                }
        );

    }
}
