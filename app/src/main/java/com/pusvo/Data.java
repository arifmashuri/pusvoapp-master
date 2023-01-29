package com.pusvo;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("order")
    @Expose
    private List<Data> Listorder;
    @SerializedName("listkyc")
    @Expose
    private List<Listkyc> Listkyc = null;
    @SerializedName("listkabupaten")
    @Expose
    private List<Listkabupaten> Listkabupaten = null;

    @SerializedName("listkecamatan")
    @Expose
    private List<Listkabupaten> Listkecamatan = null;

    @SerializedName("listdesa")
    @Expose
    private List<Listkabupaten> Listdesa = null;

    @SerializedName("servicestatus")
    @Expose
    private Servicestatus servicestatus;

    @SerializedName("listbank")
    @Expose
    private List<Bankdetail> Listbank = null;
    @SerializedName("pesan")
    @Expose
    private String pesan;

    @SerializedName("operator")
    @Expose
    private List<Operator> operator = null;

    @SerializedName("produk")
    @Expose
    private List<Produk> produk = null;

    @SerializedName("metodetersedia")
    @Expose
    private List<Metode> metodetersedia = null;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("metode")
    @Expose
    private Integer metode;
    @SerializedName("sn")
    @Expose
    private String sn;
    @SerializedName("kode")
    @Expose
    private String kode;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("operator_id")
    @Expose
    private Integer operatorId;
    @SerializedName("kycstatus")
    @Expose
    private Integer kycstatus;
    @SerializedName("waktu")
    @Expose
    private long waktu;
    @SerializedName("waktu_bayar")
    @Expose
    private long waktuBayar;
    @SerializedName("waktu_sukses")
    @Expose
    private long waktuSukses;
    @SerializedName("meter")
    @Expose
    private String meter;

    @SerializedName("hargajual")
    @Expose
    private Double hargajual;
    @SerializedName("rand")
    @Expose
    private Double rand;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("produkdetail")
    @Expose
    private Produkdetail produkdetail;
    @SerializedName("orderdetail")
    @Expose
    private Orderdetail orderdetail;
    @SerializedName("kodepembayaran")
    @Expose
    private String kodepembayaran;

    public List<Operator> getOperator() {
        return operator;
    }

    public void setOperator(List<Operator> operator) {
        this.operator = operator;
    }

    public List<Produk> getProduk() {
        return produk;
    }
    public List<Metode> getMetodetersedia() {
        return metodetersedia;
    }

    public void setProduk(List<Produk> produk) {
        this.produk = produk;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public Integer getMetode() {
        return metode;
    }



    public void setMetode(Integer metode) {
        this.metode = metode;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperatorId() {
        return operatorId;
    }
    public Integer getKYCStatus() {
        return kycstatus;
    }
    public Servicestatus getServicestatus() {
        return servicestatus;
    }


    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public long getWaktu() {
        return waktu;
    }

    public void setWaktu(long waktu) {
        this.waktu = waktu;
    }

    public long getWaktuBayar() {
        return waktuBayar;
    }

    public void setWaktuBayar(long waktuBayar) {
        this.waktuBayar = waktuBayar;
    }

    public long getWaktuSukses() {
        return waktuSukses;
    }

    public void setWaktuSukses(long waktuSukses) {
        this.waktuSukses = waktuSukses;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
    }



    public void setSn(String sn) {
        this.sn = sn;
    }

    public Double getHargajual() {
        return hargajual;
    }

    public void setHargajual(Double hargajual) {
        this.hargajual = hargajual;
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

    public Produkdetail getProdukdetail() {
        return produkdetail;
    }

    public void setProdukdetail(Produkdetail produkdetail) {
        this.produkdetail = produkdetail;
    }

    public Orderdetail getOrderdetail() {
        return orderdetail;
    }

    public void setOrderdetail(Orderdetail orderdetail) {
        this.orderdetail = orderdetail;
    }

    public String getKodepembayaran() {
        return kodepembayaran;
    }

    public void setKodepembayaran(String kodepembayaran) {
        this.kodepembayaran = kodepembayaran;
    }

    public Data getData() {
        return data;
    }

    public String getPesan() {
        return pesan;
    }
    public String getSN() {
        return sn;
    }
    public Double getRand() {
        return rand;
    }
    public List<Data> getListOrder() {
        return Listorder;
    }
    public List<Listkyc> getListkyc() {
        return Listkyc;
    }

    public List<Listkabupaten> getListkabupaten() {
        return Listkabupaten;
    }
    public List<Listkabupaten> getListkecamatan() {
        return Listkecamatan;
    }
    public List<Listkabupaten> getListdesa() {
        return Listdesa;
    }
    public List<Bankdetail> getListbank() {
        return Listbank;
    }
    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}