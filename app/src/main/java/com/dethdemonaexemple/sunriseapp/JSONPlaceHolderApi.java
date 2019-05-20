package com.dethdemonaexemple.sunriseapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("json?")
    public Call<Data> getDataCity(@Query("lat") Double lat,@Query("lng") Double lng,@Query("formatted") int form,@Query("date") String date);
}
