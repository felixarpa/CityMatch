package com.citymatch.Presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.citymatch.R;
import com.daprlabs.cardstack.SwipeDeck;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class MatcherViewController extends AppCompatActivity implements View.OnClickListener {

    private SwipeDeck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matcher_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

        CardView likeButton = (CardView) findViewById(R.id.like);
        CardView dislikeButton = (CardView) findViewById(R.id.dislike);

        deck = (SwipeDeck) findViewById(R.id.swipe_deck);

        final ArrayList<String> list = new ArrayList<>();

        list.add("http://as01.epimg.net/img/comunes/fotos/fichas/estadios/camp_nou.jpg");
        list.add("http://www.euro-sur.com/wp-content/uploads/2016/01/mudanza-barcelona-precio-mudanzabarcelona-mudanza-presupuesto-online-guardamuebles-trasteros-traslado-oficinas-empresas-de-mudanzas-internacionales-transportes-y-mudanzas-4.jpg");
        list.add("http://cache-graphicslib.viator.com/graphicslib/thumbs360x240/2512/SITours/barcelona-modernism-and-gaudi-walking-tour-in-barcelona-168394.jpg");



        deck.setAdapter(
                new BaseAdapter() {

                    @Override
                    public int getCount() {
                        return list.size();
                    }

                    @Override
                    public String getItem(int i) {
                        return list.get(i);
                    }

                    @Override
                    public long getItemId(int i) {
                        return i;
                    }

                    @Override
                    public View getView(int i, View view, ViewGroup viewGroup) {
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.swipe_layout, null);
                        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
                        final ProgressBar loading = (ProgressBar) v.findViewById(R.id.image_progress);
                        ImageLoader.getInstance().displayImage(
                                list.get(i),
                                imageView,
                                new ImageLoadingListener() {
                                    @Override
                                    public void onLoadingStarted(String imageUri, View view) {

                                    }

                                    @Override
                                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                                    }

                                    @Override
                                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                        loading.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onLoadingCancelled(String imageUri, View view) {

                                    }
                                }
                        );
                        return v;
                    }
                }
        );

        deck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                // DISLIKE
                Toast.makeText(getApplicationContext(), "LEFT: " + position, Toast.LENGTH_SHORT).show();
                isMatch(position);
            }

            @Override
            public void cardSwipedRight(int position) {
                // LIKE
                list.add("http://as01.epimg.net/img/comunes/fotos/fichas/estadios/camp_nou.jpg");
                Toast.makeText(getApplicationContext(), "RIGHT: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                Toast.makeText(getApplicationContext(), "No more card", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });

        likeButton.setOnClickListener(this);
        dislikeButton.setOnClickListener(this);

    }

    private void isMatch(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.match_dialog, null);
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
