package com.pusvo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;


public class SharedPrefManager {
    @SerializedName("spMahasiswaApp")
    public static final String SP_MAHASISWA_APP = "spMahasiswaApp";
    @SerializedName("spNama")
    public static final String SP_NAMA = "spNama";
    @SerializedName("spProduk")
    public static final String SP_PRODUK = "spProduk";
    @SerializedName("spUsername")
    public static final String SP_USERNAME = "spUsername";
    @SerializedName("spApikey")
    public static final String SP_API_KEY = "spApikey";
    @SerializedName("spSudahLogin")
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    @SerializedName("spBtdevice")
    public static final String SP_BTDEVICE = "spBtdevice";
    @SerializedName("spBtname")
    public static final String SP_BTNAME = "spBtname";
    @SerializedName("spBtoff")
    public static final String SP_BT_OFF = "spBtoff";
    @SerializedName("spMetode")
    public static final String SP_METODE = "spMetode";
    @SerializedName("spMetodeBaru")
    public static final String SP_METODEBARU = "spMetodeBaru";
    @SerializedName("spMetodeTopup")
    public static final String SP_METODETOPUP = "spMetodeTopup";
    @SerializedName("spBearer")
    public static final String SP_BEARER = "spBearer";


    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_MAHASISWA_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, Integer value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_NAMA, "");
    }

    public String getSPProduk(){
        return sp.getString(SP_PRODUK, "");
    }

    public String getSPUsername(){
        return sp.getString(SP_USERNAME, "");
    }

    public String getSPApikey(){
        return sp.getString(SP_API_KEY, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public Boolean getSPBtoff(){ return sp.getBoolean(SP_BT_OFF, false); }

    public String getSPBtdevice(){        return sp.getString(SP_BTDEVICE, "");
    }
    public String getSPBtname(){        return sp.getString(SP_BTNAME, "");
    }
    public String getSPMetode(){
        return sp.getString(SP_METODE, "3");
    }
    public Integer getSPMetodeBaru(){
        return sp.getInt(SP_METODEBARU, 13);
    }
    public Integer getSPMetodeTopup(){
        return sp.getInt(SP_METODETOPUP, 13);
    }
    public String getSPBearer(){
        return sp.getString(SP_BEARER, "");
    }
}
