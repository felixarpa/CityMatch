package com.citymatch.Presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.citymatch.R;
import com.daprlabs.cardstack.SwipeDeck;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MatcherViewController extends AppCompatActivity implements View.OnClickListener, SwipeDeck.SwipeEventCallback {

    private SwipeDeck deck;

    private ArrayList<String> urls = new ArrayList<>();
    private ArrayList<String> ids = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matcher_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        CardView likeButton = (CardView) findViewById(R.id.like);
        CardView dislikeButton = (CardView) findViewById(R.id.dislike);
        deck = (SwipeDeck) findViewById(R.id.swipe_deck);

        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);

        deck.setAdapter(new DeckAdapter(urls, this));
        deck.setEventCallback(this);

    }

    @Override
    public void cardSwipedLeft(int position) {
        // DISLIKE
        if (position % 10 == 9) {
            loadMorePlaces();
        }
        match(false, position);
    }

    @Override
    public void cardSwipedRight(int position) {
        // LIKE
        if (position % 10 == 9) {
            loadMorePlaces();
        }
        match(true, position);
    }

    private void loadMorePlaces() {

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

    }

    private void isMarch() {
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
                deck.swipeTopCardLeft(10000);
                break;

            case R.id.like:
                deck.swipeTopCardRight(10000);
                break;
        }

    }
}
