package com.example.bustime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {


    //http://bus.andong.go.kr:8080/api/route/bus/getDataList?routeId=354300491
    //https://bus.andong.go.kr:8080/api/facilities/station/getBusArriveData?stationId=370000023
    @GET("api/facilities/station/getBusArriveData")
    Call<PostResult> getPosts(@Query("stationId") String stationId);
}