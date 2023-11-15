package com.example.tp_final_sauce_algerienne_proj_2.remote;

import com.example.tp_final_sauce_algerienne_proj_2.model.Bill;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillService {

    @GET("bills/")
    Call<List<Bill>> getBills();

    @POST("bills/")
    Call<Bill> addBill(@Body Bill bills);

}
