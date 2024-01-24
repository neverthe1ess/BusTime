package com.example.bustime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.gson.annotations.SerializedName;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* REST API 응답데이터 구조
    {
        "stationNm" : "안동대학교",
        "mobiNum": "540410",
    }
     */

    public class PostResult {
        @SerializedName("stationNm")
        private String stationNm;

        @SerializedName("mobiNum")
        private String mobiNum;

        @Override
        public String toString(){
            return "stationNm = " + stationNm + "mobiNum = "+ mobiNum;
        }

    }

    public interface RetrofitService {

        @GET("api/route/station/getDataList?routeId=354300491/{post}")
        Call<PostResult> getPosts(@Path("post") String post);

    }
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bus.andong.go.kr:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RetrofitService service1 = retrofit.create(RetrofitService.class);
    Call<PostResult> call = service1.getPosts("1");
    call.enqueue(){


    }






}