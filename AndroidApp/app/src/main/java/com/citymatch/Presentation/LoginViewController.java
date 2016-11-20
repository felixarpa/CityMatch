package com.citymatch.Presentation;

import android.content.Intent;
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
    private TextInputLayout emailTIL;
    private TextInputLayout passwordTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_view);

        email = (EditText) findViewById(R.id.emial_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        emailTIL = (TextInputLayout) findViewById(R.id.email_input_layout);
        passwordTIL = (TextInputLayout) findViewById(R.id.password_input_layout);

        LinearLayout login = (LinearLayout) findViewById(R.id.button);
        LinearLayout register = (LinearLayout) findViewById(R.id.register_layout);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.register_layout) {
            startActivity(new Intent(getApplicationContext(), RegisterViewController.class));
        } else {
            String strEmail = email.getText().toString();
            String strPassword = password.getText().toString();
            if (strEmail == null || strEmail.length() == 0) {
                passwordTIL.setError("");
                emailTIL.setError("Empty email");
                return;
            }
            if (strPassword == null || strPassword.length() == 0) {
                passwordTIL.setError("Incorrect password");
                emailTIL.setError("");
                return;
            }
            Map<String, String> map = new HashMap<>();
            map.put("email", email.getText().toString());
            map.put("password", password.getText().toString());
            Service.getApiService().login(map).enqueue(
                    new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Service.login(getApplicationContext(), response.body().getId());
                            startActivity(new Intent(getApplicationContext(), MatcherViewController.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            passwordTIL.setError("Incorrect e-mail or password");
                            emailTIL.setError("");
                        }
                    }
            );

        }
    }
}
