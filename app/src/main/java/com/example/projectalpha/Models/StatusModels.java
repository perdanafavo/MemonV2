package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.LaporanData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StatusModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("morning")
    private List<LaporanData> morning;

    @SerializedName("night")
    private List<LaporanData> night;

    public List<LaporanData> getMorning() {
        return morning;
    }
    public void setMorning(List<LaporanData> morning) {
        this.morning = morning;
    }

    public List<LaporanData> getNight() {
        return night;
    }
    public void setNight(List<LaporanData> night) {
        this.night = night;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
