package com.example.tp_final_sauce_algerienne_proj_2.remote;

import com.example.tp_final_sauce_algerienne_proj_2.model.LoginCredentials;
import com.example.tp_final_sauce_algerienne_proj_2.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Body;


public interface UserService {

    @GET("users/")
    Call<List<User>> getUsers();

    @POST("users/")
    Call<User> addUser(@Body User user);

}

