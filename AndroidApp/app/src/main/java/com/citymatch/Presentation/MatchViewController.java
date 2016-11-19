package com.citymatch.Presentation;


import android.content.Context;
import android.content.Intent;
import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Response;
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

import com.citymatch.ApiService.CityMatchService;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.Domain.Models.MatchList;
import com.citymatch.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MatchViewController extends AppCompatActivity {

    private MatchItem item;
    private int userID;
    private int cityID;
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
        userID = intent.getIntExtra(MatchListViewController.IntentAttribute.USER_ID.toString(), 0);
        cityID = intent.getIntExtra(MatchListViewController.IntentAttribute.CITY_ID.toString(), 0);

        CityMatchService.getInstance().getCityDescription(cityID, new CityDescriptionSuccess(), new CityDescriptionFailure());
        CityMatchService.getInstance().getMatchImages(userID, cityID, new MatchImagesSuccess(), new MatchImagesFailure());


    }

    private class CityDescriptionSuccess implements OnSuccess {

        @Override
        public void onSuccess(Response response) {
            String json = response.getMessage().toString();
            Gson gson = new Gson();
            item = gson.fromJson(json, MatchItem.class);
            TextView name = (TextView) findViewById(R.id.name);
            name.setText(item.name);
            TextView country = (TextView) findViewById(R.id.country);
            country.setText(item.country);
            TextView description = (TextView) findViewById(R.id.description);
            description.setText(item.description);

        }
    }

    private class CityDescriptionFailure implements OnFailure {

        @Override
        public void onFailure(Response response) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + response.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MatchImagesSuccess implements OnSuccess {

        @Override
        public void onSuccess(Response response) {
            String json = response.getMessage().toString();
            Gson gson = new Gson();
            ArrayList<String> photosURL = gson.fromJson(json, ArrayList.class);
            MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(), photosURL);
            photos.setAdapter(adapter);
        }
    }

    private class MatchImagesFailure implements OnFailure {
        @Override
        public void onFailure(Response response) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + response.getStatusCode(), Toast.LENGTH_SHORT).show();
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
