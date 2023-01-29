package com.pusvo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Listkyc {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("bank_id")
    @Expose
    private Integer bankId;
    @SerializedName("norek")
    @Expose
    private String norek;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("bankdetail")
    @Expose
    private Bankdetail bankdetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBankId() {
        return bankId;
    }



    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Bankdetail getBankdetail() {
        return bankdetail;
    }

    public void setBankdetail(Bankdetail bankdetail) {
        this.bankdetail = bankdetail;
    }

}
