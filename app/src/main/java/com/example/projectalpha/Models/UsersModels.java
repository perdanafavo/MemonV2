package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.UsersData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<UsersData> data;

    public void setStatus(boolean status){
        this.status= status;
    }
    public boolean getStatus(){
        return status;
    }

    public void setMessage(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }

    public void setData(ArrayList<UsersData> data){
        this.data=data;
    }
    public ArrayList<UsersData> getData(){
        return this.data;
    }
}
