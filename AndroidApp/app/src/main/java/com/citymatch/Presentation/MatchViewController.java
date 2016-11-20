package com.citymatch.Presentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchViewController extends AppCompatActivity {

    private MatchItem item;
    private String userID;
    private String cityID;
    private ListView photos;
    private LayoutInflater inflater;
    private MapView mapView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_list_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        inflater = getLayoutInflater();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                //if(item.)
            }
        });

        Intent intent = getIntent();
        userID = intent.getStringExtra(MatchListViewController.IntentAttribute.USER_ID.toString());
        cityID = intent.getStringExtra(MatchListViewController.IntentAttribute.CITY_ID.toString());

        Service.getApiService().getCityDescription(cityID).enqueue(new CityDescriptionCallback());
        Service.getApiService().getMatchImages(userID, cityID).enqueue(new MatchImagesCallback());
    }

    private class CityDescriptionCallback implements Callback<MatchItem> {

        @Override
        public void onResponse(Call<MatchItem> call, Response<MatchItem> response) {
            item = response.body();
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(item.getName());
            TextView country = (TextView) findViewById(R.id.country);
            country.setText(item.getCountry());
            TextView description = (TextView) findViewById(R.id.description);
            description.setText(item.getDescription());
        }

        @Override
        public void onFailure(Call<MatchItem> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MatchImagesCallback implements Callback<ArrayList<String>> {

        @Override
        public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
            ArrayList<String> photosURL = response.body();
            MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), photosURL);
            photos.setAdapter(adapter);
        }

        @Override
        public void onFailure(Call<ArrayList<String>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyArrayAdapter extends ArrayAdapter<String> {
        private final ArrayList<String> photosURL;

        public MyArrayAdapter(Context context, ArrayList<String> photosURL) {
            super(context, 0, photosURL);
            this.photosURL = photosURL;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView photo = new ImageView(getApplicationContext());
            //photo.setLayoutParams(new ViewGroup.LayoutParams(getApplicationContext(), new ));
            ImageLoader.getInstance().displayImage(photosURL.get(position), photo);
            return photo;
        }
    }

}
