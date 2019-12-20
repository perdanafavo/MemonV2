package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class ReportStatusData {

    @SerializedName("laporan_id")
    private int laporan_id;

    @SerializedName("status_umum")
    private byte status_umum;

    @SerializedName("status_bbm")
    private byte status_bbm;

    @SerializedName("status_catuan")
    private byte status_catuan;

    @SerializedName("status_sentral")
    private byte status_sentral;

    @SerializedName("status_genset")
    private byte status_genset;

    @SerializedName("status_akses")
    private byte status_akses;

    @SerializedName("status_batere")
    private byte status_batere;

    @SerializedName("status_rectifier")
    private byte status_rectifier;

    @SerializedName("status_transmisi")
    private byte status_transmisi;

    @SerializedName("status_olo")
    private byte status_olo;

    public int getLaporan_id() {
        return laporan_id;
    }

    public void setLaporan_id(int laporan_id) {
        this.laporan_id = laporan_id;
    }

    public byte getStatus_umum() {
        return status_umum;
    }

    public void setStatus_umum(byte status_umum) {
        this.status_umum = status_umum;
    }

    public byte getStatus_bbm() {
        return status_bbm;
    }

    public void setStatus_bbm(byte status_bbm) {
        this.status_bbm = status_bbm;
    }

    public byte getStatus_catuan() {
        return status_catuan;
    }

    public void setStatus_catuan(byte status_catuan) {
        this.status_catuan = status_catuan;
    }

    public byte getStatus_sentral() {
        return status_sentral;
    }

    public void setStatus_sentral(byte status_sentral) {
        this.status_sentral = status_sentral;
    }

    public byte getStatus_genset() {
        return status_genset;
    }

    public void setStatus_genset(byte status_genset) {
        this.status_genset = status_genset;
    }

    public byte getStatus_akses() {
        return status_akses;
    }

    public void setStatus_akses(byte status_akses) {
        this.status_akses = status_akses;
    }

    public byte getStatus_batere() {
        return status_batere;
    }

    public void setStatus_batere(byte status_batere) {
        this.status_batere = status_batere;
    }

    public byte getStatus_rectifier() {
        return status_rectifier;
    }

    public void setStatus_rectifier(byte status_rectifier) {
        this.status_rectifier = status_rectifier;
    }

    public byte getStatus_transmisi() {
        return status_transmisi;
    }

    public void setStatus_transmisi(byte status_transmisi) {
        this.status_transmisi = status_transmisi;
    }

    public byte getStatus_olo() {
        return status_olo;
    }

    public void setStatus_olo(byte status_olo) {
        this.status_olo = status_olo;
    }
}
