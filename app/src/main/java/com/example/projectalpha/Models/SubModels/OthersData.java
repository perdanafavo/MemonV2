package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class OthersData {
    @SerializedName("laporan")
    private int laporan;

    @SerializedName("catatan")
    private String catatan;

    public int getLaporan() {
        return laporan;
    }

    public void setLaporan(int laporan) {
        this.laporan = laporan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
