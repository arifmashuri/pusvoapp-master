package com.pusvo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BayarActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private Toolbar toolbar;
    String kode_transaksi;
    String harga1;
    String qr;
Context context=this;
    private ProgressDialog progress;
    @BindView(R.id.buttonBayarShopeepay) Button buttonBayarShopeepay;
    @BindView(R.id.buttonBayarLinkaja) Button buttonBayarLinkaja;
    @BindView(R.id.progress_bar) ProgressBar progress_bar;
    @BindView(R.id.qr) ImageView qrimg;
    @BindView(R.id.petunjukbayar) TextView petunjukbayar;
    @BindView(R.id.editTextKode_voucher) EditText editTextKode_voucher;
    @BindView(R.id.editTextKode_voucher2) EditText editTextKode_voucher2;
    @BindView(R.id.pesan) TextView pesan;
    @BindView(R.id.editTextProduk) TextView editTextProduk;
    @BindView(R.id.editTextHarga) TextView editTextHarga;
    @BindView(R.id.editTexthp) TextView editTexthp;
    @BindView(R.id.editTextNama) TextView editTextNama;
    @BindView(R.id.judulvoucher1) TextView judulvoucher1;
    @BindView(R.id.judulvoucher2) TextView judulvoucher2;
    @BindView(R.id.LinearNama) LinearLayout LinearNama;
    @BindView(R.id.LinearMeter) LinearLayout LinearMeter;
    @BindView(R.id.LinearTujuan) LinearLayout LinearTujuan;
    @BindView(R.id.LinearJumlahBulan) LinearLayout LinearJumlahBulan;
   @BindView(R.id.LinearJumlahOrang) LinearLayout LinearJumlahOrang;
   @BindView(R.id.LinearVA) LinearLayout LinearVA;
    @BindView(R.id.LinearVoucher) LinearLayout LinearVoucher;
    @BindView(R.id.LinearVoucher2) LinearLayout LinearVoucher2;
    @BindView(R.id.qrlayout) LinearLayout qrlayout;

  @BindView(R.id.LinearBulan) LinearLayout LinearBulan;
    @BindView(R.id.LinearMemo) LinearLayout LinearMemo;
    @BindView(R.id.editTextJumlahBulan) TextView editTextJumlahBulan;
    @BindView(R.id.editTextJumlahOrang) TextView editTextJumlahOrang;
    @BindView(R.id.editTextMeter) TextView editTextMeter;
    @BindView(R.id.editTextBulan) TextView editTextBulan;
    @BindView(R.id.instruksi2) TextView instruksi2;
    @BindView(R.id.lalu) TextView lalu;
    @BindView(R.id.instruksi) TextView instruksi;
    @BindView(R.id.memo) TextView memo;
    @BindView(R.id.teks_tujuan) TextView teks_tujuan;
    @BindView(R.id.notice) TextView notice;
    @BindView(R.id.shimmer) ShimmerFrameLayout shimmer;
    @BindView(R.id.buttonBayar) Button buttonBayar;


    @BindView(R.id.detail) LinearLayout detail;
    @BindView(R.id.editTextTransaction_id) TextView editTextTransaction_id;
    @OnClick(R.id.copyharga1) void copyharga1() {
        String harga = harga1;
       setClipboard(context,harga);
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }

