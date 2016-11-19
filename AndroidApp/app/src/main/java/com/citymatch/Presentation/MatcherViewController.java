package com.citymatch.Presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.citymatch.Presentation.adapter.SwipeAdapter;
import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MatcherViewController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matcher_view);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));

//        FloatingActionButton likeButton = (FloatingActionButton) findViewById(R.id.like_fab);
//        FloatingActionButton dislikeButton = (FloatingActionButton) findViewById(R.id.dislike_fab);

//        SwipeFlingAdapterView container = (SwipeFlingAdapterView) findViewById(R.id.swipe_view);

        ArrayList<String> list = new ArrayList<>();

        list.add("http://as01.epimg.net/img/comunes/fotos/fichas/estadios/camp_nou.jpg");
        list.add("http://www.euro-sur.com/wp-content/uploads/2016/01/mudanza-barcelona-precio-mudanzabarcelona-mudanza-presupuesto-online-guardamuebles-trasteros-traslado-oficinas-empresas-de-mudanzas-internacionales-transportes-y-mudanzas-4.jpg");
        list.add("http://cache-graphicslib.viator.com/graphicslib/thumbs360x240/2512/SITours/barcelona-modernism-and-gaudi-walking-tour-in-barcelona-168394.jpg");

        final SwipeAdapter adapter = new SwipeAdapter(this, list);

//        container.setAdapter(adapter);
//        container.setFlingListener(
//                new SwipeFlingAdapterView.onFlingListener() {
//                    @Override
//                    public void removeFirstObjectInAdapter() {
//                        adapter.removeFirst();
//                    }
//
//                    @Override
//                    public void onLeftCardExit(Object o) {
//                        Toast.makeText(getApplicationContext(), "LEFT", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onRightCardExit(Object o) {
//                        Toast.makeText(getApplicationContext(), "LEFT", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onAdapterAboutToEmpty(int i) {
//                        pushUrls();
//                    }
//
//                    @Override
//                    public void onScroll(float v) {
//
//                    }
//                }
//        );

    }

    private void pushUrls() {

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
}
