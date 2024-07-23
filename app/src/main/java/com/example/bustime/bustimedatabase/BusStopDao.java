package com.example.bustime.bustimedatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BusStopDao {
    @Query("SELECT * FROM BusStop")
    List<BusStop> getAllBusStops();

    @Query("SELECT * FROM BusStop WHERE busStopId = :busStopId")
    BusStop getBusStopById(int busStopId);

}
