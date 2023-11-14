package com.example.tp_final_sauce_algerienne_proj_2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCredentials {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
