package com.pusvo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BlankFragmentBaru extends Fragment {




    SwipeRefreshLayout swLayout;
    RecyclerView recyclerView;
    ShimmerFrameLayout shimmer;

    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    private AdapterSemua adapterSemua;

    private List<Data> results = new ArrayList<>();
    Integer jenis;
    SharedPrefManager sharedPrefManager;
    //    @BindView(R.id.rv_recycler_view) RecyclerView recyclerView2;
    public BlankFragmentBaru(Integer cari) {
        jenis=cari;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView( @Nullable LayoutInflater  inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_blank, container, false);

       recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
       swLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swlayout);
        shimmer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimmer);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        final  ProgressBar ProgressBar =  rootView.findViewById(R.id.progress_bar);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                          @Override
                                          public void onRefresh() {

                                              new Handler().post(new Runnable() {
                                                  @Override public void run() {
                                                      loadhistory();
                                                  }
                                              });
                                              //Toast.makeText(getActivity(), "Cek", Toast.LENGTH_SHORT).show();

                                          }
                                      });



        loadhistory();
        return rootView;



    }
    private void loadhistory() {
        swLayout.setRefreshing(true);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmer();
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        sharedPrefManager = new SharedPrefManager(getActivity());
        String api_key=sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.riwayattransaksi("Bearer "+sharedPrefManager.getSPBearer(),jenis);
        call.enqueue(new Callback<Data>() {

            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
                if(response.body()!=null && response.body().getStatus()!=null) {
                    Integer status = response.body().getStatus();
                    if (status.equals(1)) {
                        swLayout.setRefreshing(false);
                        results = response.body().getData().getListOrder();
                        adapterSemua = new AdapterSemua(getActivity(), results);
                        recyclerView.setAdapter(adapterSemua);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }
                else {
                    Toast.makeText(getActivity(), R.string.error500, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                swLayout.setRefreshing(false);
                //todo perbaiki
                Toast.makeText(getActivity(), "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                shimmer.setVisibility(View.GONE);
                Log.d("dalam perbaikan","s"+t);
            }
        });
    };


}