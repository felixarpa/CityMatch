package com.citymatch.Domain.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.citymatch.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.Map;

public class MatchItem {

    private String id;
    private String name;
    private String country;
    private String coverImage;
    private String ssid;
    private String description;
    private Integer v;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     * The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return
     * The coverImage
     */
    public String getCoverImage() {
        return coverImage;
    }

    /**
     *
     * @param coverImage
     * The coverImage
     */
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    /**
     *
     * @return
     * The ssid
     */
    public String getSsid() {
        return ssid;
    }

    /**
     *
     * @param ssid
     * The ssid
     */
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The v
     */
    public Integer getV() {
        return v;
    }

    /**
     *
     * @param v
     * The __v
     */
    public void setV(Integer v) {
        this.v = v;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public View populateListItem(LayoutInflater inflater, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.match_list_item, parent, false);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView country = (TextView) rowView.findViewById(R.id.country);
        ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
        name.setText(this.name);
        country.setText(this.country);
        ImageLoader.getInstance().displayImage(coverImage, icon);
        return rowView;
    }
}