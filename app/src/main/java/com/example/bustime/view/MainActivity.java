package com.example.bustime.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bustime.viewmodel.MainViewModel;
import com.example.bustime.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private MenuItem mSearch;
    private TextView gpsLocationTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FusedLocationProviderClient fusedLocationClient;
    private BottomNavigationView bottomNavigationView;
    private MainViewModel viewModel;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupToolbar();
        setupObservers();
        startLocationTracking();
        setupBottomNavigation();
    }

    private void startLocationTracking() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            viewModel.getCurrentLocation(fusedLocationClient);
        }
    }

    private void setupToolbar() {
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if(getSupportActionBar() != null){
            // 타이틀(BUS_TIME) 제거
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        gpsLocationTextView = findViewById(R.id.gpsLocation);
    }

    private void setupObservers(){
        viewModel.getStopBusResults().observe(this, stopBusResults -> {
            Log.e(TAG, "setupObservers: " + stopBusResults.toString());
        });
        viewModel.getLocation().observe(this, location -> {
            gpsLocationTextView.setText(location);
        });
    }

    private void setupBottomNavigation(){
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
                swipeRefreshLayout.setEnabled(true);
            } else if(item.getItemId() == R.id.navigation_favorites){
                selectedFragment = new BusStopFragment();
                swipeRefreshLayout.setEnabled(true);
            } else if(item.getItemId() == R.id.navigation_timetable){
                selectedFragment = new TimeTableFragment();
                swipeRefreshLayout.setEnabled(false);
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
        if (sv != null) {
            sv.setQueryHint(" 정류장 검색");
            sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    viewModel.setSearchQuery(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    viewModel.setSearchQuery(newText);
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {
        startLocationTracking();
        swipeRefreshLayout.setRefreshing(false);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                //isGranted가 ActivityResultCallback 구현임. 람다식
                if(isGranted){
                   viewModel.getCurrentLocation(fusedLocationClient);
                } else {
                    Toast.makeText(MainActivity.this, "정확한 위치 권한을 허용해주세요", Toast.LENGTH_SHORT).show();
                }
            });
}
