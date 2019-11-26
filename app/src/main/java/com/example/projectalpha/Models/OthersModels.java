package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.OthersData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OthersModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<OthersData> data;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<OthersData> getData() {
        return data;
    }
    public void setData(List<OthersData> data) {
        this.data = data;
    }
}
