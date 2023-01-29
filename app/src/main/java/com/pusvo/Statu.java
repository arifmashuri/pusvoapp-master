package com.pusvo;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Statu {
    @SerializedName("vendor")
    String stateName;
    @SerializedName("nama")
    String nama;

    @SerializedName("hp")
    String nomorHp;
    @SerializedName("id_bank")
    String id_bank;
    @SerializedName("produk")
    List<String> produk;
    @SerializedName("harga")
    String harga;
    @SerializedName("hargaproduk")
    List<String> hargai;

    @SerializedName("deskripsi")
    List<String> deskripsi;

    @SerializedName("idproduk")
    List<String> idproduk;

    private List<String> cities;

    public Statu(String stateName,String nomorHp) {
        this.stateName = stateName;
        this.nomorHp = nomorHp;
    }


    public String getStateName() {
        return stateName;
    }

    public String getHP() {
        return nomorHp;
    }
    public String getNama() {
        return nama;
    }
    public String getHarga() {
        return harga;
    }
    public String getIDBank() {
        return id_bank;
    }
    public List<String> getProduk() {
        return produk;
    }

    public List<String> getsHargai() {
        return hargai;
    }

    public List<String> getDeskripsi() {
        return deskripsi;
    }
    public List<String> getCities() {
        return cities;
    }
    public List<String> getIDProduk() {
        return idproduk;
    }

}
