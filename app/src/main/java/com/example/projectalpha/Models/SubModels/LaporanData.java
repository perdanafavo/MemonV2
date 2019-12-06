package com.example.projectalpha.Models.SubModels;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class LaporanData{
    @SerializedName("id")
    private int id;

    @SerializedName("users")
    private String users;

    @SerializedName("witel")
    private int witel;

    @SerializedName("sto")
    private int sto;

    @SerializedName("shift")
    private byte shift;

    @SerializedName("tanggal_shift")
    private String tanggal_shift;

    @SerializedName("tanggal_upload")
    private String tanggal_upload;

    @SerializedName("jam_upload")
    private String jam_upload;

    @SerializedName("status")
    private byte status;

    @SerializedName("status_approved")
    private byte status_approved;

    public byte getStatus_approved() {
        return this.status_approved;
    }

    public void setStatus_approved(byte status_approved) {
        this.status_approved = status_approved;
    }
    public boolean isStatusApproved(){
        return this.status == 0;
    }

    public void setId(int id){
        this.id= id;
    }
    public int getId(){
        return this.id;
    }

    public void setUsers(String users){
        this.users = users;
    }
    public String getUsers(){
        return this.users;
    }

    public void setWitel(int witel){
        this.witel= witel;
    }
    public int getWitel(){
        return this.witel;
    }

    public void setSto(int sto){
        this.sto= sto;
    }
    public int getSto(){
        return this.sto;
    }

    public void setShift(byte nama){
        this.shift=shift;
    }
    public byte getShift(){
        return this.shift;
    }

    public String getTanggal_shift() {
        return tanggal_shift;
    }
    public void setTanggal_shift(String tanggal_shift) {
        this.tanggal_shift = tanggal_shift;
    }

    public String getTanggal_upload() {
        return tanggal_upload;
    }
    public void setTanggal_upload(String tanggal_upload) {
        this.tanggal_upload = tanggal_upload;
    }

    public String getJam_upload() {
        return jam_upload;
    }
    public void setJam_upload(String jam_upload) {
        this.jam_upload = jam_upload;
    }

    public void setStatus(byte status){
        this.status=status;
    }
    public byte getStatus(){
        return this.status;
    }

    public boolean isStatus(){
        return this.status == 1;
    }

    @NonNull
    @Override
    public String toString() {
        return tanggal_shift+" "+jam_upload;
    }
}
