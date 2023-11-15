package com.example.tp_final_sauce_algerienne_proj_2.remote;

import com.example.tp_final_sauce_algerienne_proj_2.model.DirectionsResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapService {

    @GET("directions/json")
    Call<DirectionsResponse> getDirections(
            @Query("destination") String destination,
            @Query("origin") String origin,
            @Query("key") String apiKey
    );

}