package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class WitelData {
    @SerializedName("id")
    private int id;

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

    @Override
    public String toString() {
        return "WitelData{" +
                "id=" + id +
                ", wilayah='" + nama + '\'' +
                ", singkatan='" + singkatan + '\'' +
                '}';
    }
}
