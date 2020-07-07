package com.huawei.bookingapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.textfield.TextInputEditText;
import com.huawei.bookingapp.R;
import com.huawei.bookingapp.data.UserDAO;
import com.huawei.bookingapp.data.UserDatabase;
import com.huawei.bookingapp.model.User;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    View mView;
    TextInputEditText inputUserName, inputFullName, inputEmail, inputPhone, inputPassword;
    Button updateUserBtn;

    User user;
    UserDAO db;
    UserDatabase dataBase;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_profile, container, false);

        inputUserName = mView.findViewById(R.id.input_userName);
        inputFullName = mView.findViewById(R.id.input_fullName);
        inputEmail = mView.findViewById(R.id.input_email);
        inputPhone = mView.findViewById(R.id.input_phoneNumber);
        inputPassword = mView.findViewById(R.id.input_password);
        updateUserBtn = mView.findViewById(R.id.updateUser);

        dataBase = Room.databaseBuilder(getActivity(), UserDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build();

        db = dataBase.getUserDao();

        mSharedPreferences = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        int userID = mSharedPreferences.getInt("userID", 1);

        final User user = db.getUserUsingID(userID);
        if (user != null) {
            if (!user.getUserName().isEmpty()) {
                inputUserName.setText(user.getUserName(), TextView.BufferType.EDITABLE);
            }
            if (!user.getUserFullName().isEmpty()) {
                inputFullName.setText(user.getUserFullName(), TextView.BufferType.EDITABLE);
            }
            if (!user.getUserMail().isEmpty()) {
                inputEmail.setText(user.getUserMail(), TextView.BufferType.EDITABLE);
            }
            if (!user.getUserNumber().isEmpty()) {
                inputPhone.setText(user.getUserNumber(), TextView.BufferType.EDITABLE);
            }
            if (!user.getUserPassword().isEmpty()) {
                inputPassword.setText(user.getUserPassword(), TextView.BufferType.EDITABLE);
            }
        }

        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (user != null) {
                        String newUsername = Objects.requireNonNull(inputUserName.getText()).toString().trim();
                        String newFullName = Objects.requireNonNull(inputFullName.getText()).toString().trim();
                        String newEmail = Objects.requireNonNull(inputEmail.getText()).toString().trim();
                        String newPhone = Objects.requireNonNull(inputPhone.getText()).toString().trim();
                        String newPassword = Objects.requireNonNull(inputPassword.getText()).toString().trim();
                        user.setUserName(newUsername);
                        user.setUserFullName(newFullName);
                        user.setUserMail(newEmail);
                        user.setUserNumber(newPhone);
                        user.setUserPassword(newPassword);
                        db.update(user);

                        Toast.makeText(getActivity(), "Update Successful", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Update Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return mView;
    }
}
