package com.citymatch.Domain.Models;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Place {

    private String placeId;
    private String placeName;
    private String countryId;
    private String regionId;
    private String cityId;
    private String countryName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The placeId
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     *
     * @param placeId
     * The PlaceId
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     *
     * @return
     * The placeName
     */
    public String getPlaceName() {
        return placeName;
    }

    /**
     *
     * @param placeName
     * The PlaceName
     */
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     *
     * @return
     * The countryId
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     * The CountryId
     */
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     * The regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     *
     * @param regionId
     * The RegionId
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     *
     * @return
     * The cityId
     */
    public String getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The CityId
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName
     * The CountryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}