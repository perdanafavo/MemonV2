package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class ContactData {
    @SerializedName("id")
    private int id;

    @SerializedName("id_witel")
    private int id_witel;

    @SerializedName("witel")
    private String witel;

    @SerializedName("nama")
    private String nama;

    @SerializedName("handphone")
    private long handphone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_witel() {
        return id_witel;
    }

    public void setId_witel(int id_witel) {
        this.id_witel = id_witel;
    }

    public String getWitel() {
        return witel;
    }

    public void setWitel(String witel) {
        this.witel = witel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public long getHandphone() {
        return handphone;
    }

    public void setHandphone(long handphone) {
        this.handphone = handphone;
    }
}
