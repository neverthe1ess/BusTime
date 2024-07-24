package com.example.bustime.bustimedatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class BusStop {
    @PrimaryKey
    public int busStopId;

    @ColumnInfo(name = "station_kor_name")
    public String stationName;

    @ColumnInfo(name = "station_eng_name")
    public String stationEngName;

    @ColumnInfo(name = "latitude")
    public double lat;

    @ColumnInfo(name = "longitude")
    public double lon;

    @ColumnInfo(name = "gov_name")
    public String govName;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

}
