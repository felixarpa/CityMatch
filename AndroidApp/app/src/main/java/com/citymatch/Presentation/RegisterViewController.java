package com.citymatch.Presentation;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);


        email = (TextView) findViewById(R.id.emial_edit_text);
        password = (TextView) findViewById(R.id.password_edit_text);
        confpassword = (TextView) findViewById(R.id.conf_password_edit_text);
        city = (TextView) findViewById(R.id.city_edit_text);
        city.addTextChangedListener(this);

        LinearLayout button = (LinearLayout) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email.getText().toString());
        map.put("password", password.getText().toString());
        map.put("city", city.getText().toString());

        Service.getApiService().register(map).enqueue(
                new Callback<Match>() {
                    @Override
                    public void onResponse(Call<Match> call, Response<Match> response) {
                        System.out.println("SUCCESS");
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
                        setAdapter(response.body());
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
    
    private void setAdapter(ArrayList<Place> places) {
        ArrayList<Place> array = noRepeat(places);

        for (final Place p : array) {
            View v = getLayoutInflater().inflate(R.layout.suggestion_item, null);
            TextView textView = (TextView) v.findViewById(R.id.text);
            textView.setText(p.getCityId());
            v.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            city.setText(p.getCityId());
                        }
                    }
            );
            suggestion.addView(v);
        }
    }

    private ArrayList<Place> noRepeat(ArrayList<Place> places) {
        int n = places.size();
        ArrayList<Place> res = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            int m = res.size();
            boolean noEsta = true;
            for (int j = 0; j < m && noEsta; j++) {
                noEsta = (!places.get(i).getCityId().equalsIgnoreCase(res.get(j).getCityId()));
            }
            if (noEsta) res.add(places.get(i));
        }
        return res;
    }
}
