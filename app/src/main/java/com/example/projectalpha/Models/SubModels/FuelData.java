package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class FuelData {
    @SerializedName("laporan")
    private int laporan;

    @SerializedName("sto")
    private int sto;

    @SerializedName("witel")
    private int witel;

    @SerializedName("shift")
    private byte shift;

    @SerializedName("tanggal_shift")
    private String tanggal_shift;

    @SerializedName("kapasitas")
    private int kapasitas;

    @SerializedName("tanki_bulanan")
    private int tanki_bulanan;

    @SerializedName("kapasitas_rendah")
    private int kapasitas_rendah;

    @SerializedName("foto")
    private String foto;

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

    public String getTanggal_shift() {
        return tanggal_shift;
    }

    public void setTanggal_shift(String tanggal_shift) {
        this.tanggal_shift = tanggal_shift;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public int getTanki_bulanan() {
        return tanki_bulanan;
    }

    public void setTanki_bulanan(int tanki_bulanan) {
        this.tanki_bulanan = tanki_bulanan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getKapasitas_rendah() {
        return kapasitas_rendah;
    }

    public void setKapasitas_rendah(int kapasitas_rendah) {
        this.kapasitas_rendah = kapasitas_rendah;
    }
}
