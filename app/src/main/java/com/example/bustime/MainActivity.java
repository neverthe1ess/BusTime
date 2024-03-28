package com.example.bustime;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.bustime.repository.api.RetrofitClient.getRetrofitService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bustime.repository.api.RetrofitClient;
import com.example.bustime.repository.api.RetrofitService;
import com.example.bustime.repository.api.dto.routeData.PostResult;
import com.example.bustime.repository.api.dto.stopData.StopBusResults;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    MenuItem mSearch;
    TextView textView, testSearch;
    SwipeRefreshLayout swipeRefreshLayout;

    // private final static String SECERT_KEY = "test";
    private RetrofitService retrofitService;
    private StopPostResults postResults;
    private List<StopBusResults> stopBusResultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        textView = findViewById(R.id.textView);
        testSearch = findViewById(R.id.testSearch);
        stopBusResultsList = new ArrayList<>();

        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        retrofitService = getRetrofitService();

        // RetrofitService service1 = retrofitService.getBusArrivalData();


        // 네트워크 요청 예제
        Call<PostResult> routeDataCall = retrofitService.getRouteData("All");

        routeDataCall.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    PostResult result = response.body();
                    Log.e(TAG, "onResponse: 성공, 결과\n" + result.toString());
                } else { // 3xx ~ 4xx
                    try {
                        textView.setText(response.errorBody().string());
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


        Call<StopPostResults> arrivalDataCall = retrofitService.getBusArrivalData("370000023");

        arrivalDataCall.enqueue(new Callback<StopPostResults>(){
            @Override
            public void onResponse(Call<StopPostResults> call, Response<StopPostResults> response) {
                if (response.isSuccessful()) {
                    postResults = response.body();
                    stopBusResultsList = postResults.getStArriveResults();

                    RecyclerView mRecycleView = (RecyclerView) findViewById(R.id.recyclerView);
                    CustomAdapter mCustomAdapter = new CustomAdapter(postResults, stopBusResultsList);
                    mRecycleView.setAdapter(mCustomAdapter);
                    mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    Log.e(TAG, "onResponse: 성공, 결과\n" + postResults.toString());
                } else { // 3xx ~ 4xx
                    try {
                        textView.setText(response.errorBody().string());
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        mSearch = menu.findItem(R.id.search);


        SearchView sv = (SearchView) mSearch.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //testSearch.setText(query + "검색함!");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                testSearch.setText(newText + "검색함!");
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRefresh() {

    }
}
