package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class BIRData {
    @SerializedName("ruangan")
    private int ruangan;

    @SerializedName("laporan")
    private int laporan;

    @SerializedName("sto")
    private int sto;

    @SerializedName("witel")
    private int witel;

    @SerializedName("shift")
    private byte shift;

    @SerializedName("benda_terbakar")
    private String benda_terbakar;

    @SerializedName("steker_bertumpuk")
    private String steker_bertumpuk;

    @SerializedName("suhu")
    private int suhu;

    @SerializedName("ceceran_oli")
    private String ceceran_oli;

    @SerializedName("foto_ruangan")
    private String foto_ruangan;

    @SerializedName("foto_suhu")
    private String foto_suhu;


    public int getRuangan() {
        return ruangan;
    }

    public void setRuangan(int ruangan) {
        this.ruangan = ruangan;
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

    public String getBenda_terbakar() {
        return benda_terbakar;
    }

    public void setBenda_terbakar(String benda_terbakar) {
        this.benda_terbakar = benda_terbakar;
    }

    public String getSteker_bertumpuk() {
        return steker_bertumpuk;
    }

    public void setSteker_bertumpuk(String steker_bertumpuk) {
        this.steker_bertumpuk = steker_bertumpuk;
    }

    public int getSuhu() {
        return suhu;
    }

    public void setSuhu(int suhu) {
        this.suhu = suhu;
    }

    public String getCeceran_oli() {
        return ceceran_oli;
    }

    public void setCeceran_oli(String ceceran_oli) {
        this.ceceran_oli = ceceran_oli;
    }

    public String getFoto_ruangan() {
        return foto_ruangan;
    }

    public void setFoto_ruangan(String foto_ruangan) {
        this.foto_ruangan = foto_ruangan;
    }

    public String getFoto_suhu() {
        return foto_suhu;
    }

    public void setFoto_suhu(String foto_suhu) {
        this.foto_suhu = foto_suhu;
    }
}
