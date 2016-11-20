package com.citymatch.Presentation;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.Image;
import com.citymatch.Domain.Models.Location;
import com.citymatch.Domain.Models.MatchItem;
import com.citymatch.R;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchViewController extends AppCompatActivity implements OnMapReadyCallback {

    private MatchItem item;
    private String userID;
    private String cityID;
    private LinearLayout photos;
    private LayoutInflater inflater;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_detail_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        inflater = getLayoutInflater();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        userID = intent.getStringExtra(MatchListViewController.IntentAttribute.USER_ID.toString());
        cityID = intent.getStringExtra(MatchListViewController.IntentAttribute.CITY_ID.toString());

        photos = (LinearLayout) findViewById(R.id.photos);

        Service.getApiService().getCityDescription(cityID).enqueue(new CityDescriptionCallback());
        Service.getApiService().getMatchImages(userID, cityID).enqueue(new MatchImagesCallback());

        ImageButton button = (ImageButton) findViewById(R.id.skyscanner_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.skyscanner.net/transport/flights/gva/" + item.getSsid().split("-")[0];
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setScrollGesturesEnabled(false);

        if(item != null) {
            setMapLocation();
        }
    }

    private void setMapLocation() {
        Service.getApiService().getLocation(item.getName()).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                Location pos = response.body();
                LatLng coord = new LatLng(pos.getLat(), pos.getLng());
                map.addMarker(new MarkerOptions().position(coord).title("Marker"));
                map.moveCamera(CameraUpdateFactory.newLatLng(coord));
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {}
        });
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
            ImageLoader.getInstance().displayImage(item.getCoverImage(), (ImageView) findViewById(R.id.icon));
            if(item != null) {
                setMapLocation();
            }
        }

        @Override
        public void onFailure(Call<MatchItem> call, Throwable t) {
            Toast.makeText(getApplicationContext(), "Database connection error = " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MatchImagesCallback implements Callback<ArrayList<Image>> {

        @Override
        public void onResponse(Call<ArrayList<Image>> call, Response<ArrayList<Image>> response) {
            ArrayList<Image> images = response.body();
            if(images != null) {
                for(Image image : images) {
                    View v = getLayoutInflater().inflate(R.layout.photo_row, null);
                    ImageView imageView = (ImageView) v.findViewById(R.id.photo);
                    ImageLoader.getInstance().displayImage(image.getUrl(), imageView);
                    photos.addView(v);
                }
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Image>> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(getApplicationContext(), "Database connection error = " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
