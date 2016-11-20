package com.citymatch.Presentation;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.Match;
import com.citymatch.Domain.Models.Place;
import com.citymatch.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewController extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private LinearLayout suggestion;
    private TextView email;
    private TextView password;
    private TextView confpassword;
    private TextView city;
    private TextInputLayout emailT;
    private TextInputLayout passwordT;
    private TextInputLayout confpasswordT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);


        email = (TextView) findViewById(R.id.emial_edit_text);
        password = (TextView) findViewById(R.id.password_edit_text);
        confpassword = (TextView) findViewById(R.id.conf_password_edit_text);
        emailT = (TextInputLayout) findViewById(R.id.email_input_layout);
        passwordT = (TextInputLayout) findViewById(R.id.password_input_layout);
        confpasswordT = (TextInputLayout) findViewById(R.id.conf_password_input_layout);


        LinearLayout button = (LinearLayout) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<>();
        String strE = email.getText().toString();
        String strP = password.getText().toString();
        String strC = confpassword.getText().toString();
        if (strE == null || strE.length() == 0) {
            emailT.setError("Empty e-mail");
            passwordT.setError("");
            confpasswordT.setError("");
            return;
        }
        if (strP == null || strP.length() == 0) {
            emailT.setError("");
            passwordT.setError("Empty password");
            confpasswordT.setError("");
            return;
        }
        if (strC == null || !strC.equals(strP)) {
            emailT.setError("");
            passwordT.setError("");
            confpasswordT.setError("Passwords must be the same");
            return;
        }
        map.put("email", strE);
        map.put("password", strP);
        map.put("city", "LOND-sky");

        Service.getApiService().register(map).enqueue(
                new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Match> call, Throwable t) {

                    }
                }
        );
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String message = charSequence.toString();
        Service.getApiService().getPlaces(message).enqueue(
                new Callback<ArrayList<Place>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Place>> call, Response<ArrayList<Place>> response) {
                        setSpinner(response.body());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Place>> call, Throwable t) {

                    }
                }
        );
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    
    private void setSpinner(ArrayList<Place> places) {

    }
}
