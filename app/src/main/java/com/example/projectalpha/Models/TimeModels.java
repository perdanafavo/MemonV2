package com.example.projectalpha.Models;

import com.google.gson.annotations.SerializedName;

public class TimeModels {
    @SerializedName("hari")
    private String hari;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("bulan")
    private String bulan;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("jam")
    private String jam;

    public String getHari() {
        return hari;
    }
    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setTanggal(String tanggal){
        this.tanggal= tanggal;
    }
    public String getTanggal(){
        return tanggal;
    }

    public String getBulan() {
        return bulan;
    }
    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public void setWaktu(String waktu){
        this.waktu=waktu;
    }
    public String getWaktu(){
        return this.waktu;
    }

    public void setJam(String jam){
        this.jam=jam;
    }
    public String getJam(){
        return this.jam;
    }
}
