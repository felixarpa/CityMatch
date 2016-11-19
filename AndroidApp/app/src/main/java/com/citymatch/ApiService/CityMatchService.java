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

    public void login(String username, String password, OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.POST, URL);

        request.addParam("username", username);
        request.addParam("password", password);

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }

    public void getMatchList(OnSuccess onSuccess, OnFailure onFailure) {
        String username = "pepe";

        Request request = new Request(Request.GET, URL);
        request.addParam("username", username);

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        //request.send();
        onSuccess.onSuccess(null);
    }

    public void getImages(OnSuccess onSuccess, OnFailure onFailure) {
        Request request = new Request(Request.GET, URL + "images/");

        request.setOnSuccess(onSuccess);
        request.setOnFailure(onFailure);

        request.send();
    }
}
