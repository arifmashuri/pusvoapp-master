package com.pusvo;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Value {
    @SerializedName("value")
    String value;
    @SerializedName("message")
    String message;
    @SerializedName("transaction_id")
    String transaction_id;
    @SerializedName("transaction_kode")
    String transaction_kode;
    @SerializedName("hp")
    String hp;
    @SerializedName("produk")
    String produk;
    @SerializedName("keterangan")
    String keterangan;
    @SerializedName("meter")
    String meter;
    @SerializedName("waktu")
    long waktu;
    @SerializedName("waktu_bayar")
    long waktu_bayar;
    @SerializedName("waktu_sukses")
    long waktu_sukses;
    @SerializedName("harga")
    Double harga;
    @SerializedName("result")
    List<Result> result;

    @SerializedName("hasil")
    List<Statu> hasil;
    @SerializedName("status")
    String status;
    @SerializedName("saldo")
    String saldo;
    @SerializedName("poin")
    String poin;
    @SerializedName("api_key")
    String api_key;
    @SerializedName("jumlah")
    String jumlah;
    @SerializedName("metode")
    String metode;
    @SerializedName("operator")
    String operator;
    @SerializedName("id")
    String id;
    @SerializedName("id_depo")
    String id_depo;
    @SerializedName("email")
    String email;
    @SerializedName("total_sukses")
    String total_sukses;
    @SerializedName("va")
    String va;
    @SerializedName("username")
    String username;
    @SerializedName("bulan")
    String bulan;
    @SerializedName("nama")
    String nama;
    @SerializedName("jumlahorang")
    String jumlahorang;
    @SerializedName("jumlahbulan")
    String jumlahbulan;
    @SerializedName("notice")
    String notice;
    @SerializedName("sn")
    String sn;
    @SerializedName("voucher")
    String voucher;
    @SerializedName("qr")
    String qr;
    @SerializedName("vendor")
    String vendor;
    @SerializedName("gopay")
    String gopay;
    @SerializedName("ovo")
    String ovo;
    @SerializedName("dana")
    String dana;
    @SerializedName("linkaja")
    String linkaja;
    @SerializedName("shopeepay")
    String shopeepay;
    @SerializedName("bri")
    String bri;
    @SerializedName("mandiri")
    String mandiri;
    @SerializedName("bni")
    String bni;
    @SerializedName("bca")
    String bca;
    @SerializedName("matrik")
    String matrik;
    @SerializedName("fee_wd")
    Integer fee_wd;
    @SerializedName("bearer")
    String bearer;
    @SerializedName("pengumuman")
    String pengumuman;
    @SerializedName("pesan")
    String pesan;






    public String getValue() {
        return value;
    }
    public String getMessage() {
        return message;
    }
    public String getPesan() {
        return pesan;
    }
    public String getTransaction_id() {
        return transaction_id;
    }
    public String getTransaction_kode() {
        return transaction_kode;
    }
    public String getHp() {
        return hp;
    }
    public long getWaktu() {
        return waktu;
    }
    public long getWaktu_bayar() {
        return waktu_bayar;
    }
    public long getWaktu_sukses() {
        return waktu_sukses;
    }
    public String getProduk() {
        return produk;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public String getMeter() {
        return meter;
    }
    public Double getHarga() {
        return harga;
    }
    public List<Result> getResult() {
        return result;
    }

    public List<Statu> getHasil() {
        return hasil;
    }
    public String getStatus() {
        return status;
    }
    public String getSaldo() {
        return saldo;
    }
    public String getPoin() {
        return poin;
    }
    public String getApi_key() {
        return api_key;
    }
    public String getBearer() {
        return bearer;
    }
    public String getJumlah() {
        return jumlah;
    }
    public String getMetode() {
        return metode;
    }

    public String getOperator() {
        return operator;
    }
    public String getID_depo() {
        return id_depo;
    }
    public String getIDProduk() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getTotal_sukses() { return total_sukses; }
    public String getVA() {
        return va;
    }
    public String getUsername() { return username; }
    public String getBulan() {
        return bulan;
    }
    public String getNama() {
        return nama;
    }
    public String getJumlahOrang() {
        return jumlahorang;
    }
    public String getJumlahBulan() {
        return jumlahbulan;
    }
    public String getNotice() {
        return notice;
    }
    public String getSN() {
        return sn;
    }
    public String getVoucher() {
        return voucher;
    }
    public String getQR() {
        return qr;
    }
    public String getVendor() {
        return vendor;
    }
    public String getGopay() {
        return gopay;
    }
    public String getDana() {
        return dana;
    }
    public String getLinkaja() {
        return linkaja;
    }
    public String getOvo() {
        return ovo;
    }
    public String getShopeepay() {
        return shopeepay;
    }
    public String getBRI() {
        return bri;
    }
    public String getMandiri() {
        return mandiri;
    }
    public String getBNI() {
        return bni;
    }
    public String getBCA() {
        return bca;
    }
    public String getMatrik() {
        return matrik;
    }
    public String getPengumuman() {
        return pengumuman;
    }
    public Integer getFee_wd() {
        return fee_wd;
    }

}
