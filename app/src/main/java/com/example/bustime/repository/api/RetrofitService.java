package com.example.bustime.repository.api;

import com.example.bustime.repository.api.dto.routeData.PostResult;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    //1. 정류소 정보 (http://bus.andong.go.kr:8080/api/facilities/station/getDataList?type=Paging&pageSize=10&pageUnit=10&pageIndex=1)
    @GET("api/facilities/station/getDataList")
    Call<PostResult> getBusStation(@Query("?type=Paging&pageSize=10&pageUnit=10&pageIndex=") String pageIndex);

    //2. 정류소 버스도착정보(단위: minute) http://bus.andong.go.kr:8080/api/facilities/station/getBusArriveData?stationId=370000023
    @GET("api/facilities/station/getBusArriveData")
    Call<StopPostResults> getBusArrivalData(@Query("stationId") String stationId);

    //3. 노선 정보 제공 서비스 http://bus.andong.go.kr:8080/api/route/getDataList?type=All
    @GET("api/route/getDataList")
    Call<PostResult> getRouteData(@Query("type") String type);

    //4. 노선 별 정류소 정보 http://bus.andong.go.kr:8080/api/route/station/getDataList?routeId=354300491
    @GET("api/route/station/getDataList")
    Call<PostResult> getRouteStation(@Query("routeId=") String stationId);

    //5. 노선 별 운행 버스 정보 http://bus.andong.go.kr:8080/api/route/bus/getDataList?routeId=354300491
    @GET("api/route/bus/getDataList")
    Call<PostResult> getRouteBusData(@Query("type=All") String stationId);
}