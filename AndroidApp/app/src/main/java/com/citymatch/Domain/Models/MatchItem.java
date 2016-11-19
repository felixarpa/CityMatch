package com.citymatch.Domain.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MatchItem {

    public String name;
    public String country;
    public String imageURL;
    public Integer cityID;
    public String description;
    public ArrayList<String> photos;

    public MatchItem() {}

    public View populateListItem(Context context, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.match_list_item, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView country = (TextView) rowView.findViewById(R.id.country);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        name.setText(this.name);
        country.setText(this.country);
        //ImageLoader.getInstance().displayImage(imageURL, icon);
        return rowView;
    }
}
