package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.FuelData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FuelModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("morning")
    private List<FuelData> morning;

    @SerializedName("night")
    private List<FuelData> night;

    @SerializedName("data")
    private List<FuelData> data;

    public List<FuelData> getMorning() {
        return morning;
    }
    public void setMorning(List<FuelData> morning) {
        this.morning = morning;
    }

    public List<FuelData> getNight() {
        return night;
    }
    public void setNight(List<FuelData> night) {
        this.night = night;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }


    public List<FuelData> getData() {
        return data;
    }
    public void setData(List<FuelData> data) {
        this.data = data;
    }
}
