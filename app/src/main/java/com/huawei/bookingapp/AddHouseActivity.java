package com.huawei.bookingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.huawei.bookingapp.data.DataConverter;
import com.huawei.bookingapp.data.HouseDAO;
import com.huawei.bookingapp.data.HouseDatabase;
import com.huawei.bookingapp.fragment.HomeFragment;
import com.huawei.bookingapp.fragment.ProfileFragment;
import com.huawei.bookingapp.model.House;
import com.huawei.bookingapp.model.User;

import java.io.IOException;
import java.util.Objects;

public class AddHouseActivity extends AppCompatActivity {

    Spinner houseTypeSpinner, houseRoomNumberSpinner, houseCity, houseTown;
    ImageView houseImage;
    EditText input_houseTitle, input_houseAddress, input_houseSalary, input_houseHeatingSystem;

    Bitmap selectedImage;
    HouseDAO houseDAO;

    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        houseImage = findViewById(R.id.house_imageView);
        input_houseTitle = findViewById(R.id.input_houseTitle);
        input_houseAddress = findViewById(R.id.input_houseAddress);
        input_houseSalary = findViewById(R.id.input_houseSalary);
        input_houseHeatingSystem = findViewById(R.id.input_houseHeatingSystem);

        houseDAO = HouseDatabase.getDBInstance(this).getHouseDao();

        houseTypeSpinner = findViewById(R.id.spinner_houseType);
        ArrayAdapter<CharSequence> houseTypeAdapter = ArrayAdapter.createFromResource(this, R.array.house_type, android.R.layout.simple_spinner_item);
        houseTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        houseTypeSpinner.setAdapter(houseTypeAdapter);

        houseRoomNumberSpinner = findViewById(R.id.spinner_houseRoomCount);
        ArrayAdapter<CharSequence> RoomNumberAdapter = ArrayAdapter.createFromResource(this, R.array.house_room_number, android.R.layout.simple_spinner_item);
        RoomNumberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        houseRoomNumberSpinner.setAdapter(RoomNumberAdapter);

        houseCity = findViewById(R.id.spinner_houseCity);
        ArrayAdapter<CharSequence> CityAdapter = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        CityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        houseCity.setAdapter(CityAdapter);

        houseTown = findViewById(R.id.spinner_houseTown);
        ArrayAdapter<CharSequence> TownAdapter = ArrayAdapter.createFromResource(this, R.array.town, android.R.layout.simple_spinner_item);
        TownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        houseTown.setAdapter(TownAdapter);


        if (getIntent().getByteArrayExtra("image") != null) {
            houseImage.setImageBitmap(DataConverter.convertByteArray2Image(getIntent().getByteArrayExtra("image")));
        }

        if (getIntent().getStringExtra("title") != null) {
            input_houseTitle.setText(getIntent().getStringExtra("title"));
        }

        if (getIntent().getStringExtra("type") != null) {
            if (Objects.equals(getIntent().getStringExtra("type"), "Room"))
                houseTypeSpinner.setSelection(0);
            if (Objects.equals(getIntent().getStringExtra("type"), "Build"))
                houseTypeSpinner.setSelection(1);
        }

        if (getIntent().getStringExtra("room") != null) {
            if (Objects.equals(getIntent().getStringExtra("room"), "1"))
                houseRoomNumberSpinner.setSelection(0);
            if (Objects.equals(getIntent().getStringExtra("type"), "2"))
                houseRoomNumberSpinner.setSelection(1);
            if (Objects.equals(getIntent().getStringExtra("type"), "3"))
                houseRoomNumberSpinner.setSelection(2);
            if (Objects.equals(getIntent().getStringExtra("type"), "4"))
                houseRoomNumberSpinner.setSelection(3);
        }

        if (getIntent().getStringExtra("address") != null) {
            input_houseAddress.setText(getIntent().getStringExtra("address"));
        }

