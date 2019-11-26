package com.example.projectalpha.Views;

import android.graphics.Bitmap;

import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.WitelData;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface CreateUserViews {

    Bitmap getBitmap();
    List<STOData> getDataSTO();
    List<WitelData> getDataWitel();
    Map<String, RequestBody> getUsersData();
    RequestBody createPartFromString(String descriptionString);

    void actionAddUsers(Map<String, RequestBody> usersData, boolean action);
}
