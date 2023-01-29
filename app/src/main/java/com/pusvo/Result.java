package com.pusvo;

import com.google.gson.annotations.SerializedName;



public class Result {
    @SerializedName("hp")
    String hp;
    @SerializedName("nama")
    String nama;
    @SerializedName("meter")
    String meter;
    @SerializedName("produk")
    String produk;
    @SerializedName("waktu")
    long waktu;
    @SerializedName("keterangan")
    String keterangan;
    @SerializedName("harga")
    String harga;
    @SerializedName("transaction_id")
    String transaction_id;
    @SerializedName("status")
    String status;
    @SerializedName("metode")
    String metode;
    @SerializedName("operator")
    String operator;
    @SerializedName("id")
    String id;
    @SerializedName("id_depo")
    String id_depo;
    @SerializedName("va")
    String va;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("total_sukses")
    String total_sukses;
    @SerializedName("bulan")
    String bulan;
    @SerializedName("saldo")
    String saldo;
    @SerializedName("poin")
    String poin;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("notice")
    String notice;
    @SerializedName("jumlahorang")
    String jumlahorang;
    @SerializedName("jumlahbulan")
    String jumlahbulan;
    @SerializedName("transaction_kode")
    String transaction_kode;
    public String gethp() {
        return hp;
    }

    public String getNama() {
        return nama;
    }
    public String getMeter() {
        return meter;
    }
    public long getWaktu() {
        return waktu;
    }

    public String getProduk() {
        return produk;
    }
    public String getKeterangan() {
        return keterangan;
    }

    public String getHarga() {
        return harga;
    }
    public String getTransaction_id() {
        return transaction_id;
    }
    public String getStatus() {
        return status;
    }
    public String getMetode() {
        return metode;
    }
    public String getOperator() {
        return operator;
    }
    public String getIDProduk() {
        return id;
    }
    public String getID_depo() {
        return id_depo;
    }
    public String getVA() { return va; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getTotal_sukses() { return total_sukses; }
    public String getBulan() {
        return bulan;
    }
    public String getSaldo() {
        return saldo;
    }
    public String getPoin() {
        return poin;
    }
    public String getJumlah() {
        return jumlah;
    }
    public String getNotice() {
        return notice;
    }
    public String getJumlahOrang() {
        return jumlahorang;
    }
    public String getJumlahBulan() {
        return jumlahbulan;
    }
    public String getTransaction_kode() {
        return transaction_kode;
    }

}
