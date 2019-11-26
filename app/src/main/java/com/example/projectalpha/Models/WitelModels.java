package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.WitelData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WitelModels {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<WitelData> data;


    public boolean getStatus() {
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

    public List<WitelData> getData() {
        return data;
    }

    public void setData(List<WitelData> data) {
        this.data = data;
    }
}
