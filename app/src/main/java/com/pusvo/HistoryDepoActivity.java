
    package com.pusvo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HistoryDepoActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private Toolbar toolbar;
    private List<Data> results = new ArrayList<>();
    private RecyclerViewAdapter4 viewAdapter;
    Context context=this;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.swlayout) SwipeRefreshLayout swLayout;
    @BindView(R.id.shimmer) ShimmerFrameLayout shimmer;
    //@BindView(R.id.detail) LinearLayout detail;
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







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historydepo);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Riwayat Deposit");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        sharedPrefManager = new SharedPrefManager(this);

      //  final SwipeRefreshLayout swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadhistorydeposit();

                    }
                });

            }

            //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadhistorydeposit();
    }
private void loadhistorydeposit() {
    swLayout.setRefreshing(true);

    shimmer.setVisibility(View.VISIBLE);
    shimmer.startShimmer();
    UUID uuid = UUID.randomUUID();
    String randomUUIDString = uuid.toString();
    String SHA1 = Enkripsi.access_key(randomUUIDString);
    sharedPrefManager = new SharedPrefManager(context);
    String api_key=sharedPrefManager.getSPApikey();
    String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    Call<Data> call = api.riwayatdeposit("Bearer "+sharedPrefManager.getSPBearer());
    call.enqueue(new Callback<Data>() {
        @Override
        public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
            shimmer.setVisibility(View.GONE);
            if (response.body() != null && response.body().getStatus() != null) {
                Integer status = response.body().getStatus();


                if (status.equals(1)) {
                    results = response.body().getData().getListOrder();
                    viewAdapter = new RecyclerViewAdapter4(context, results);
                    recyclerView.setAdapter(viewAdapter);
                    swLayout.setRefreshing(false);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setVisibility(View.VISIBLE);
                    //detail.setVisibility(View.VISIBLE);
                }
            }
            else {
                Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<Data> call, Throwable t) {
            t.printStackTrace();
            Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            shimmer.setVisibility(View.GONE);
            swLayout.setRefreshing(false);
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