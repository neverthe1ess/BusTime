package com.example.bustime;

import static com.example.bustime.repository.api.RetrofitClient.getRetrofitService;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initializeUI();
        setupObservers();


        stopBusResultsList = new ArrayList<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            viewModel.getLocation();
        }
        setupBottomNavigation();
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

    private void setupObservers(){
        viewModel.getStopBusResults().observe(this, stopBusResults -> {
            // TODO UPDATE UI WITH stopBusResults
            // DB 반영
            Log.e(TAG, "setupObservers: " + stopBusResults.toString());
        });
        viewModel.getLocation().observe(this, location -> {
            textView.setText(location);
        });
    }

    private void setupBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });

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
        //viewModel.fetchRouteData();
        viewModel.fetchBusArrivalData("370000023");
        swipeRefreshLayout.setRefreshing(false);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                //isGranted가 ActivityResultCallback 구현임. 람다식
                if(isGranted){
                    viewModel.getLocation();
                } else {
                    Toast.makeText(MainActivity.this, "위치 권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
                }
            });


}
