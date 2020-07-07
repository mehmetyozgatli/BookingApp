package com.huawei.bookingapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class House implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int house_id;

    int house_provider_id;
    String house_title;
    String house_type;
    String house_room;
    String house_address;
    String house_salary;
    String house_heating;
    String house_city;
    String house_town;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    byte[] house_image;

    public House(){

    }

    public House(String house_title, String house_type, String house_room, String house_address,
                 String house_salary, String house_heating, String house_city, String house_town, byte[] house_image) {
        this.house_title = house_title;
        this.house_type = house_type;
        this.house_room = house_room;
        this.house_address = house_address;
        this.house_salary = house_salary;
        this.house_heating = house_heating;
        this.house_city = house_city;
        this.house_town = house_town;
        this.house_image = house_image;
    }

    @NonNull
    public int getHouse_id() {
        return house_id;
    }

    public String getHouse_title() {
        return house_title;
    }

    public String getHouse_type() {
        return house_type;
    }

    public String getHouse_room() {
        return house_room;
    }

    public String getHouse_address() {
        return house_address;
    }

    public String getHouse_salary() {
        return house_salary;
    }

    public String getHouse_heating() {
        return house_heating;
    }

    public String getHouse_city() {
        return house_city;
    }

    public String getHouse_town() {
        return house_town;
    }

    public byte[] getHouse_image() { return house_image; }

    public int getHouse_provider_id() { return house_provider_id; }



    public void setHouse_id(@NonNull int house_id) {
        this.house_id = house_id;
    }

    public void setHouse_title(String house_title) {
        this.house_title = house_title;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public void setHouse_room(String house_room) {
        this.house_room = house_room;
    }

    public void setHouse_address(String house_address) {
        this.house_address = house_address;
    }

    public void setHouse_salary(String house_salary) {
        this.house_salary = house_salary;
    }

    public void setHouse_heating(String house_heating) {
        this.house_heating = house_heating;
    }

    public void setHouse_city(String house_city) {
        this.house_city = house_city;
    }

    public void setHouse_town(String house_town) {
        this.house_town = house_town;
    }

    public void setHouse_image(byte[] house_image) {
        this.house_image = house_image;
    }

    public void setHouse_provider_id(int house_provider_id) { this.house_provider_id = house_provider_id; }
}
