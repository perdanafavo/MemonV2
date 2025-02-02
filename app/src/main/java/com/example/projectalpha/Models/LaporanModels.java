package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.LaporanData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LaporanModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LaporanData> data;


    public void setStatus(boolean status){
        this.status=status;
    }
    public boolean getStatus(){
        return this.status;
    }

    public void setMessage(String message){
        this.message= message;
    }
    public String getMessage(){
        return message;
    }

    public void setData(List<LaporanData> data){
        this.data=data;
    }
    public List<LaporanData> getData(){
        return this.data;
    }
}
