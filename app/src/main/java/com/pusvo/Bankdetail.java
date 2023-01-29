package com.pusvo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bankdetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("urutan")
    @Expose
    private Integer urutan;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("otomatis")
    @Expose
    private Integer otomatis;
    @SerializedName("bisa_manual")
    @Expose
    private Integer bisaManual;
    @SerializedName("ewallet")
    @Expose
    private Integer ewallet;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOtomatis() {
        return otomatis;
    }

    public void setOtomatis(Integer otomatis) {
        this.otomatis = otomatis;
    }

    public Integer getBisaManual() {
        return bisaManual;
    }

    public void setBisaManual(Integer bisaManual) {
        this.bisaManual = bisaManual;
    }

    public Integer getEwallet() {
        return ewallet;
    }

    public void setEwallet(Integer ewallet) {
        this.ewallet = ewallet;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

}