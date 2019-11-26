package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.KondisiUmumData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KondisiUmumModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<KondisiUmumData> data;

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

    public void setData(List<KondisiUmumData> data){
        this.data=data;
    }
    public List<KondisiUmumData> getData(){
        return this.data;
    }
}
