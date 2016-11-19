package com.citymatch.ApiService;

import android.content.Context;
import android.nakima.requestslibrary.OnFailure;
import android.nakima.requestslibrary.OnSuccess;
import android.nakima.requestslibrary.Request;

public class CityMatchService {

    private static final String URL = "https://appcitymatch.herokuapp.com/";

    private static CityMatchService ourInstance = new CityMatchService();

    public static CityMatchService getInstance() {
        return ourInstance;
    }
    public static CityMatchService getInstance(Context context) {
        return new CityMatchService(context);
    }

    private CityMatchService() {
    }

    private CityMatchService(Context context) {
        Request.initialize(context);
    }

    public void login(String email, OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.POST, URL + "users/login/");

        request.addParam("email", "pau@outlook.com");

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }

    public void getMatchList(int userID, OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.GET, URL + "match/" + userID);

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }

    public void getMatchImages(int userID, int cityID, OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.GET, URL + "likes/" + userID + "/" + cityID);

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }

    public void getCityDescription(int cityID, OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.GET, URL + "city/" + cityID);

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }

    public void getImages(OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.GET, URL + "images/");

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }
}
