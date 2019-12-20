package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class OthersData {
    @SerializedName("laporan")
    private int laporan;

    @SerializedName("catatan")
    private String catatan;

    @SerializedName("catatan_validator")
    private String catatan_validator;

    @SerializedName("catatan_manager")
    private String catatan_manager;


    public int getLaporan() {
        return laporan;
    }

    public void setLaporan(int laporan) {
        this.laporan = laporan;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getCatatan_validator() {
        return catatan_validator;
    }

    public void setCatatan_validator(String catatan_validator) {
        this.catatan_validator = catatan_validator;
    }

    public String getCatatan_manager() {
        return catatan_manager;
    }

    public void setCatatan_manager(String catatan_manager) {
        this.catatan_manager = catatan_manager;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
