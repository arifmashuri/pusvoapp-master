package com.pusvo;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BayarDepoActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private Toolbar toolbar;

    private ProgressDialog progress;
    Context context=this;
    @BindView(R.id.editTextKode_voucher) EditText editTextKode_voucher;
    @BindView(R.id.pesan) TextView pesan;
    @BindView(R.id.editTextTransaction_id) TextView editTextTransaction_id;
    @BindView(R.id.editTextTransaction_kode) TextView editTextTransaction_kode;
    @BindView(R.id.editTextJumlah) TextView editTextJumlah;
    @BindView(R.id.editTextHarga) TextView editTextHarga;
    @BindView(R.id.editTextHarga2) TextView editTextHarga2;
    @BindView(R.id.viewharga1) TextView harga1;
    @BindView(R.id.viewharga2) TextView harga2;

    @OnClick(R.id.copyharga1) void copyharga1() {
        String harga = harga1.getText().toString();
        setClipboard(BayarDepoActivity.this,harga);
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.copyharga2) void copyharga2() {
        String harga = harga2.getText().toString();
        setClipboard(BayarDepoActivity.this,harga);
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }
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
            String text = item.getText().toString();
            if (item.getText().toString().length() > 10) {
                if (text.substring(0, 8).equals("BTC-IDR-")) {
                    // Set the text to target textview.
                    editTextKode_voucher.setText(text);
                } else {
                    Toast.makeText(BayarDepoActivity.this, "Periksa format voucher", Toast.LENGTH_SHORT).show();

                }
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
        String transaction_kode = editTextTransaction_kode.getText().toString();
        String jumlah = editTextJumlah.getText().toString();
        String kode_voucher = editTextKode_voucher.getText().toString();
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
        Call<Data> call = api.konfirmasideposit("Bearer "+sharedPrefManager.getSPBearer(),transaction_kode,kode_voucher,randomUUIDString,SHA1,api_key,api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                progress.dismiss();
                if (response.body() != null && response.body().getStatus() != null) {
                    Integer status = response.body().getStatus();
                    String message = response.body().getPesan();

                    if (status.equals(1)) {
                        Toast.makeText(BayarDepoActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        pesan.setVisibility(View.VISIBLE);
                        Toast.makeText(BayarDepoActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(BayarDepoActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayardepo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String transaction_kode = intent.getStringExtra("transaction_kode");
        String transaction_id = intent.getStringExtra("transaction_id");
        String harga = intent.getStringExtra("harga");
        String jumlah = intent.getStringExtra("jumlah");
        editTextTransaction_kode.setText(transaction_kode);
        editTextTransaction_id.setText("#"+transaction_id);
        editTextJumlah.setText(jumlah);
        editTextKode_voucher.setText("");
        int tarifsms=1000;
        Double tarifsms2=tarifsms+Double.parseDouble(harga);
        harga1.setText(Enkripsi.decimalformat.format(tarifsms2));
        harga2.setText(Enkripsi.decimalformat.format(Double.parseDouble(harga)));
        editTextHarga.setText(Enkripsi.rupiah(tarifsms2)+" (SMS Authenticator)");
        editTextHarga2.setText(Enkripsi.rupiah(Double.parseDouble(harga))+" (Google Authenticator)");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pembayaran Deposit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        // Menginisiasi  NavigationView


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
