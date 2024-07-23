package com.example.bustime.bustimedatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BusStop.class}, version = 1)
public abstract class BusStopDatabase extends RoomDatabase {
    private static BusStopDatabase INSTANCE;

    public abstract BusStopDao busStopDao();

    public static synchronized BusStopDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    BusStopDatabase.class,
                    "testDataBase3.db")
                    .createFromAsset("database/BusStop.db")
                    .build();
        }
        return INSTANCE;
    }
}

