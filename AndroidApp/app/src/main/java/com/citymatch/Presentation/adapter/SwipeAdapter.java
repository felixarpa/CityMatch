package com.citymatch.Presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.citymatch.Presentation.MatcherViewController;
import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class SwipeAdapter extends BaseAdapter {

    private MatcherViewController activity;
    private ArrayList<String> urls;

    public SwipeAdapter(MatcherViewController activity, ArrayList<String> urls) {
        this.activity = activity;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.swipe_layout, viewGroup, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_view);
        ImageLoader.getInstance().displayImage(urls.get(i), imageView);
        return null;
    }

    public void removeFirst() {
        urls.remove(0);
    }
}
