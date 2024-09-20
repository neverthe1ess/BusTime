package com.example.bustime;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.bustime.repository.api.RetrofitClient;
import com.example.bustime.repository.api.RetrofitService;
import com.example.bustime.repository.api.dto.routeData.PostResult;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;
import com.example.bustime.repositorydatabase.BusStop;
import com.example.bustime.repositorydatabase.BusStopDatabase;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationTokenSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepository {
    private RetrofitService retrofitService;
    private RetrofitClient retrofitClient;
    private Context context;
    private BusStopDatabase busStopDatabase;

    public MainRepository(Application application){
        this.context = application.getApplicationContext();
        retrofitClient = RetrofitClient.getInstance();
        retrofitService = RetrofitClient.getRetrofitService();
        busStopDatabase = BusStopDatabase.getInstance(application);
    }

    public void fetchRouteData(ApiCallback<PostResult> callback){
        Call<PostResult> routeDataCall = retrofitService.getRouteData("All");
        routeDataCall.enqueue(new Callback<PostResult>(){
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Server Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void fetchBusArrivalData(String busStopId, ApiCallback<StopPostResults> callback){
        Call<StopPostResults> arrivalDataCall = retrofitService.getBusArrivalData(busStopId);
        arrivalDataCall.enqueue(new Callback<StopPostResults>(){
            @Override
            public void onResponse(Call<StopPostResults> call, Response<StopPostResults> response) {
                if(response.isSuccessful()){
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Server Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<StopPostResults> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    public void updateFavoriteStatus(BusStop busStop){
        new Thread(() -> {
            busStopDatabase.busStopDao().updateBusStop(busStop);
        }).start();
    }

    //TODO 퍼미션 체크 함수 만들기.
    @SuppressLint("MissingPermission")
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationClient, LocationCallback callback){
        CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.getToken())
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        String locationString = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
                        //Toast.makeText(context.getApplicationContext(), "위치 확인함", Toast.LENGTH_SHORT).show();
                        callback.onLocationResult(locationString);
                    } else {
                        callback.onError("위치 정보를 가져올 수 없습니다");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
    public void getFavoriteBusStops(ApiCallback<List<BusStop>> callback){
        new Thread(() -> {
            List<BusStop> busStops = busStopDatabase.busStopDao().getFavoriteBusStops();
            if(busStops != null){
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(busStops));
            } else {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError("즐겨찾기 목록을 가져올 수 없습니다"));
            }
        }).start();
    }


    public interface ApiCallback<T>{
        void onSuccess(T result);
        void onError(String error);
    }

    public interface LocationCallback{
        void onLocationResult(String locationString);
        void onError(String error);
    }
}
