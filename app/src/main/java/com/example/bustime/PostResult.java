package com.example.bustime;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;


// result nested 배열 처리 하기


public class PostResult {

    @SerializedName("description")
    private String description;

    @Override
    public String toString(){
        return "results = " + results + "\n설명 = "+ description;
    }

}