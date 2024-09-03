package com.example.bustime;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StopInfoActivity extends AppCompatActivity {
    TextView busStopName;
    TextView busStopNumber;
    TextView busStopEngName;
    RecyclerView recyclerBusStopInfoView;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
        EdgeToEdge.enable(this);

        busStopNumber = findViewById(R.id.bus_stop_id);
        busStopName = findViewById(R.id.bus_stop_name);
        busStopEngName = findViewById(R.id.bus_stop_eng_name);

        recyclerBusStopInfoView = findViewById(R.id.recycler_busStopInfo);
        recyclerBusStopInfoView.setLayoutManager(new LinearLayoutManager(this));
        recyclerBusStopInfoView.setHasFixedSize(true); // 성능 최적화

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // 데이터가 제대로 전달되지 않은 경우를 대비해 기본값 또는 예외처리
        int busStopId = getIntent().getIntExtra("BUS_STOP_ID", -1);
        if (busStopId == -1) {
            Log.e("StopInfoActivity", "Invalid BUS_STOP_ID");
            finish(); // Activity 종료 또는 오류 처리
            return;
        }

        viewModel.fetchBusArrivalData(String.valueOf(busStopId));
        viewModel.getStopBusResults().observe(this, stopBusResults -> {
            if (stopBusResults != null && !stopBusResults.isEmpty()) {
                recyclerBusStopInfoView.setAdapter(new BusInfoAdapter(stopBusResults));
            } else {
                // Empty state를 표시하거나 기본 메시지 출력
                Log.e("StopInfoActivity", "No data received from API");
            }
        });
        busStopNumber.setText(String.valueOf(busStopId));
        busStopName.setText(getIntent().getStringExtra("BUS_STOP_NAME"));
        busStopEngName.setText(getIntent().getStringExtra("BUS_STOP_ENG_NAME"));
    }
}