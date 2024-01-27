package com.example.bustime.repository.api.dto.routeData;

import com.example.bustime.repository.api.dto.routeData.BusResults;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class PostResult {

    @SerializedName("results")
    private BusResults[] results;

    @SerializedName("description")
    private String description;

    @Override
    public String toString(){
        return "results = " + Arrays.toString(results) + "\n설명 = "+ description;
    }

}