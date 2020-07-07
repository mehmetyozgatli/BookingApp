package com.huawei.bookingapp.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.huawei.bookingapp.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDao();

}
