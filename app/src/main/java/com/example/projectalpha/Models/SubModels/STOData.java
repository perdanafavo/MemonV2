package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class STOData {
    @SerializedName("id")
    private int id;

    @SerializedName("witel")
    private int witel;

    @SerializedName("nama")
    private String nama;

    @SerializedName("singkatan")
    private String singkatan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWitel() {
        return witel;
    }

    public void setWitel(int witel) {
        this.witel = witel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSingkatan() {
        return singkatan;
    }

    public void setSingkatan(String singkatan) {
        this.singkatan = singkatan;
    }

    public String toString(){
        return nama;
    }
}
