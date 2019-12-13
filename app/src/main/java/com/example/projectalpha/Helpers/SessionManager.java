package com.example.projectalpha.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

public class SessionManager {

    private static final String SP_STO_APP = "spSTOApp";

    public static final String SP_IDUSER= "spIdUser";
    public static final String SP_FULLNAME = "spFullname";
    public static final String SP_USERNAME = "spUsername";
    public static final String SP_STO = "spSTO";
    public static final String SP_NAMASTO = "spNamaSTO";
    public static final String SP_WITEL = "spWitel";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_HANDPHONE = "spHandphone";
    public static final String SP_FOTO = "spFoto";
    public static final String SP_PRIVILEGES = "spPrivileges";

    private static final String SP_IDLAPORAN= "spIdLaporan";

    public static final String SP_ALREADY_LOGINADMIN = "spAlreadyLoginAdmins";
    public static final String SP_ALREADY_LOGINPETUGAS = "spAlreadyLoginPetugas";
    public static final String SP_ALREADY_LOGINMANAGER = "spAlreadyLoginManager";
    public static final String SP_ALREADY_LOGINVALIDATOR = "spAlreadyLoginValidator";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SessionManager(@NotNull Context mContext){
        sp = mContext.getSharedPreferences(SP_STO_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
    }

    void clearSP(){
        spEditor.clear().commit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPLong(String keySP, long value){
        spEditor.putLong(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpSTOApp() {
        return SP_STO_APP;
    }

    public String getSpIduser() {
        return sp.getString(SP_IDUSER, "");
    }

    public int getSpIdlaporan() {
        return sp.getInt(SP_IDLAPORAN, 0);
    }

    public int getSpSTO() {
        return sp.getInt(SP_STO, 0);
    }

    public String getSpNamaSTO() {
        return sp.getString(SP_NAMASTO, "");
    }

    public String getSpAlamat() {
        return sp.getString(SP_ALAMAT, "");
    }

    public long getSpHandphone() {
        return sp.getLong(SP_HANDPHONE, 0);
    }

    public String getSpFoto() {
        return sp.getString(SP_FOTO, "");
    }

    public int getSpWitel() {
        return sp.getInt(SP_WITEL, 0);
    }

    public String getSpFullname() {
        return sp.getString(SP_FULLNAME, "");
    }

    public String getSpUsername() {
        return sp.getString(SP_USERNAME, "");
    }

    public Boolean getSpAlreadyLoginAdmin() { return sp.getBoolean(SP_ALREADY_LOGINADMIN, false);}

    public Boolean getSpAlreadyLoginPetugas() { return sp.getBoolean(SP_ALREADY_LOGINPETUGAS, false);}

    public Boolean getSpAlreadyLoginManager() { return sp.getBoolean(SP_ALREADY_LOGINMANAGER, false);}

    public Boolean getSpAlreadyLoginValidator() { return sp.getBoolean(SP_ALREADY_LOGINVALIDATOR, false);}

    public int getSpPrivileges() {
        return sp.getInt(SP_PRIVILEGES, 0);
    }

}
