package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.STOData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class STOModels {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<STOData> data;


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

    public List<STOData> getData() {
        return data;
    }

    public void setData(List<STOData> data) {
        this.data = data;
    }
}
