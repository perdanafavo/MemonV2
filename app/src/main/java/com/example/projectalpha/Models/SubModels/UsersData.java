package com.example.projectalpha.Models.SubModels;

import com.google.gson.annotations.SerializedName;

public class UsersData{

    public UsersData(){

    }

    @SerializedName("id")
    private String id;

    @SerializedName("privileges")
    private int privileges;

    @SerializedName("admin")
    private String admin;

    @SerializedName("witel")
    private int witel;

    @SerializedName("nama_witel")
    private String nama_witel;

    @SerializedName("sto")
    private int sto;

    @SerializedName("nama_sto")
    private String nama_sto;

    @SerializedName("nama")
    private String nama;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("handphone")
    private long handphone;

    @SerializedName("foto")
    private String foto;

    public void setId(String id){
        this.id= id;
    }
    public String getId(){
        return this.id;
    }

    public void setPrivileges(int privileges){
        this.privileges = privileges;
    }
    public int getPrivileges(){
        return this.privileges;
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

    public void setNama_sto(String nama){
        this.nama_sto= nama;
    }
    public String getNama_sto(){
        return this.nama_sto;
    }

    public void setNama(String nama){
        this.nama= nama;
    }
    public String getNama(){
        return this.nama;
    }

    public void setAlamat(String alamat){
        this.alamat= alamat;
    }
    public String getAlamat(){
        return this.alamat;
    }

    public void setHandphone(long handphone){
        this.handphone= handphone;
    }
    public long getHandphone(){
        return this.handphone;
    }

    public void setFoto(String foto){
        this.foto=foto;
    }
    public String getFoto(){
        return this.foto;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_witel() {
        return nama_witel;
    }

    public void setNama_witel(String nama_witel) {
        this.nama_witel = nama_witel;
    }
}
