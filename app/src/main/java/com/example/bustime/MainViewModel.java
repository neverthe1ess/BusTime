package com.example.bustime;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bustime.repository.api.dto.stopData.StopBusResults;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<StopBusResults>> stopBusResultsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> locationLiveData = new MutableLiveData<>();
    private MainRepository repository;

    public MainViewModel(Application application) {
        super(application);
        repository = new MainRepository(application);
    }
    public LiveData<List<StopBusResults>> getStopBusResults(){
        return stopBusResultsLiveData;
    }
    public LiveData<String> getLocation(){
        return locationLiveData;
    }

    public void fetchBusArrivalData(String busStopId){
        repository.fetchBusArrivalData(busStopId, new MainRepository.ApiCallback<StopPostResults>(){
            @Override
            public void onSuccess(StopPostResults result) {
                stopBusResultsLiveData.setValue(result.getStArriveResults());
                Log.e(TAG, "onResponse: 성공, 결과 " + result.toString());
            }

            @Override
            public void onError(String error){
                Log.e(TAG, "fetchBusArrivalData: 실패 - " + error);
            }
        });
    }
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationClient) {
        repository.getCurrentLocation(fusedLocationClient, new MainRepository.LocationCallback() {
            @Override
            public void onLocationResult(String locationString) {
                locationLiveData.setValue(locationString);
            }

            @Override
            public void onError(String error) {
                locationLiveData.setValue(error);
            }
        });
    }

}
