package com.example.projectalpha.Views;

import com.example.projectalpha.Models.SubModels.UsersData;

import java.util.List;

public interface LoginViews {
    String getUsername();
    String getPassword();
    void successLogin(List<UsersData> dataResponse);
    void failedLogin(String message);
}
