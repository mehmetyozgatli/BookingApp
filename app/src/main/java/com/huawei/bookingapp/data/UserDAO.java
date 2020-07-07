package com.huawei.bookingapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.huawei.bookingapp.model.User;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User where userMail= :mail and userPassword= :password")
    User getUser(String mail, String password);

    @Query("SELECT * FROM User where id= :userID")
    User getUserUsingID(int userID);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

}
