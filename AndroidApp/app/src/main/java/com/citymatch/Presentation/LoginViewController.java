package com.citymatch.Presentation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Response;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.citymatch.ApiService.CityMatchService;
import com.citymatch.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        CityMatchService.getInstance(this).login(
                email.getText().toString(),
                new OnSuccess() {
                    @Override
                    public void onSuccess(Response response) {
                        SharedPreferences.Editor editor = getSharedPreferences("sp-citymatch", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("logged", true);
                        String userId = "asdf";
                        Log.v("USERID", response.getMessage());
                        try {
                            JSONObject object1 = new JSONObject(response.getMessage());
                            JSONArray array = object1.getJSONArray("user");
                            JSONObject object2 = array.getJSONObject(0);
                            userId = object2.getString("_id");
                        } catch (JSONException ignored) {
                        }
                        Log.v("USERID", userId);
                        editor.putString("user_id", userId);
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), MatcherViewController.class));
                        finish();
                    }
                },
                new OnFailure() {
                    @Override
                    public void onFailure(Response response) {
                        passwordTIL.setError("Incorrect password");
                        password.setText("");
                    }
                }
        );
    }
}
