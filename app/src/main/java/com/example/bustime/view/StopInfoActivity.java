package com.example.bustime.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bustime.adapter.BusStopInfoAdapter;
import com.example.bustime.viewmodel.MainViewModel;
import com.example.bustime.R;

public class StopInfoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    TextView busStopName;
    TextView busStopNumber;
    TextView busStopEngName;
    RecyclerView recyclerBusStopInfoView;
    SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel viewModel;
    private int busStopId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
        EdgeToEdge.enable(this);
        busStopId = getBusStopIdFromMainActivity();
        initializeUI();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setupObservers();

        updateBusStopData(busStopId);
    }

    private void initializeUI() {
        busStopNumber = findViewById(R.id.bus_stop_id);
        busStopName = findViewById(R.id.bus_stop_name);
        busStopEngName = findViewById(R.id.bus_stop_eng_name);

        swipeRefreshLayout = findViewById(R.id.stops_swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerBusStopInfoView = findViewById(R.id.recycler_busStopInfo);
        recyclerBusStopInfoView.setLayoutManager(new LinearLayoutManager(this));
        recyclerBusStopInfoView.setHasFixedSize(true); // 성능 최적화

        busStopNumber.setText(String.valueOf(busStopId));
        busStopName.setText(getIntent().getStringExtra("BUS_STOP_NAME"));
        busStopEngName.setText(getIntent().getStringExtra("BUS_STOP_ENG_NAME"));
    }

    private void setupObservers() {
        viewModel.getStopBusResults().observe(this, stopBusResults -> {
            if (stopBusResults != null && !stopBusResults.isEmpty()) {
                recyclerBusStopInfoView.setAdapter(new BusStopInfoAdapter(stopBusResults));
            } else {
                // Empty state를 표시하거나 기본 메시지 출력
                Log.e("StopInfoActivity", "No data received from API");
            }
        });
    }

    private int getBusStopIdFromMainActivity() {
        busStopId = getIntent().getIntExtra("BUS_STOP_ID", -1);
        // 데이터가 제대로 전달되지 않은 경우를 대비해 기본값 또는 예외처리
        if (busStopId == -1) {
            Log.e("StopInfoActivity", "Invalid BUS_STOP_ID");
            finish(); // Activity 종료 또는 오류 처리
        }
        return busStopId;
    }

    private void updateBusStopData(int busStopId) {
        viewModel.fetchBusArrivalData(String.valueOf(busStopId));
    }



    @Override
    public void onRefresh() {
        updateBusStopData(busStopId);
        viewModel.getStopBusResults().observe(this, stopBusResults -> {
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}