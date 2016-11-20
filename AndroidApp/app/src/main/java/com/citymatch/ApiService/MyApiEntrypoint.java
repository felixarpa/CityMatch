package com.citymatch.ApiService;


import com.citymatch.Domain.Models.Image;
import com.citymatch.Domain.Models.User;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MyApiEntrypoint {

    @GET("images/")
    Call<ArrayList<Image>> getImages();

    @FormUrlEncoded
    @POST("users/login/")
    Call<User> login(@FieldMap Map<String, String> names);


}
