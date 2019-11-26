package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.BIRData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BIRModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("morning")
    private List<BIRData> morning;

    @SerializedName("night")
    private List<BIRData> night;

    @SerializedName("data")
    private List<BIRData> data;

    public List<BIRData> getMorning() {
        return morning;
    }
    public void setMorning(List<BIRData> morning) {
        this.morning = morning;
    }

    public List<BIRData> getNight() {
        return night;
    }
    public void setNight(List<BIRData> night) {
        this.night = night;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BIRData> getData() {
        return data;
    }
    public void setData(List<BIRData> data) {
        this.data = data;
    }
}
