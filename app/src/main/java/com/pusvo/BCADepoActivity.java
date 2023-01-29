package com.pusvo;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class BCADepoActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private Toolbar toolbar;

    private ProgressDialog progress;
    Context context=this;
    @BindView(R.id.editTextTransaction_id) TextView editTextTransaction_id;
    @BindView(R.id.editTextTransaction_kode) TextView editTextTransaction_kode;
    @BindView(R.id.editTextID_depo) TextView editTextID_depo;
    @BindView(R.id.editTextJumlah) TextView editTextJumlah;
    @BindView(R.id.editTextHarga) TextView editTextHarga;
    @BindView(R.id.editTextNorek) TextView editTextNorek;
    @BindView(R.id.shimmer) ShimmerFrameLayout shimmer;
    @BindView(R.id.detail) LinearLayout detail;

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



    @OnClick(R.id.buttonBayar) void login() {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Sedang memuat...");
        Sprite doubleBounce = new ThreeBounce();
        doubleBounce.setColor(R.color.bg_colordark);
        progress.setIndeterminateDrawable(doubleBounce);
        progress.show();

        //mengambil data dari edittext
        String transaction_kode = editTextTransaction_kode.getText().toString();
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
        Call<Data> call = api.konfirmasideposit("Bearer "+sharedPrefManager.getSPBearer(),transaction_kode,"",randomUUIDString,SHA1,api_key,api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                progress.dismiss();
                if (response.body() != null && response.body().getStatus() != null) {
                    Integer status = response.body().getStatus();
                    String pesan = response.body().getPesan();
                    if (status.equals(1)) {
                        Toast.makeText(BCADepoActivity.this, pesan, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(BCADepoActivity.this, pesan, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(BCADepoActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcadepo);
        final ShimmerFrameLayout shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        shimmer.startShimmer();
        shimmer.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        sharedPrefManager = new SharedPrefManager(BCADepoActivity.this);
        String api_key = sharedPrefManager.getSPApikey();
        String username = sharedPrefManager.getSPUsername();
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String SHA1 = Enkripsi.access_key(randomUUIDString);

        String api_secret =Enkripsi.api_secret(api_key,username);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        String transaction_kode = intent.getStringExtra("transaction_kode");
        Call<Data> call = api.depositdetail("Bearer "+sharedPrefManager.getSPBearer(),transaction_kode, randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() != null) {
                    Integer status = response.body().getStatus();
                    Data data = response.body().getData();
                    if (status.equals(1)) {
                        Integer transaction_id = data.getId();
                        Integer metode = data.getMetode();
                        editTextTransaction_id.setText("#" + transaction_id.toString());
                        editTextTransaction_kode.setText(transaction_kode);
                        editTextJumlah.setText(data.getJumlah());
                        if (metode.equals(4))
                            editTextNorek.setText(R.string.instruksibca);
                        if (metode.equals(7))
                            editTextNorek.setText(R.string.instruksibni);
                        if (metode.equals(5))
                            editTextNorek.setText(R.string.instruksimandiri);
                        if (metode.equals(9))
                            editTextNorek.setText(R.string.instruksiovo);
                        editTextID_depo.setText("PUSVODEP" + transaction_id.toString());
                        editTextHarga.setText(Enkripsi.rupiah(Double.parseDouble(data.getJumlah())));

                        detail.setVisibility(View.VISIBLE);
                    } else {
                        String message = response.body().getPesan();
                        Toast.makeText(BCADepoActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(BCADepoActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pembayaran Deposit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);


    }





}