        if (getIntent().getStringExtra("salary") != null) {
            input_houseSalary.setText(getIntent().getStringExtra("salary"));
        }

        if (getIntent().getStringExtra("heating") != null) {
            input_houseHeatingSystem.setText(getIntent().getStringExtra("heating"));
        }

        if (getIntent().getStringExtra("city") != null) {
            if (Objects.equals(getIntent().getStringExtra("city"), "Istanbul"))
                houseCity.setSelection(0);
            if (Objects.equals(getIntent().getStringExtra("city"), "Mersin"))
                houseCity.setSelection(1);
        }

        if (getIntent().getStringExtra("town") != null) {
            if (Objects.equals(getIntent().getStringExtra("town"), "Ataşehir"))
                houseTown.setSelection(0);
            if (Objects.equals(getIntent().getStringExtra("town"), "Kadıköy"))
                houseTown.setSelection(1);
            if (Objects.equals(getIntent().getStringExtra("town"), "Yenişehir"))
                houseTown.setSelection(2);
            if (Objects.equals(getIntent().getStringExtra("town"), "Mezitli"))
                houseTown.setSelection(3);
        }

    }

    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            try {

                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    houseImage.setImageBitmap(selectedImage);

                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    houseImage.setImageBitmap(selectedImage);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveHouse(View view) {

        House getHouseUsingID = houseDAO.getHouseUsingID(getIntent().getIntExtra("house_id", 99999999));

        if (input_houseTitle.getText().toString().isEmpty() ||
                input_houseAddress.getText().toString().isEmpty() ||
                input_houseSalary.getText().toString().isEmpty() ||
                input_houseHeatingSystem.getText().toString().isEmpty()) {
            Toast.makeText(this, "House Data missing!", Toast.LENGTH_LONG).show();
        }

        if (getHouseUsingID != null) {

            if (selectedImage != null)
            {
                getHouseUsingID.setHouse_image(DataConverter.convertImage2ByteArray(selectedImage));
            }

            getHouseUsingID.setHouse_title(input_houseTitle.getText().toString());
            getHouseUsingID.setHouse_type(houseTypeSpinner.getSelectedItem().toString());
            getHouseUsingID.setHouse_room(houseRoomNumberSpinner.getSelectedItem().toString());
            getHouseUsingID.setHouse_address(input_houseAddress.getText().toString());
            getHouseUsingID.setHouse_salary(input_houseSalary.getText().toString());
            getHouseUsingID.setHouse_heating(input_houseHeatingSystem.getText().toString());
            getHouseUsingID.setHouse_city(houseCity.getSelectedItem().toString());
            getHouseUsingID.setHouse_town(houseTown.getSelectedItem().toString());

            houseDAO.update(getHouseUsingID);
            Toast.makeText(this, "House Updated!", Toast.LENGTH_LONG).show();

        } else {

            House house = new House();

            mSharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
            int userID = mSharedPreferences.getInt("userID", 1);

            house.setHouse_provider_id(userID);
            house.setHouse_image(DataConverter.convertImage2ByteArray(selectedImage));
            house.setHouse_title(input_houseTitle.getText().toString());
            house.setHouse_type(houseTypeSpinner.getSelectedItem().toString());
            house.setHouse_room(houseRoomNumberSpinner.getSelectedItem().toString());
            house.setHouse_address(input_houseAddress.getText().toString());
            house.setHouse_salary(input_houseSalary.getText().toString());
            house.setHouse_heating(input_houseHeatingSystem.getText().toString());
            house.setHouse_city(houseCity.getSelectedItem().toString());
            house.setHouse_town(houseTown.getSelectedItem().toString());

            houseDAO.insert(house);
            Toast.makeText(this, "House Added!", Toast.LENGTH_LONG).show();

        }
    }

    public void deleteHouse(View view) {
        houseDAO.deleteById(getIntent().getIntExtra("house_id", 1));
        Toast.makeText(this, "House Deleted!", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddHouseActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}