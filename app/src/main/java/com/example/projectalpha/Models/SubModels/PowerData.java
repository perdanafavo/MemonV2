package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class PowerData {
    @SerializedName("sto")
    private int sto;

    @SerializedName("witel")
    private int witel;

    @SerializedName("shift")
    private byte shift;

    @SerializedName("laporan")
    private int laporan;

    @SerializedName("pln")
    private String pln;

    @SerializedName("genset")
    private String genset;

    @SerializedName("status_approved")
    private byte status_approved;


    public void setStatus_approved(byte status_approved) {
        this.status_approved = status_approved;
    }
    public byte getStatus_approved() {
        return this.status_approved;
    }
    public boolean isStatusApproved(){
        return this.status_approved == 0;
    }
    public int getLaporan() {
        return laporan;
    }

    public void setLaporan(int laporan) {
        this.laporan = laporan;
    }

    public int getSto() {
        return sto;
    }

    public void setSto(int sto) {
        this.sto = sto;
    }

    public int getWitel() {
        return witel;
    }

    public void setWitel(int witel) {
        this.witel = witel;
    }

    public byte getShift() {
        return shift;
    }

    public void setShift(byte shift) {
        this.shift = shift;
    }

    public String getPln() {
        return pln;
    }

    public void setPln(String pln) {
        this.pln = pln;
    }

    public String getGenset() {
        return genset;
    }

    public void setGenset(String genset) {
        this.genset = genset;
    }
}
