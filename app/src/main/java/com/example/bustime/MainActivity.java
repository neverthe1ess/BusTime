package com.example.bustime;

import static com.example.bustime.repository.api.RetrofitClient.getRetrofitService;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bustime.repositorydatabase.BusStop;
import com.example.bustime.repositorydatabase.BusStopDatabase;
import com.example.bustime.repository.api.RetrofitClient;
import com.example.bustime.repository.api.RetrofitService;
import com.example.bustime.repository.api.dto.routeData.PostResult;
import com.example.bustime.repository.api.dto.stopData.StopBusResults;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MenuItem mSearch;
    TextView textView;
    SwipeRefreshLayout swipeRefreshLayout;

    // private final static String SECERT_KEY = "test";
    private RetrofitService retrofitService;
    private StopPostResults postResults;
    private List<StopBusResults> stopBusResultsList;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String TAG = "MainActivity";
    private BusStopDatabase busStopDatabase;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();
        stopBusResultsList = new ArrayList<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            getCurrentLocation();
        }

        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitService = getRetrofitService();


        uiUpdateByDatabase();
        //dataUpdate();

    }

    private void uiUpdateByDatabase(){
        busStopDatabase = BusStopDatabase.getInstance(this);
        //fetchBusStopsData();
        setupBottomNavigation();
    }

    private void setupBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if(item.getItemId() == R.id.navigation_favorites){
                    selectedFragment = new BusStopFragment();
                }

                if(selectedFragment != null){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, selectedFragment)
                            .commit();
                }
                return true;
            }
        });

    }

    private void fetchBusStopsData(){
        new Thread(() -> {
            List<BusStop> busStops = busStopDatabase.busStopDao().getFavoriteBusStops();

            runOnUiThread(() -> {
                //RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                StopsAdpater stopsAdpater = new StopsAdpater(busStops, new StopsAdpater.FavoriteClickListener() {
                    @Override
                    public void onFavoriteClick(BusStop busStop) {
                        updateFavoriteInDatabase(busStop);
                    }
                });
                //TODO 수정 필요
                //recyclerView.setAdapter(stopsAdpater);
                //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            });

        }).start();
    }

    private void updateFavoriteInDatabase(BusStop busStop){
        new Thread(() -> {
            busStopDatabase.busStopDao().updateBusStop(busStop);
        }).start();
    }

    private void dataUpdate() {
        fetchRouteData();
        fetchBusArrivalData("370000023");
    }

    private void initializeUI() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        textView = findViewById(R.id.gpsLocation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        mSearch = menu.findItem(R.id.search);

        SearchView sv = (SearchView) mSearch.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        busInfoUpdate();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void busInfoUpdate(){
        //dataUpdate();
    }

    private void fetchRouteData() {
        Call<PostResult> routeDataCall = retrofitService.getRouteData("All");
        routeDataCall.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) { // HTTP 200 성공
                    PostResult result = response.body();
                    Log.e(TAG, "onResponse: 성공, 결과\n" + result.toString());
                } else { // 3xx ~ 4xx 서버 사이드 오류
                    try {
                        Log.e(TAG, "onResponse: 실패 - 3xx ~ 4xx 사이의 서버 사이드 오류 + " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e(TAG, "onResponse: 실패");
                }
            }
            @Override
            public void onFailure(Call<PostResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void fetchBusArrivalData(String busStopId) {
        Call<StopPostResults> arrivalDataCall = retrofitService.getBusArrivalData(busStopId);
        arrivalDataCall.enqueue(new Callback<StopPostResults>(){
            @Override
            public void onResponse(Call<StopPostResults> call, Response<StopPostResults> response) {
                if (response.isSuccessful()) {
                    postResults = response.body();
                    stopBusResultsList = postResults.getStArriveResults();

                    // TODO 나중에 추출 하기
//                    RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
//                    CustomAdapter mCustomAdapter = new CustomAdapter(postResults, stopBusResultsList);
//                    mRecycleView.setAdapter(mCustomAdapter);
//                    mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    Log.e(TAG, "onResponse: 성공, 결과\n" + postResults.toString());
                } else { // 3xx ~ 4xx
                    try {
                        Log.e(TAG, "onResponse: 실패 - 3xx ~ 4xx 사이의 서버 사이드 오류 + " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Log.e(TAG, "onResponse: 실패");
                }
            }
            @Override
            public void onFailure(Call<StopPostResults> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                //isGranted가 ActivityResultCallback 구현임. 람다식
                if(isGranted){
                    getCurrentLocation();
                } else {
                    Toast.makeText(MainActivity.this, "위치 권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
                }
            });

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if(location != null){
                        textView.setText("Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude());
                    } else {
                        Toast.makeText(MainActivity.this, "위치 정보를 가져올 수 없습니다", Toast.LENGTH_SHORT).show();
                    }});
    }
}
