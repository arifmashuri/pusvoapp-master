package com.pusvo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface API {

    @FormUrlEncoded
    @POST("/api/masuk.json")
    Call<Userinfo> masuk(
            @Field("username") String username,
            @Field("password") String password,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key);

    @FormUrlEncoded
    @POST("/api/google-login")
    Call<Userinfo> masukgoogle(
            @Field("email") String email,
            @Field("token") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key);


    @FormUrlEncoded
    @POST("/api/facebook-login")
    Call<Userinfo> masukfacebook(
            @Field("email") String email,
            @Field("token") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key);



    @FormUrlEncoded
    @POST("/api/simpankyc")
    Call<Data> simpankyc(
            @Header("Authorization") String token,
            @Field("email_indodax") String indodax,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("bank1") String bank1,
            @Field("bank2") String bank2,
            @Field("bank3") String bank3,
            @Field("bank4") String bank4,
            @Field("bank5") String bank5,
            @Field("nomorrekening1") String nomorrekening1,
            @Field("nomorrekening2") String nomorrekening2,
            @Field("nomorrekening3") String nomorrekening3,
            @Field("nomorrekening4") String nomorrekening4,
            @Field("nomorrekening5") String nomorrekening5,
            @Field("kecamatan") String kecamatan,
            @Field("desa") String desa,
            @Field("alamat") String alamat,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/checkout.json")
//    Call<Value> pushtx(
//            @Field("hp") String hp,
//            @Field("vendor") String vendor,
//            @Field("produk") String produk,
//            @Field("metode") String metode,
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/simpantransaksi")
    Call<Data> simpantransaksi(
            @Header("Authorization") String token,
            @Field("hp") String hp,
            @Field("meter") String meter,
            @Field("produk_id") Integer produk,
            @Field("metode") Integer metode,
            @Field("vapps") String vapps,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/checkouttoken.json")
//    Call<Value> savetoken(@Field("hp") String hp,
//                          @Field("meter") String meter,
//                          @Field("produk") String produk,
//                          @Field("metode") String metode,
//                          @Field("access_token") String access_token,
//                          @Field("access_key") String access_key,
//                          @Field("api_key") String api_key,
//                          @Field("api_secret") String api_secret);



    @FormUrlEncoded
    @POST("/api/lupa-password")
    Call<Userinfo> lupapassword(
            @Field("email") String email,
            @Field("pin_google_authenticator") String pin,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key);


    @FormUrlEncoded
    @POST("/api/lupa-username")
    Call<Userinfo> lupausername(
            @Field("email") String email,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key);

    @FormUrlEncoded
    @POST("/api/simpantransaksitopup")
    Call<Data> simpantransaksitopup(
            @Header("Authorization") String token,
            @Field("jumlah") String jumlah,
            @Field("manual") Integer manual,
            @Field("bank_id") Integer idkycdefault,
            @Field("metode") Integer metode,
            @Field("vapps") String vapps,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/checkoutmarketplace.json")
//    Call<Value> savemarketplace(@Field("hp") String hp,
//                          @Field("meter") String meter,
//                          @Field("produk") String produk,
//                          @Field("metode") String metode,
//                          @Field("access_token") String access_token,
//                          @Field("access_key") String access_key,
//                          @Field("api_key") String api_key,
//                          @Field("api_secret") String api_secret);


//    @FormUrlEncoded
//    @POST("/api/checkoutpasca.json")
//    Call<Value> savepasca(@Field("hp") String hp,
//                                @Field("meter") String meter,
//                                @Field("produk") String produk,
//                                @Field("metode") String metode,
//                                @Field("access_token") String access_token,
//                                @Field("access_key") String access_key,
//                                @Field("api_key") String api_key,
//                                @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/kycstatus.json")
    Call<Data> kycstatus(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);
    @FormUrlEncoded
    @POST("/api/simpandeposit")
    Call<Data> simpandeposit(
            @Header("Authorization") String token,
            @Field("jumlah") String jumlah,
            @Field("metode") Integer metode,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/checkoutpascabpjs.json")
//    Call<Value> savepascabpjs(@Field("hp") String hp,
//                          @Field("meter") String meter,
//                          @Field("produk") String produk,
//                          @Field("metode") String metode,
//                          @Field("access_token") String access_token,
//                          @Field("access_key") String access_key,
//                          @Field("api_key") String api_key,
//                          @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/checkouttopupgame.json")
//    Call<Value> pushtxtopupgame(@Field("hp") String hp,
//                          @Field("meter") String meter,
//                         @Field("vendor") String vendor,
//                          @Field("produk") String produk,
//                          @Field("metode") String metode,
//                          @Field("access_token") String access_token,
//                          @Field("access_key") String access_key,
//                          @Field("api_key") String api_key,
//                          @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/konfirmasiqris")
    Call<Data> konfirmasiqris(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/konfirmasi")
    Call<Data> konfirmasi(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("voucherorpin") String kode_voucher,
            @Field("voucherbinance") String kode_voucher2,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);



    @FormUrlEncoded
    @POST("/api/konfirmasitopup")
    Call<Data> konfirmasitopup(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("voucherorpin") String kode_voucher,
            @Field("voucherbinance") String kode_voucher2,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/konfirmasideposit")
    Call<Data> konfirmasideposit(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("vouchervip") String kode_voucher,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
////    @POST("/api/daftar_harga.json")
//    @POST("/api/produk")
//    Call<Userinfo> daftarharga(
//            @Field("jenis") String jenis,
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);


    @FormUrlEncoded
    @POST("/api/terimatoken.json")
    Call<Userinfo> kirimtoken(
            @Field("api_key") String api_key,
            @Field("token") String token
            );


    @FormUrlEncoded
    @POST("/api/keluar")
    Call<Userinfo> keluar(
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret
    );



//    @FormUrlEncoded
//    @POST("/api/rincian-transaksi.json")
//    Call<Value> rinciantransaksi(
//            @Header("Authorization") String token,
//            @Field("transaction_kode") String transaction_kode,
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/transaksidetail")
    Call<Data> transaksidetail(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);


    @FormUrlEncoded
    @POST("/api/depositdetail")
    Call<Data> depositdetail(
            @Header("Authorization") String token,
            @Field("kode") String transaction_kode,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/status-topup.json")
//    Call<Value> statustopup(
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);

    //@FormUrlEncoded
    @GET("/api/deposit")
    Call<Data> riwayatdeposit(
            @Header("Authorization")String token);


    @GET("/api/harga")
    Call<Data> dataharga();;

    @FormUrlEncoded
    @POST("/api/produk")
    Call<Data> dataproduk(
            @Field("jenis") Integer produk);

//    @FormUrlEncoded
//    @POST("/api/produk.json")
//    Call<Value> produkssss(
//            @Field("produk") String produk);

//    @FormUrlEncoded
//    @POST("/api/alamat.json")
//    Call<Data> alamat(
//            @Field("jenis") String jenis,
//            @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("/api/kabupatenkota")
    Call<Data> kabupatenkota(
            @Header("Authorization") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("/api/kecamatan")
    Call<Data> kecamatan(
            @Header("Authorization") String token,
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("/api/desa")
    Call<Data> desa(
            @Header("Authorization") String token,
            @Field("id") String id
    );


    @FormUrlEncoded
    @POST("/api/riwayat-transaksi-unpaid")
    Call<Data> riwayattransaksiunpaid(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/riwayat-transaksi-sukses")
    Call<Data> riwayattransaksisukses(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);


    @FormUrlEncoded
    @POST("/api/riwayat-transaksi-gagal")
    Call<Data> riwayattransaksigagal(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/riwayat-transaksi-proses")
    Call<Data> riwayattransaksiproses(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);


    @FormUrlEncoded
    @POST("/api/riwayat-transaksi")
    Call<Data> riwayattransaksi(
            @Header("Authorization") String token,
            @Field("jenis") Integer jenis);


    @FormUrlEncoded
    @POST("/api/cek-login.json")
    Call<Userinfo> ceklogin(
            @Field("vapps") String vapps,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

//    @FormUrlEncoded
//    @POST("/api/akun.json")
//    Call<Value> akun(
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/gantipassword")
    Call<Userinfo> gantipassword(
            @Header("Authorization") String token,
            @Field("password_lama") String password_lama,
            @Field("password") String password_baru,
            @Field("konfirmasi_password") String password_baru2,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);

    @FormUrlEncoded
    @POST("/api/userinfo")
    Call<Userinfo> userinfo(
            @Header("Authorization") String token,
            @Field("access_token") String access_token,
            @Field("access_key") String access_key,
            @Field("api_key") String api_key,
            @Field("api_secret") String api_secret);


//    @FormUrlEncoded
//    @POST("/api/list-deposit.json")
//    Call<Value> listdeposit(
//            @Field("access_token") String access_token,
//            @Field("access_key") String access_key,
//            @Field("api_key") String api_key,
//            @Field("api_secret") String api_secret);

}
