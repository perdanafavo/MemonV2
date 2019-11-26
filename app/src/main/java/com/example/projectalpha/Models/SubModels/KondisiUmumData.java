package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class KondisiUmumData {
    @SerializedName("id")
    private int id;

    @SerializedName("laporan")
    private int laporan;

    @SerializedName("cuaca")
    private String cuaca;

    @SerializedName("pompa_air")
    private String pompa_air;

    @SerializedName("genangan_air")
    private String genangan_air;

    @SerializedName("foto")
    private String foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLaporan() {
        return laporan;
    }

    public void setLaporan(int laporan) {
        this.laporan = laporan;
    }

    public String getCuaca() {
        return cuaca;
    }

    public void setCuaca(String cuaca) {
        this.cuaca = cuaca;
    }

    public String getPompa_air() {
        return pompa_air;
    }

    public void setPompa_air(String pompa_air) {
        this.pompa_air = pompa_air;
    }

    public String getGenangan_air() {
        return genangan_air;
    }

    public void setGenangan_air(String genangan_air) {
        this.genangan_air = genangan_air;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
