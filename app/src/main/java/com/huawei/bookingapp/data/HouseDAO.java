package com.huawei.bookingapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.huawei.bookingapp.model.House;
import com.huawei.bookingapp.model.User;

import java.util.List;

@Dao
public interface HouseDAO {

    @Query("SELECT * FROM House where house_provider_id= :userID")
    List<House> getAllHouse(int userID);

    @Query("SELECT * FROM House where house_id= :houseID")
    House getHouseUsingID(int houseID);

    @Insert
    void insert(House house);

    @Update
    void update(House house);

    @Query("DELETE FROM House where house_id= :houseID")
    void deleteById(int houseID);

    @Delete
    void deleteAll(House house);
}
