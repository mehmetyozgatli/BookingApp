package com.huawei.bookingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.bookingapp.AddHouseActivity;
import com.huawei.bookingapp.R;
import com.huawei.bookingapp.data.DataConverter;
import com.huawei.bookingapp.data.HouseDAO;
import com.huawei.bookingapp.data.HouseDatabase;
import com.huawei.bookingapp.model.House;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<House> data;
    Context mContext;
    HouseDAO houseDAO;
    SharedPreferences mSharedPreferences;

    public RecyclerViewAdapter(Context context, List<House> house){
        data = house;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        House house = data.get(position);
        holder.houseImage.setImageBitmap(DataConverter.convertByteArray2Image(house.getHouse_image()));
        holder.houseTitle.setText(house.getHouse_title());
        holder.houseAddress.setText(house.getHouse_address());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSharedPreferences = mContext.getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                int userID = mSharedPreferences.getInt("userID", 1);
                houseDAO = HouseDatabase.getDBInstance(mContext).getHouseDao();
                List<House> userHouse = houseDAO.getAllHouse(userID);

                Intent i = new Intent(mContext, AddHouseActivity.class);
                i.putExtra("house_id", userHouse.get(position).getHouse_id());
                i.putExtra("image", userHouse.get(position).getHouse_image());
                i.putExtra("title", userHouse.get(position).getHouse_title());
                i.putExtra("type", userHouse.get(position).getHouse_type());
                i.putExtra("room", userHouse.get(position).getHouse_room());
                i.putExtra("address", userHouse.get(position).getHouse_address());
                i.putExtra("salary", userHouse.get(position).getHouse_salary());
                i.putExtra("heating", userHouse.get(position).getHouse_heating());
                i.putExtra("city", userHouse.get(position).getHouse_city());
                i.putExtra("town", userHouse.get(position).getHouse_town());
                mContext.startActivity(i);
                ((Activity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView houseImage;
        TextView houseTitle;
        TextView houseAddress;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            houseImage = itemView.findViewById(R.id.house_image);
            houseTitle = itemView.findViewById(R.id.house_title);
            houseAddress = itemView.findViewById(R.id.house_address);
        }
    }
}
