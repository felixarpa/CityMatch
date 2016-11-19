package com.citymatch.Presentation;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class DeckAdapter extends BaseAdapter {

    private ArrayList<String> urls;
    private MatcherViewController activity;

    public DeckAdapter(ArrayList<String> urls, MatcherViewController activity) {
        this.urls = urls;
        this.activity = activity;
    }

    public void push(String url) {
        urls.add(url);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.swipe_layout, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
        final ProgressBar loading = (ProgressBar) v.findViewById(R.id.image_progress);
        ImageLoader.getInstance().displayImage(
                urls.get(i),
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
