package com.citymatch.Presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.citymatch.ApiService.MyApiEntryPoint;
import com.citymatch.ApiService.Service;
import com.citymatch.Domain.Models.Image;
import com.citymatch.R;
import com.daprlabs.cardstack.SwipeDeck;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatcherViewController extends AppCompatActivity implements View.OnClickListener, SwipeDeck.SwipeEventCallback {

    private SwipeDeck deck;

    private ArrayList<String> ids = new ArrayList<>();

    private int page = 0;
    private DeckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matcher_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        CardView likeButton = (CardView) findViewById(R.id.like);
        CardView dislikeButton = (CardView) findViewById(R.id.dislike);
        deck = (SwipeDeck) findViewById(R.id.swipe_deck);
        ImageView settingsButton = (ImageView) findViewById(R.id.settings);
        ImageView listButton = (ImageView) findViewById(R.id.matches);

        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);
        settingsButton.setOnClickListener(this);
        listButton.setOnClickListener(this);

        adapter = new DeckAdapter(this);

        MyApiEntryPoint apiService = Service.getApiService();

        Service.getApiService().getImages().enqueue(
                new Callback<ArrayList<Image>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Image>> call, Response<ArrayList<Image>> response) {
                        ArrayList<Image> imgs = response.body();
                        for (Image i : imgs) {
                            adapter.push(i.getUrl());
                        }
                        setDeck();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Image>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Can't get places", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    private void setDeck() {
        deck.setAdapter(adapter);
        deck.setEventCallback(this);
    }

    @Override
    public void cardSwipedLeft(int position) {
        // DISLIKE
        if (position % 10 == 7) {
            loadMorePlaces();
        }
        match(false, position);
    }

    @Override
    public void cardSwipedRight(int position) {
        // LIKE
        if (position % 10 == 7) {
            loadMorePlaces();
        }
        match(true, position);
    }

    private void loadMorePlaces() {
        ++page;
    }

    @Override
    public void cardsDepleted() {

    }

    @Override
    public void cardActionDown() {

    }

    @Override
    public void cardActionUp() {

    }

    private void match(boolean like, int position) {
        String id = ids.get(position);

    }

    private void isMatch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.match_dialog, null);
        // TODO image views
        builder.setView(view);
        builder.setPositiveButton(
                "Show match",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }
        );
        builder.setNeutralButton(
                "Keep swiping",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );
        builder.show();
    }

    @Override
    protected void onPause() {
        ImageLoader.getInstance().destroy();
        super.onPause();
    }

    @Override
    protected void onResume() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dislike:
                deck.swipeTopCardLeft(1000);
                break;

            case R.id.like:
                deck.swipeTopCardRight(1000);
                break;

            case R.id.settings:
                break;

            case R.id.matches:
                break;
        }

    }
}
