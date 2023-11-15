package com.example.tp_final_sauce_algerienne_proj_2.remote;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Map<String, Retrofit> retrofitInstances = new HashMap<>();

    public static Retrofit getClient(String baseUrl) {
        if (!retrofitInstances.containsKey(baseUrl)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInstances.put(baseUrl, retrofit);
        }
        return retrofitInstances.get(baseUrl);
    }
}
