package com.huawei.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.navigation.NavigationView;
import com.huawei.bookingapp.fragment.HomeFragment;
import com.huawei.bookingapp.fragment.ProfileFragment;
import com.huawei.bookingapp.model.User;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        setNavDrawer();
        navigationView.setNavigationItemSelectedListener(this);
        if (fragmentTransaction == null)
        {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new HomeFragment());
            fragmentTransaction.commit();
        }

        user = (User) getIntent().getSerializableExtra("User");

    }

    private void setNavDrawer(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.menu_home);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.isDrawerIndicatorEnabled();
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_home :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;

            case R.id.menu_add_home :
                navigationView.setCheckedItem(R.id.menu_home);
                Intent addHouse = new Intent(HomeActivity.this, AddHouseActivity.class);
                startActivity(addHouse);
                finish();
                break;

            case R.id.menu_profile_detail :
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.menu_logout :
                mSharedPreferences = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                mEditor = mSharedPreferences.edit();
                mEditor.putBoolean("login", false);
                mEditor.apply();

                Intent i = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}