package com.pusvo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metode {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("wd")
    @Expose
    private Integer wd;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("harga_jual")
    @Expose
    private Double hargaJual;
    @SerializedName("jenis")
    @Expose
    private Integer jenis;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("operator_id")
    @Expose
    private Integer operatorId;

    public Integer getId() {
        return id;
    }
    public Integer getWd() {
        return wd;
    }
    public Integer getStatus() {
        return status;
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

    public Double getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(Double hargaJual) {
        this.hargaJual = hargaJual;
    }

    public Integer getJenis() {
        return jenis;
    }

    public void setJenis(Integer jenis) {
        this.jenis = jenis;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

}