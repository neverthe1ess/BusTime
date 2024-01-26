package com.example.bustime;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://bus.andong.go.kr:8080/";
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        textView = findViewById(R.id.textView);

        // Retrofit 초기화
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Retrofit 인터페이스를 사용하여 서비스 생성
        RetrofitService service = retrofit.create(RetrofitService.class);

        // 네트워크 요청 예제
        Call<PostResult> call = service.getPosts("370000023");

        call.enqueue(new Callback<PostResult>() {
            @Override
            public void onResponse(Call<PostResult> call, Response<PostResult> response) {
                if (response.isSuccessful()) {
                    PostResult result = response.body();
                    textView.setText(result.toString());
                    Log.e(TAG, "onResponse: 성공, 결과\n" + result.toString());
                } else {
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
