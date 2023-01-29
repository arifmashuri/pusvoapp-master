package com.pusvo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CekHargaActivity extends AppCompatActivity {

    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
    };

    @OnClick(R.id.profile) void profile() {
        startActivity(new Intent(context, AkunSayaActivity.class));
    };
    @OnClick(R.id.tutorial) void deposit() {
        startActivity(new Intent(context, TutorialActivity.class));
    };
    @OnClick(R.id.riwayattransaksi) void riwayattransaksi() {
        startActivity(new Intent(context, LayarUtama.class));
    };
    @OnClick(R.id.home) void home() {
        startActivity(new Intent(context, MainActivity.class));
    };
    
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private Toolbar toolbar;
    Context context=this;
    private List<Produk> results = new ArrayList<>();
    private RecyclerViewAdapter3 viewAdapter;
    @BindView(R.id.LayoutWD) LinearLayout LayoutWD;
    @BindView(R.id.LayoutHarga) LinearLayout LayoutHarga;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.scrollview)  ScrollView scrollview;
    @BindView(R.id.shimmer_view_container) ShimmerFrameLayout shimmer;

    @OnClick(R.id.pulsa) void pulsa() {
        filterdata(5);
    }
    @OnClick(R.id.paket) void paket() {
        filterdata(2);
    }
    @OnClick(R.id.ojol) void ojol() {
        filterdata(9);
    }
    @OnClick(R.id.topupgame) void topupgame() {
        filterdata(13);
    }
    @OnClick(R.id.token) void token() {
        filterdata(1);
    }
    @OnClick(R.id.game) void game() {
        filterdata(3);
    }
    @OnClick(R.id.paketsms) void paketsms() {
        filterdata(6);
    }
    @OnClick(R.id.wifi_id) void wifi_id() {
        filterdata(11);
    }
    @OnClick(R.id.wd) void wd() {
        filterdata(10);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cekharga);
        ButterKnife.bind(this);
        shimmer.startShimmer();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Harga");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);

        viewAdapter = new RecyclerViewAdapter3(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        tarikdata();


        // Menginisiasi  NavigationView

    }


private void filterdata(Integer jenis) {
    if (jenis==10) {
        LayoutWD.setVisibility(View.VISIBLE);
        LayoutHarga.setVisibility(View.GONE);
        scrollview.setVisibility(View.GONE);

    } else {
        List<Produk> terfilter = new ArrayList<>();
        for (Produk b : results) {
            if (b.getJenis().equals(jenis)) {
                terfilter.add(b);
            }
        }
        viewAdapter = new RecyclerViewAdapter3(context, terfilter);
        recyclerView.setAdapter(viewAdapter);
        LayoutWD.setVisibility(View.GONE);
        LayoutHarga.setVisibility(View.VISIBLE);
        scrollview.setVisibility(View.VISIBLE);
    }
}

    private void tarikdata() {

            scrollview.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);

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
            //Call<Userinfo> call = api.daftarharga(jenis,randomUUIDString,SHA1,api_key,api_secret);
        Call<Data> call = api.dataharga();
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                    shimmer.setVisibility(View.GONE);

                    if (response.body() != null && response.body().getStatus() != null) {
                        Integer status = response.body().getStatus();

                        scrollview.setVisibility(View.VISIBLE);
                        if (status.equals(1)) {
                            results = response.body().getData().getProduk();
//                        viewAdapter = new RecyclerViewAdapter3(context, results);
//                        recyclerView.setAdapter(viewAdapter);
                            filterdata(9);
                        } else
                            Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                    shimmer.setVisibility(View.GONE);
                    Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}