package com.example.bustime.repository.api.dto.stopData;


public class Parameter
{
    private String stationId;

    public String getStationId ()
    {
        return stationId;
    }

    public void setStationId (String stationId)
    {
        this.stationId = stationId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stationId = "+stationId+"]";
    }
}