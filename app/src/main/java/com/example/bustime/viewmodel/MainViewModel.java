package com.example.bustime.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bustime.MainRepository;
import com.example.bustime.repository.api.dto.stopData.StopBusResults;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;
import com.example.bustime.repositorydatabase.BusStop;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<List<StopBusResults>> stopBusResultsLiveData = new MutableLiveData<>();
    private MutableLiveData<String> locationLiveData = new MutableLiveData<>();
    private MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private MutableLiveData<Boolean> favoriteUpdateStatus = new MutableLiveData<>();
    private MutableLiveData<List<BusStop>> favoriteBusStops = new MutableLiveData<>();
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
    public LiveData<String> getSearchQuery(){
        return searchQuery;
    }
    public LiveData<Boolean> getFavoriteUpdateStatus() {return favoriteUpdateStatus; }
    public LiveData<List<BusStop>> getFavoriteBusStops(){return favoriteBusStops;}

    public void fetchBusArrivalData(String busStopId){
        repository.fetchBusArrivalData(busStopId, new MainRepository.ApiCallback<StopPostResults>(){
            @Override
            public void onSuccess(StopPostResults result) {
                stopBusResultsLiveData.setValue(result.getStArriveResults());
                Log.e(TAG, "onResponse: 성공, 결과 " + result.toString());
            }

            @Override
            public void onError(String error){
                Toast.makeText(getApplication(), "네트워크 연결 오류", Toast.LENGTH_SHORT).show();
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

    public void updateFavoriteStatus(BusStop busStop) {
        repository.updateFavoriteStatus(busStop);
    }

    public void fetchFavoriteBusStops(){
        // TODO 인터페이스 만들기
        repository.getFavoriteBusStops(new MainRepository.ApiCallback<List<BusStop>>(){
            @Override
            public void onSuccess(List<BusStop> result) {
                favoriteBusStops.setValue(result);
            }
            //TODO 생각해보기
            @Override
            public void onError(String error) {

            }
        });
    }


    public void setSearchQuery(String query){
        searchQuery.setValue(query);
    }
}
