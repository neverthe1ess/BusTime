package com.example.bustime.bustimedatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BusStopDao {
    @Query("SELECT * FROM BusStop")
    List<BusStop> getAllBusStops();

    @Query("SELECT * FROM BusStop WHERE isFavorite = 1")
    List<BusStop> getFavoriteBusStops();

    @Query("SELECT * FROM BusStop WHERE busStopId = :busStopId")
    BusStop getBusStopById(int busStopId);

    @Update
    void updateBusStop(BusStop busStop);

}
