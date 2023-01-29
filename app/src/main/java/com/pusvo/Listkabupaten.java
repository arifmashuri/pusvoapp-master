package com.pusvo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Listkabupaten {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("nama")
    @Expose
    private String nama;





    public long getId() {
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




}