package com.pusvo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderdetail {

    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("jumlahbulan")
    @Expose
    private String jumlahbulan;
    @SerializedName("jumlahorang")
    @Expose
    private String jumlahorang;
    @SerializedName("bulan")
    @Expose
    private String bulan;
    @SerializedName("qr")
    @Expose
    private String qr;

    public String getNama() {
        return nama;
    }
    public String getJumlahorang() {
        return jumlahorang;
    }
    public String getJumlahbulan() {
        return jumlahbulan;
    }
    public String getBulan() {
        return bulan;
    }
    public String getQR() {
        return qr;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}