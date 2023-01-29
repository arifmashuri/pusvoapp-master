package com.pusvo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Produkdetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("satuan")
    @Expose
    private String satuan;
    @SerializedName("kode")
    @Expose
    private String kode;

    @SerializedName("harga_jual")
    @Expose
    private String hargaJual;
    @SerializedName("harga_jualpoin")
    @Expose
    private String hargaJualpoin;
    @SerializedName("poin")
    @Expose
    private String poin;
    @SerializedName("harga_jualp")
    @Expose
    private String hargaJualp;
    @SerializedName("jenis")
    @Expose
    private Integer jenis;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("aktif")
    @Expose
    private Integer aktif;
    @SerializedName("urutan")
    @Expose
    private Integer urutan;
    @SerializedName("manual")
    @Expose
    private Integer manual;
    @SerializedName("bca")
    @Expose
    private Integer bca;
    @SerializedName("voucher")
    @Expose
    private Integer voucher;
    @SerializedName("sms")
    @Expose
    private Integer sms;
    @SerializedName("verified_only")
    @Expose
    private Integer verifiedOnly;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }



    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getHargaJualpoin() {
        return hargaJualpoin;
    }

    public void setHargaJualpoin(String hargaJualpoin) {
        this.hargaJualpoin = hargaJualpoin;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getHargaJualp() {
        return hargaJualp;
    }

    public void setHargaJualp(String hargaJualp) {
        this.hargaJualp = hargaJualp;
    }

    public Integer getJenis() {
        return jenis;
    }

    public void setJenis(Integer jenis) {
        this.jenis = jenis;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAktif() {
        return aktif;
    }

    public void setAktif(Integer aktif) {
        this.aktif = aktif;
    }

    public Integer getUrutan() {
        return urutan;
    }

    public void setUrutan(Integer urutan) {
        this.urutan = urutan;
    }

    public Integer getManual() {
        return manual;
    }

    public void setManual(Integer manual) {
        this.manual = manual;
    }

    public Integer getBca() {
        return bca;
    }

    public void setBca(Integer bca) {
        this.bca = bca;
    }

    public Integer getVoucher() {
        return voucher;
    }

    public void setVoucher(Integer voucher) {
        this.voucher = voucher;
    }

    public Integer getSms() {
        return sms;
    }

    public void setSms(Integer sms) {
        this.sms = sms;
    }

    public Integer getVerifiedOnly() {
        return verifiedOnly;
    }

    public void setVerifiedOnly(Integer verifiedOnly) {
        this.verifiedOnly = verifiedOnly;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}