package com.example.bustime;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.bustime.repository.api.RetrofitClient.getRetrofitService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.bustime.repository.api.RetrofitClient;
import com.example.bustime.repository.api.RetrofitService;
import com.example.bustime.repository.api.dto.routeData.PostResult;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView textView;

    // private final static String SECERT_KEY = "test";
    private RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        textView = findViewById(R.id.textView);

        RetrofitClient retrofitClient = RetrofitClient.getInstance();

        retrofitService = getRetrofitService();

        // RetrofitService service1 = retrofitService.getBusArrivalData();


        // 네트워크 요청 예제
        Call<PostResult> call = retrofitService.getRouteData("All");

        call.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    PostResult result = response.body();
                    textView.setText(result.toString());
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
    }
}
