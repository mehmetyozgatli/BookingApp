package com.huawei.bookingapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.bookingapp.R;
import com.huawei.bookingapp.adapter.RecyclerViewAdapter;
import com.huawei.bookingapp.data.HouseDAO;
import com.huawei.bookingapp.data.HouseDatabase;

import java.util.Objects;

public class HomeFragment extends Fragment {

    View mView;
    RecyclerView recyclerView;
    HouseDAO houseDAO;
    SharedPreferences mSharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = mView.findViewById(R.id.mRecyclerView);

        mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        int userID = mSharedPreferences.getInt("userID", 1);

        houseDAO = HouseDatabase.getDBInstance(getActivity()).getHouseDao();
        RecyclerViewAdapter houseRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), houseDAO.getAllHouse(userID));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(houseRecyclerViewAdapter);

        return mView;
    }
}
