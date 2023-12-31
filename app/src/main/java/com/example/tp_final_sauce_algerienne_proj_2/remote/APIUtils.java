package com.example.tp_final_sauce_algerienne_proj_2.remote;

public class APIUtils {
    private APIUtils() {

    };

    public static final String MAP_API_URL = "https://maps.googleapis.com/maps/api/";
    public static final String API_URL = "http://10.0.2.2:3000/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(API_URL).create(UserService.class);
    }

    public static BillService getBillService(){
        return RetrofitClient.getClient(API_URL).create(BillService.class);
    }

    public static MapService getMapService(){
        return RetrofitClient.getClient(MAP_API_URL).create(MapService.class);
    }

}
