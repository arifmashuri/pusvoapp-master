package com.pusvo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Userinfo {

    @SerializedName("data")
    @Expose
    private Userinfo datas2;

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_photo")
    @Expose
    private String profile_photo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pesan")
    @Expose
    private String pesan;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("api_key")
    @Expose
    private String api_key;
    @SerializedName("bearer")
    @Expose
    private String bearer;
    @SerializedName("saldo")
    @Expose
    private Double saldo;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("idx_kyc")
    @Expose
    private Integer idxKyc;
    @SerializedName("poin")
    @Expose
    private Integer poin;
    @SerializedName("pengumuman")
    @Expose
    private String pengumuman;

    @SerializedName("produks")
    @Expose
    List<Produk> produks;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHp() {
        return hp;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIdxKyc() {
        return idxKyc;
    }

    public void setIdxKyc(Integer idxKyc) {
        this.idxKyc = idxKyc;
    }

    public Integer getPoin() {
        return poin;
    }

    public void setPoin(Integer poin) {
        this.poin = poin;
    }

    public String getPengumuman() {
        return pengumuman;
    }
    public String getMessage() {
        return message;
    }
    public String getApi_key() {
        return api_key;
    }
    public String getBearer() {
        return bearer;
    }
    public String getPesan() {
        return pesan;
    }

    public void setPengumuman(String pengumuman) {
        this.pengumuman = pengumuman;
    }
    public Userinfo getData() {
        return datas2;
    }
    public List<Produk> getProduks() {
        return produks;
    }

}