//    @OnClick(R.id.copyEmail) void copyemail() {
//        setClipboard(context,"pusvoretail@gmail.com");
//        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
//    }
    @OnClick(R.id.copyUsername) void copyUsername() {
        setClipboard(context,teks_tujuan.getText().toString());
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.copyMemo) void copyMemo() {
            setClipboard(context,memo.getText().toString());;
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.saveimg) void saveimg() {

        if (hasWritePermissions()) {
            qrimg.buildDrawingCache();
            Bitmap bm = qrimg.getDrawingCache();
            String nnana= MediaStore.Images.Media.insertImage(getContentResolver(), bm, "QRIS", "QRIS");
            Toast.makeText(context, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
        }
        else {
            requestAppPermissions();
        }
    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 44); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    Integer metode;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrcoords[] = new int[2];
            w.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + w.getLeft() - scrcoords[0];
            float y = event.getRawY() + w.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP
                    && (x < w.getLeft() || x >= w.getRight()
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    @OnClick(R.id.buttonPaste) void losgin() {
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager) clipboardService;
        if (clipboardManager == null) return;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return;
            // Get source text.
            ClipData.Item item = clipData.getItemAt(0);
            if(item!=null && item.getText()!=null) {
                String text = item.getText().toString();
                if (text.length() > 5 && metode == 13) {
                    if (text.substring(0, 8).equals("VIP-IDK-") && Enkripsi.validator(20, 22, "voucher", text)) {
                        // Set the text to target textview.
                        editTextKode_voucher.setText(text);
                    } else {
                        Toast.makeText(context, "Periksa format TX ID", Toast.LENGTH_SHORT).show();

                    }
                }

                if (text.length() > 5 && metode == 8) {
                    if (Enkripsi.validator(6, 6, "nomor", text)) {
                        // Set the text to target textview.
                        editTextKode_voucher.setText(text);
                    } else {
                        Toast.makeText(context, "Periksa format PIN", Toast.LENGTH_SHORT).show();

                    }
                }


                if (text.length() > 5 && metode == 15) {
                    if (Enkripsi.validator(16, 16, "voucher", text)) {
                        // Set the text to target textview.
                        editTextKode_voucher.setText(text);
                    } else {
                        Toast.makeText(context, "Periksa format card code", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }


    @OnClick(R.id.buttonPaste2) void losgin2() {
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager) clipboardService;
        if (clipboardManager == null) return;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return;
        // Get source text.
        ClipData.Item item = clipData.getItemAt(0);
        if(item!=null && item.getText()!=null) {
            String text = item.getText().toString();

            if (text.length() > 5 && metode == 15) {
                if (Enkripsi.validator(16, 16, "nomor", text)) {
                    // Set the text to target textview.
                    editTextKode_voucher2.setText(text);
                } else {
                    Toast.makeText(context, "Periksa format card number", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }
    @OnClick(R.id.buttonBayarShopeepay) void shopeepay() {
        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setAction(Intent.ACTION_VIEW);
        sendIntent.setPackage("com.shopee.id");
        String url = "shopeeid://reactPath?tab=buy&path=shopee%2FTRANSFER_PAGE&navigate_url=https%3A%2F%2Fshopee.co.id%2Fwallet%2Fpay%3Fdeep_and_deferred%3D1%26token%3D" + qr + "&stm_source=rw&stm_medium=referral";
        if(Enkripsi.isPackageInstalled("com.shopee.id",pm)) {
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        }
        else {
            Toast.makeText(context, "Aplikasi Shopee belum terinstal", Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.buttonBayarLinkaja) void linkaja() {


        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setAction(Intent.ACTION_VIEW);
        sendIntent.setPackage("com.telkom.mwallet");
        String url = "linkaja://linkaja.id/applink/payment?data=" + qr;
        if(Enkripsi.isPackageInstalled("com.telkom.mwallet",pm)) {
            sendIntent.setData(Uri.parse(url));
            startActivity(sendIntent);
        }
        else {
            Toast.makeText(context, "Aplikasi Linkaja belum terinstal", Toast.LENGTH_SHORT).show();
        }

    }


    @OnClick(R.id.buttonBayar) void login() {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Sedang memuat...");
        Sprite doubleBounce = new ThreeBounce();
        doubleBounce.setColor(R.color.bg_colordark);
        progress.setIndeterminateDrawable(doubleBounce);
        progress.show();
        pesan.setVisibility(View.GONE);
        //mengambil data dari edittext
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        sharedPrefManager = new SharedPrefManager(this);
        String api_key=sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.konfirmasi("Bearer "+sharedPrefManager.getSPBearer(),kode_transaksi,editTextKode_voucher.getText().toString(),editTextKode_voucher2.getText().toString(),randomUUIDString,SHA1,api_key,api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                progress.dismiss();
                if(response.body()!=null && response.body().getStatus()!=null) {
                    Integer status = response.body().getStatus();
                    String message = response.body().getPesan();

                    if (status.equals(1)) {
                        Intent i = new Intent(context, RincianTransaksi.class);
                        i.putExtra("transaction_kode", kode_transaksi);
                        startActivity(i);

                    } else {
                        pesan.setText(message);
                        pesan.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    pesan.setText(R.string.error500);
                    pesan.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        kode_transaksi = intent.getStringExtra("transaction_kode");



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Transaksi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);

        // Menginisiasi  NavigationView



        shimmer.startShimmer();
        shimmer.setVisibility(View.VISIBLE);
        detail.setVisibility(View.GONE);


        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String SHA1 = Enkripsi.access_key(randomUUIDString);


        final ShimmerFrameLayout shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        shimmer.startShimmer();




        sharedPrefManager = new SharedPrefManager(context);
        String api_key = sharedPrefManager.getSPApikey();
        String username = sharedPrefManager.getSPUsername();

        String api_secret =Enkripsi.api_secret(api_key,username);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.transaksidetail("Bearer "+sharedPrefManager.getSPBearer(),kode_transaksi, randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() != null) {
                    Integer status = response.body().getStatus();
                    Data data = response.body().getData();
                    String pesan = response.body().getPesan();

                    if (status.equals(1)) {
                        if (data.getStatus() == 0) {
                            String id_transaksi = data.getId().toString();
                            getSupportActionBar().setTitle("Transaksi #" + id_transaksi);
                            metode = data.getMetode();
                            Double harga3 = data.getHargajual();
                            //Double harga=Double.parseDouble(Integer.toString(Enkripsi.hargafix(Integer.parseInt(harga3))))/1000;
                            String hargashow = Enkripsi.tentukanhargarupiah(harga3, metode,data.getRand());
                            Double harga = Enkripsi.tentukanharga(harga3, metode,data.getRand());
                            if (metode == 4 || metode == 7 || metode == 5 || metode == 9) {
                                instruksi.setText(R.string.silakan_transfer);
                                editTextHarga.setText(hargashow);
                                harga1 = String.valueOf(harga).split("\\.")[0];
                                petunjukbayar.setVisibility(View.GONE);
                                LinearMemo.setVisibility(View.VISIBLE);
                                memo.setText("PUSVOCOM" + id_transaksi);
                                lalu.setText(R.string.isikan_berita);
                                lalu.setTextColor(getResources().getColor(R.color.merah));
                                lalu.setVisibility(View.VISIBLE);
                                if (metode == 9)
                                    teks_tujuan.setText(R.string.norekovo);
                                if (metode == 4)
                                    teks_tujuan.setText(R.string.norekbca);
                                if (metode == 5)
                                    teks_tujuan.setText(R.string.norekmandiri);
                                if (metode == 7)
                                    teks_tujuan.setText(R.string.norekbni);
                                android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                                teks_tujuan.setLayoutParams(params);
//                        teks_tujuan.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                                LinearTujuan.setVisibility(View.VISIBLE);
                                instruksi2.setText(R.string.ke_nomor_rekening);
                                instruksi2.setVisibility(View.VISIBLE);

                            }

                            if (metode == 8) {

                                editTextHarga.setText(hargashow);
                                harga1 = String.valueOf(harga).split("\\.")[0];
                                editTextKode_voucher.setHint(R.string.pin_google_authenticator);
                                instruksi.setText("Harga :");
                                instruksi.setVisibility(View.VISIBLE);
                                LinearVoucher.setVisibility(View.VISIBLE);
                                judulvoucher1.setText(R.string.masukkan_pin);
                                judulvoucher1.setVisibility(View.VISIBLE);

                                petunjukbayar.setVisibility(View.GONE);
                            }

                            if (metode == 11) {
                                buttonBayar.setVisibility(View.GONE);
                                instruksi.setText(R.string.silakan_shopeepay);
                                buttonBayarShopeepay.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                harga1 = String.valueOf(harga).split("\\.")[0];

                                qr = data.getKodepembayaran();

                                petunjukbayar.setText("Petunjuk pembayaran :\n\n\" +" +
                                        "1. Setelah klik Lanjutkan, aplikasi akan dialihkan ke Shopeepay.\n" +
                                        "2. Konfirmasi pembelian dan masukkan PIN.\n" +
                                        "3. Apabila pembayaran berhasil, status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "4. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami.");
                            }

                            if (metode == 12) {
                                buttonBayar.setVisibility(View.GONE);
                                instruksi.setText(R.string.silakan_shopeepay);
                                buttonBayarLinkaja.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                harga1 = String.valueOf(harga).split("\\.")[0];
                                qr = data.getKodepembayaran();
                                petunjukbayar.setText("Petunjuk pembayaran :\n\n\" +1. Setelah klik Lanjutkan, aplikasi akan dialihkan ke Linkaja.\n" +
                                        "2. Konfirmasi pembelian dan masukkan PIN.\n" +
                                        "3. Apabila pembayaran berhasil, status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "4. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami.");


                            }

                            if (metode == 14) {
                                editTextHarga.setText(hargashow);
                                instruksi.setText(R.string.silakan_kirim_idk_onchain);
                                harga1 = harga.toString();
                                teks_tujuan.setText(R.string.alamatidk_pusvo);
                                //teks_tujuan.setText("tes");
                                LinearTujuan.setVisibility(View.VISIBLE);
                                lalu.setText(R.string.isikan_memo);
                                lalu.setTextColor(getResources().getColor(R.color.merah));
                                lalu.setVisibility(View.VISIBLE);
                                memo.setText(id_transaksi);
                                LinearMemo.setVisibility(View.VISIBLE);
                                instruksi2.setText(R.string.isikan_alamat);
                                instruksi2.setVisibility(View.VISIBLE);
                                petunjukbayar.setText("Petunjuk transfer :\n\n\" +" +
                                        "1. Pastikan saldo IDK di wallet Anda mencukupi untuk melakukan pembayaran.\n" +
                                        "2. Kirim IDK ke wallet Anda ke alamat GDMMM2KPFU3UQKLUPI7GK6MOWVG2BJNXTFBYSYCTTY5SMG4AKRH3CVKR dengan MEMO WAJIB DIISI " + id_transaksi + " .\n" +
                                        "3. Jika nominal IDK yang dikirim sudah benar dan telah terkonfirmasi oleh blockchain XLM maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "4. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami. ");
                            }
                            if (metode == 10) {
                                String qr = data.getKodepembayaran();
                                qrlayout.setVisibility(View.VISIBLE);
                                progress_bar.setVisibility(View.VISIBLE);

                                instruksi.setText(R.string.silakan_scan);
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                                StrictMode.setThreadPolicy(policy);
                                try {
                                    URL newurl = new URL("https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/qris/" + qr + ".png");
                                    qrimg.setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
                                    qrimg.setVisibility(View.VISIBLE);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                buttonBayar.setVisibility(View.GONE);
                                petunjukbayar.setText("Petunjuk pembayaran :\n\n\" +" +
                                        "1. Buka aplikasi E-Wallet/M-Banking Anda.\n" +
                                        "2. Pilih menu pembayaran/pay QRIS.\n" +
                                        "3. Scan QR yang terdapat pada order Anda di Pusvo, jika menggunakan HP yang sama, Anda dapat melakukan screenshot/download QR.\n");
                            }
                            if (metode == 15) {
                                judulvoucher2.setText(R.string.masukkan_nomor_kartu);
                                judulvoucher2.setVisibility(View.VISIBLE);
                                LinearTujuan.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                teks_tujuan.setText(R.string.email_pusvo);
                                instruksi.setText(R.string.silakan_voucher_bidr);
                                harga1 = String.valueOf(harga).split("\\.")[0];
                                instruksi2.setText(R.string.isikan_email);
                                instruksi2.setVisibility(View.VISIBLE);
                                petunjukbayar.setText("Petunjuk pembayaran melalui website Binance :\n\n" +
                                        "1. Jika BIDR masih tersimpan di wallet Fiat&Spot, pindahkan dulu ke saldo Finance(Pembiayaan).\n" +
                                        "2. Klik menu Finance(Keuangan) pada website Binance. Pilih Binance Gift Card(Kartu Hadiah Binance) atau klik link https://www.binance.com/en/gift-card/create-card.\n" +
                                        "3. Klik pada tombol Send Gift Card(Kirim Kartu Hadiah).\n" +
                                        "4. Isikan form dengan BIDR, masukkan jumlah voucher yaitu " + hargashow + ", email tujuan voucher diisi pusvoretail@gmail.com , lalu klik Create/Buat.\n" +
                                        "5. Masukkan PIN sesuai jenis pengamanan pada Akun Anda.\n" +
                                        "6. Untuk melihat kode voucher silakan cek pilih menu Binance Code, pilih Code History(Riwayat Code).\n" +
                                        "7. Copy Card Number (berbentuk angka 16 digit) dan Card Code (berbentuk huruf dan angka 16 digit) di bagian Operation(Operasional)\n" +
                                        "8. Masukkan nomor kartu dan kode voucher tersebut ke halaman order Anda di Pusvo. Klik tombol Konfirmasi.\n" +
                                        "9. Jika nominal voucher sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "10. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami. \n\n\n" +
                                        "Petunjuk pembayaran melalui aplikasi Binance :\n\n" +

                                        "1. Buka aplikasi Binance.\n" +
                                        "2. Jika BIDR masih tersimpan di wallet Fiat&Spot, pindahkan dulu ke saldo Finance(Pembiayaan).\n" +
                                        "3. Pilih menu Profil di pojok kiri atas, lalu pilih Gift Card(Kartu Hadiah). Pilih Send Giftcard(Kirim Hadiah).Klik Create New Card(Buat Kartu Baru).\n" +
                                        "4. Pilih BIDR, masukkan jumlah voucher yaitu " + hargashow + ", email tujuan voucher diisi pusvoretail@gmail.com , lalu klik Next(Berikutnya).\n" +
                                        "5. Masukkan PIN sesuai jenis pengamanan pada Akun Anda.\n" +
                                        "6. Lalu akan muncul nomor kartu 16 digit nomor, kode voucher dapat dilihat di menu riwayat kartu.\n" +
                                        "7. Masukkan Card Number dan Card Code tersebut ke halaman order Anda di Pusvo. Klik tombol Konfirmasi.\n" +
                                        "8. Jika nominal voucher sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "9. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami. ");
                                editTextKode_voucher.setHint(R.string.contoh_voucher_bidr);
                                LinearVoucher.setVisibility(View.VISIBLE);
                                editTextKode_voucher2.setHint(R.string.contoh_cardnumber_bidr);
                                LinearVoucher2.setVisibility(View.VISIBLE);
                                judulvoucher1.setText(R.string.masukkan_kode_kartu);
                                judulvoucher1.setVisibility(View.VISIBLE);
                                lalu.setText(R.string.lalu);
                                lalu.setVisibility(View.VISIBLE);

                            }


                            if (metode == 17) {
                                LinearTujuan.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                teks_tujuan.setText(R.string.address_tko);
                                instruksi.setText(R.string.silakan_transfer_bidr);
                                instruksi2.setText(R.string.isikan_alamat);
                                instruksi2.setVisibility(View.VISIBLE);
                                harga1 = String.valueOf(harga);
                                qrlayout.setVisibility(View.VISIBLE);
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                                StrictMode.setThreadPolicy(policy);
                                try {
                                    URL newurl = new URL("https://chart.apis.google.com/chart?cht=qr&chs=400&chl=0x7db49a30560e6930f0aa6d0ee21b238d5d766217");
                                    qrimg.setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
                                    qrimg.setVisibility(View.VISIBLE);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                petunjukbayar.setText("Petunjuk transfer melalui website Tokocrypto :\n\n" +
                                        "1. Pastikan saldo BIDR di akun Tokocrypto Anda mencukupi untuk melakukan pembayaran.\n" +
                                        "2. Klik menu Dompet pada website Tokocrypto. Klik Penarikan pilih BIDR.\n" +
                                        "3. Pilih jaringan BSC(BEP-20). Kirim BIDR senilai " + hargashow + " ke alamat 0x7db49a30560e6930f0aa6d0ee21b238d5d766217.\n" +
                                        "4. Jika nominal BIDR yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "5. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami. \n\n\n"+

                                                "Petunjuk transfer melalui aplikasi Tokocrypto :\n\n" +
                                                "1. Pastikan saldo BIDR di akun Tokocrypto Anda mencukupi untuk melakukan pembayaran.\n" +
                                                "2. Klik menu Dompet pada aplikasi Tokocrypto.\n" +
                                        "3. Klik Penarikan.\n"+
                                        "4. Pilih Crypto, lalu pilih BIDR.\n"+
                                                "5. Pilih jaringan BSC(BEP-20).\n" +
                                                "6. Isikan alamat BIDR tujuan diisi 0x7db49a30560e6930f0aa6d0ee21b238d5d766217. Kolom jumlah diisi dengan "+hargashow+". Transfer antar Tokocrypto tidak dikenakan biaya, abaikan biaya transaksi yang muncul. Lanjutkan verifikasi.\n" +
                                                "7. Jika nominal BIDR yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n"+
                                                "8. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami."
                                        );

                            }


                            if (metode == 18) {
                                LinearTujuan.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                teks_tujuan.setText(R.string.email_pusvo);
                                instruksi.setText(R.string.silakan_transfer_bidr_binance);
                                instruksi2.setText(R.string.isikan_alamat);
                                instruksi2.setVisibility(View.VISIBLE);
                                harga1 = String.valueOf(harga);


                                petunjukbayar.setText("Petunjuk transfer melalui website Binance :\n\n" +
                                        "1. Pastikan saldo BIDR di akun Binance Anda mencukupi untuk melakukan pembayaran.\n" +
                                        "2. Klik menu Dompet pada website Binance. Pilih Dompet Spot atau Dompet Pendanaan.\n" +
                                        "3. Pilih Kirim.3. Pilih jaringan BSC(BEP-20). Kirim BIDR senilai " + hargashow + " ke alamat pusvoretail@gmail.com.\n" +
                                        "4. Email tujuan diisi pusvoretail@gmail.com kemudian klik Lanjutkan.\n" +
                                        "5. Mata Uang pilih BIDR, jumlah diisi dengan Rp 2.673,23 Salin, klik Lanjutkan.\n"+
                                        "6. Cek kembali transaksi Anda, jika sudah klik Konfirmasi. Lanjutkan verifikasi.\n" +
                                        "7. Jika nominal BIDR yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "8. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami."+
                                        "\n\n\n"+
                                        "Petunjuk transfer melalui aplikasi Binance :\n\n" +

                                        "1. Pastikan saldo BIDR di akun Binance Anda mencukupi untuk melakukan pembayaran.\n" +
                                        "2. Klik menu Dompet, pilih jenis dompet yang digunakan (Spot atau Pendanaan) lalu pilih BIDR.\n" +
                                        "3. Pilih Penarikan.\n" +
                                        "4. Pilih Kirim melalui Email/Telepon/ID.\n"+
                                        "5. Mode Kirim pilih Email. Email tujuan diisi pusvoretail@gmail.com. Kolom jumlah diisi dengan "+hargashow+".\n" +
                                        "6. Cek kembali transaksi Anda, jika sudah klik Berikutnya. Lanjutkan verifikasi.\n" +
                                        "7. Jika nominal BIDR yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n"+
                                        "8. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami."
                                );

                            }




                            if (metode == 13) {
                                LinearTujuan.setVisibility(View.VISIBLE);
                                editTextHarga.setText(hargashow);
                                instruksi.setText(R.string.silakan_kirim_idk);
                                harga1 = harga.toString();
                                instruksi2.setVisibility(View.VISIBLE);
                                petunjukbayar.setText("Petunjuk transfer melalui website Indodax :\n\n" +
                                        "1. Untuk transaksi, pastikan saldo IDK sudah tersedia dan mencukupi untuk membayar order.\n" +
                                        "2. Klik menu Wallet pada website Indodax. Klik Withdraw pada samping bagian IDK.\n" +
                                        "3. Pilih jaringan XLM.\n" +
                                        "4. Masukkan jumlah IDK pada bagian Terima Bersih senilai " + hargashow + ", alamat IDK pilih username, diisi dengan : pusvoretail , lalu masukkan OTP SMS/PIN Google Authenticator.\n" +
                                        "5. Klik tombol KIRIM.\n" +
                                        "6. Periksa email Anda. Pastikan kembali jumlah IDK yang akan dikirim sudah benar. Klik tautan dalam email.\n" +
                                        "7. Lalu akan muncul kode transaction ID yang akan digunakan untuk membayar. Rahasiakan kode transaction ID tersebut dari siapapun, gunakan hanya untuk membayar order di APK/Web Pusvo.\n" +
                                        "8. Masukkan transaction ID tersebut ke halaman order Anda di Pusvo. Klik tombol Konfirmasi\n" +
                                        "9. Jika nominal IDK yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "10. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami.\n\n\n" +
                                        "Petunjuk transfer melalui aplikasi Indodax :\n\n" +

                                        "1. Untuk transaksi, pastikan saldo IDK sudah tersedia dan mencukupi untuk membayar order.\n" +
                                        "2. Buka aplikasi Indodax. Pilih menu Funds, lalu pilih IDK. Pilih Penarikan.\n" +
                                        "3. Pilih jaringan XLM.\n" +
                                        "4. Masukkan jumlah IDK " + Enkripsi.idkrupiah(harga + 1) + " (jika Anda menggunakan OTP SMS) atau " + hargashow + " (jika sudah menggunakan Google Authenticator), alamat IDK pilih username, diisi dengan : pusvoretail. Masukkan OTP SMS/PIN Google Authenticator.\n" +
                                        "5. Klik tombol Kirim.\n" +
                                        "6. Periksa email Anda. Pastikan kembali jumlah IDK yang akan dikirim sudah benar. Klik tautan dalam email.\n" +
                                        "7. Lalu akan muncul kode transaction ID yang akan digunakan untuk membayar. Rahasiakan kode transaction ID tersebut dari siapapun, gunakan hanya untuk membayar order di APK/Web Pusvo.\n" +
                                        "8. Masukkan transaction ID tersebut ke halaman order Anda di Pusvo. Klik tombol Konfirmasi.\n" +
                                        "9. Jika nominal IDK yang dikirim sudah benar maka status order akan berubah menjadi \"sedang diproses\".\n" +
                                        "10. Jika status menunjukan pembayaran belum diterima tetapi Anda sudah merasa membayar, silakan hubungi kami.");
                                editTextKode_voucher.setHint(R.string.contoh_tx_id);
                                LinearVoucher.setVisibility(View.VISIBLE);
                                judulvoucher1.setText(R.string.masukkan_tx_id);
                                judulvoucher1.setVisibility(View.VISIBLE);
                                lalu.setText(R.string.lalu_tx);
                                lalu.setVisibility(View.VISIBLE);
                                android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                                teks_tujuan.setLayoutParams(params);
                            }
                            String hp = data.getHp();
                            String produk = data.getProdukdetail().getNama();
                            Integer jenis = data.getProdukdetail().getJenis();
                            if (jenis.equals(2) || jenis.equals(5) || jenis.equals(6)) {
                                if (hp.length() > 12)
                                    pesan = "Nomor HP yang Anda masukkan lebih dari 12 digit, abaikan pesan ini jika sudah benar";
                                if (hp.length() < 12)
                                    pesan = "Nomor HP yang Anda masukkan kurang dari 12 digit, abaikan pesan ini jika sudah benar";
                            }


                            String meter = data.getMeter();


                            if (data.getOrderdetail() != null) {
                                String nama = data.getOrderdetail().getNama();
                                String jumlahbulan = data.getOrderdetail().getJumlahbulan();
                                String jumlahorang = data.getOrderdetail().getJumlahorang();
                                String bulan = data.getOrderdetail().getBulan();
                                editTextNama.setText(nama);
                                if (nama != null)
                                    LinearNama.setVisibility(View.VISIBLE);

                                editTextJumlahBulan.setText(jumlahbulan);
                                if (jumlahbulan != null)
                                    LinearJumlahBulan.setVisibility(View.VISIBLE);
                                editTextJumlahOrang.setText(jumlahorang);
                                if (jumlahorang != null)
                                    LinearJumlahOrang.setVisibility(View.VISIBLE);
                                editTextBulan.setText(bulan);
                                if (bulan != null)
                                    LinearBulan.setVisibility(View.VISIBLE);
                            }


                            notice.setText(pesan);
                            if (pesan != null && !pesan.isEmpty())
                                notice.setVisibility(View.VISIBLE);
                            editTexthp.setText(hp);
                            editTextProduk.setText(produk);
                            editTextTransaction_id.setText("#" + id_transaksi.toString());
                            editTextMeter.setText(meter);


                            if (meter != null && meter.length() > 4)
                                LinearMeter.setVisibility(View.VISIBLE);

                            detail.setVisibility(View.VISIBLE);

                        } else {
                            Toast.makeText(context, "Status pesanan sudah berubah, silakan cek riwayat transaksi Anda", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, LayarUtama.class));
                        }
                    } else {

                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
                else {

                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
            }
        });
        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi

    }

    public void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("" +
                    " telah dicopy", text);
            clipboard.setPrimaryClip(clip);
        }
    }



}
