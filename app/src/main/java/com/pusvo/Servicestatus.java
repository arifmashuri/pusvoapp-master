package com.pusvo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Servicestatus {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("telkomsel")
    @Expose
    private Integer telkomsel;
    @SerializedName("three")
    @Expose
    private Integer three;
    @SerializedName("telkomsel_rate")
    @Expose
    private Integer telkomselRate;
    @SerializedName("three_rate")
    @Expose
    private Integer threeRate;
    @SerializedName("indodax")
    @Expose
    private Integer indodax;
    @SerializedName("isoma")
    @Expose
    private Integer isoma;
    @SerializedName("liburan")
    @Expose
    private Integer liburan;
    @SerializedName("matrik")
    @Expose
    private Integer matrik;
    @SerializedName("telkomsel_out")
    @Expose
    private Integer telkomselOut;
    @SerializedName("three_out")
    @Expose
    private Integer threeOut;
    @SerializedName("pengumuman_apk")
    @Expose
    private String pengumumanApk;
    @SerializedName("vendor_wd")
    @Expose
    private Integer vendorWd;
    @SerializedName("fee_wd")
    @Expose
    private Integer feeWd;
    @SerializedName("fee_wdreal")
    @Expose
    private Integer feeWdreal;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTelkomsel() {
        return telkomsel;
    }

    public void setTelkomsel(Integer telkomsel) {
        this.telkomsel = telkomsel;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getTelkomselRate() {
        return telkomselRate;
    }

    public void setTelkomselRate(Integer telkomselRate) {
        this.telkomselRate = telkomselRate;
    }

    public Integer getThreeRate() {
        return threeRate;
    }

    public void setThreeRate(Integer threeRate) {
        this.threeRate = threeRate;
    }

    public Integer getIndodax() {
        return indodax;
    }

    public void setIndodax(Integer indodax) {
        this.indodax = indodax;
    }

    public Integer getIsoma() {
        return isoma;
    }

    public void setIsoma(Integer isoma) {
        this.isoma = isoma;
    }

    public Integer getLiburan() {
        return liburan;
    }

    public void setLiburan(Integer liburan) {
        this.liburan = liburan;
    }

    public Integer getMatrik() {
        return matrik;
    }

    public void setMatrik(Integer matrik) {
        this.matrik = matrik;
    }

    public Integer getTelkomselOut() {
        return telkomselOut;
    }

    public void setTelkomselOut(Integer telkomselOut) {
        this.telkomselOut = telkomselOut;
    }

    public Integer getThreeOut() {
        return threeOut;
    }

    public void setThreeOut(Integer threeOut) {
        this.threeOut = threeOut;
    }

    public String getPengumumanApk() {
        return pengumumanApk;
    }

    public void setPengumumanApk(String pengumumanApk) {
        this.pengumumanApk = pengumumanApk;
    }

    public Integer getVendorWd() {
        return vendorWd;
    }

    public void setVendorWd(Integer vendorWd) {
        this.vendorWd = vendorWd;
    }

    public Integer getFeeWd() {
        return feeWd;
    }

    public void setFeeWd(Integer feeWd) {
        this.feeWd = feeWd;
    }

    public Integer getFeeWdreal() {
        return feeWdreal;
    }

    public void setFeeWdreal(Integer feeWdreal) {
        this.feeWdreal = feeWdreal;
    }

}