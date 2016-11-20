package com.citymatch.ApiService;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    public static final String BASE_URL = "https://appcitymatch.herokuapp.com/";
    public static final String SP_NAME = "sp-citymatch";

    public static MyApiEntryPoint getApiService() {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(MyApiEntryPoint.class);
    }

    public static String getUserId(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString("user_id", "null");
    }

    public static void login(Context context, String userId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("user_id", userId);
        editor.putBoolean("logged", true);
        editor.apply();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("user_id", "null");
        editor.putBoolean("logged", false);
        editor.apply();
    }

    public static boolean isLogged(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("logged", false);
    }

}
