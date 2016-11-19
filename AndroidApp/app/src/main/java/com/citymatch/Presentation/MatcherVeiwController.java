package com.citymatch.Presentation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.citymatch.R;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

public class MatcherVeiwController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matcher_view);

        FloatingActionButton likeButton = (FloatingActionButton) findViewById(R.id.like_fab);
        FloatingActionButton dislikeButton = (FloatingActionButton) findViewById(R.id.dislike_fab);

        SwipeFlingAdapterView container = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);

        //container.setAdapter();

    }
}
