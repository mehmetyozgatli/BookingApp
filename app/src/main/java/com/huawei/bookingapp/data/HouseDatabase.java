package com.huawei.bookingapp.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.huawei.bookingapp.model.House;

@Database(entities = {House.class}, version = 1, exportSchema = false)
public abstract class HouseDatabase extends RoomDatabase {

    private static HouseDatabase houseDB = null;

    public abstract HouseDAO getHouseDao();

    public static synchronized HouseDatabase getDBInstance(Context context){
        if (houseDB == null){
            houseDB = Room.databaseBuilder(context.getApplicationContext(), HouseDatabase.class, "house")
                    .allowMainThreadQueries().build();
        }
        return houseDB;
    }
}
