package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.PowerData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PowerModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("morning")
    private List<PowerData> morning;

    @SerializedName("night")
    private List<PowerData> night;

    @SerializedName("data")
    private List<PowerData> data;

    public List<PowerData> getMorning() {
        return morning;
    }
    public void setMorning(List<PowerData> morning) {
        this.morning = morning;
    }

    public List<PowerData> getNight() {
        return night;
    }
    public void setNight(List<PowerData> night) {
        this.night = night;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PowerData> getData() {
        return data;
    }
    public void setData(List<PowerData> data) {
        this.data = data;
    }
}
