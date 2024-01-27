package com.example.bustime.repository.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//싱글톤 패턴
//사용법
//먼저 getInstance로 retrofit빌드 해주고,
//getRetrofitAPI를 사용하면 된다.

//기존의 call

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private static RetrofitService retrofitService;
    private static final String BASE_URL = "http://bus.andong.go.kr:8080/";

    private RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitService = retrofit.create(RetrofitService.class);
    }

    public static RetrofitClient getInstance() {
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public static RetrofitService getRetrofitService(){
        return retrofitService;
    }
}
