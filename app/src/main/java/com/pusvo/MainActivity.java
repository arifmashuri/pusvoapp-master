package com.pusvo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import androidx.appcompat.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.lang.String;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener
{
    public void onBackPressed() {

    };
    Context context=this;
    private AppUpdateManager appUpdateManager;
    private static final int FLEXIBLE_APP_UPDATE_REQ_CODE = 126;

    @OnClick(R.id.profile) void profile() {
        startActivity(new Intent(context, AkunSayaActivity.class));
    };
    @OnClick(R.id.tutorial) void tutorial() {
        startActivity(new Intent(context, TutorialActivity.class));
    };
    @OnClick(R.id.riwayattransaksi) void riwayattransaksi() {
        startActivity(new Intent(context, LayarUtama.class));
    };
    @OnClick(R.id.daftarharga) void daftarharga() {
        startActivity(new Intent(context, CekHargaActivity.class));
    };
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    Userinfo userinfo;
    SliderLayout sliderLayout ;
    SharedPrefManager sharedPrefManager;
    HashMap<String, String> HashMapForURL ;
    @BindView(R.id.saldoakun) TextView saldoakuns;
    @BindView(R.id.pesanupdate) TextView pesanupdate;
    @BindView(R.id.poinakun) TextView poinakuns;
    @BindView(R.id.pengumuman) TextView pengumumans;
    @BindView(R.id.nama_user) TextView nama_user;
    @BindView(R.id.linearpengumuman) LinearLayout linearpengumuman;
    @BindView(R.id.linearupdate) LinearLayout linearupdate;
   @BindView(R.id. linearakun) LinearLayout  linearakun;
    @BindView(R.id.linear_shimmer) LinearLayout linear_shimmer;
    @BindView(R.id.topupgame) LinearLayout topupgame;


    @BindView(R.id.drawer) DrawerLayout drawerLayout;
    @BindView(R.id.shimmer_view_container)  ShimmerFrameLayout shimmer;
    @OnClick(R.id.pulsa) void pulsa() {

        Intent i = new Intent(context, PulsaActivity.class);
        i.putExtra("jenisorder", "pulsa");
        startActivity(i);
};


;
    @OnClick(R.id.deposit2) void deposit2() {
        startActivity(new Intent(context, DepositActivity.class));
    };
    @OnClick(R.id.paketdata) void paketdata() {
        Intent i = new Intent(context, PulsaActivity.class);
        i.putExtra("jenisorder", "kuota");
        startActivity(i);
    };
    @OnClick(R.id.paketsms) void paketsms() {
        Intent i = new Intent(context, PulsaActivity.class);
        i.putExtra("jenisorder", "sms");
        startActivity(i);
}
    @OnClick(R.id.token) void token() {
        startActivity(new Intent(context, TokenActivity.class));
    }
    @OnClick(R.id.wifi) void wifi() {
        startActivity(new Intent(context, WifiActivity.class));
    }
    @OnClick(R.id.ojol) void ojol() {
        startActivity(new Intent(context, EwalletActivity.class));
    }
    @OnClick(R.id.game) void game() {
        startActivity(new Intent(context, GameActivity.class));
    }
    @OnClick(R.id.topup_manual) void topup_manual() {
        startActivity(new Intent(context, TopupActivity.class));
    }
    @OnClick(R.id.wdbinance) void wdbinance() {
        startActivity(new Intent(context, TopupActivity.class));
    }
    @OnClick(R.id.wdtokocrypto) void wdtokocrypto() {
        startActivity(new Intent(context, TopupActivity.class));
    }
    @OnClick(R.id.topupgame) void topupgame() {
    startActivity(new Intent(context, TopupGameActivity.class));
}
    @OnClick(R.id.plnpasca) void plnpasca() {
        startActivity(new Intent(context, PLNPascaActivity.class));
    }
    @OnClick(R.id.bpjspasca) void bpjspasca() {
        startActivity(new Intent(context, BPJSPascaActivity.class));
    }
    @OnClick(R.id.marketplace) void marketplace() {
        startActivity(new Intent(context, MarketplaceActivity.class));
    }
    @OnClick(R.id.tiketkereta) void tiketkereta() {
        Toast.makeText(context, "Coming soon!", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.linearupdate) void updateapk() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pusvo")));
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        new AppEULA(this).show();

//        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
//        checkUpdate();

        Intent intent = getIntent();
        topupgame.setVisibility(View.GONE);
        sharedPrefManager = new SharedPrefManager(context);
        String nama=sharedPrefManager.getSPNama();
        if(!sharedPrefManager.getSPUsername().equals("demoaccount"))
            topupgame.setVisibility(View.VISIBLE);
//        String nama = sharedPrefManager.getSPNama();
        nama_user.setText(nama);
        sliderLayout = (SliderLayout)findViewById(R.id.slider);
        shimmer.startShimmer();
        final SwipeRefreshLayout swLayout = (SwipeRefreshLayout) findViewById(R.id.swlayout);
        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                          @Override
                                          public void onRefresh() {
                                              new Handler().post(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      linear_shimmer.setVisibility(View.VISIBLE);
                                                      shimmer.startShimmer();
                                                      linearakun.setVisibility(View.GONE);
                                                      // Berhenti berputar/refreshing
                                                      swLayout.setRefreshing(true);

                                                      UUID uuid = UUID.randomUUID();
                                                      String randomUUIDString = uuid.toString();
                                                      String SHA1 = Enkripsi.access_key(randomUUIDString);
                                                      sharedPrefManager = new SharedPrefManager(context);
                                                      String api_key = sharedPrefManager.getSPApikey();

                                                      String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
                                                      Retrofit retrofit = new Retrofit.Builder()
                                                              .baseUrl(URL)
                                                              .addConverterFactory(GsonConverterFactory.create())
                                                              .build();
                                                      API api = retrofit.create(API.class);
                                                      Call<Userinfo> call = api.userinfo("Bearer "+sharedPrefManager.getSPBearer(),randomUUIDString, SHA1, api_key, api_secret);
                                                      call.enqueue(new Callback<Userinfo>() {
                                                          @Override
                                                          public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                                                              if (response.body() != null && response.body().getStatus() != null) {
                                                                  Integer status = response.body().getStatus();
                                                                  Userinfo data = response.body().getData();
                                                                  String message = data.getMessage();
                                                                  //Double saldo = response.body().getSaldo();




                                                                  Double saldo = data.getSaldo();
                                                                  Integer poin = data.getPoin();
                                                                  String pengumuman = data.getPengumuman();
                                                                  if (status.equals(1)) {
                                                                      saldoakuns.setText(Enkripsi.rupiah(saldo));
                                                                      poinakuns.setText(poin.toString());
                                                                      linearakun.setVisibility(View.VISIBLE);

                                                                      pengumumans.setText(pengumuman);


                                                                      if (!pengumuman.equals("kosong"))
                                                                          linearpengumuman.setVisibility(View.VISIBLE);
                                                                      else
                                                                          linearpengumuman.setVisibility(View.GONE);
                                                                  } else {
                                                                      Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                                                  }
                                                              }
                                                              else {
                                                                  Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                                                              }
                                                              swLayout.setRefreshing(false);
                                                              linear_shimmer.setVisibility(View.GONE);
                                                              shimmer.stopShimmer();
                                                          }

                                                          @Override
                                                          public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                                                              t.printStackTrace();
                                                              Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                                                              swLayout.setRefreshing(false);
                                                              linear_shimmer.setVisibility(View.GONE);
                                                              shimmer.stopShimmer();
                                                          }
                                                      });
                                                  }
                                              });
                                          }
                                      });
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String SHA1 = Enkripsi.access_key(randomUUIDString);
        sharedPrefManager = new SharedPrefManager(this);
        String api_key = sharedPrefManager.getSPApikey();
        String bearer = sharedPrefManager.getSPBearer();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);

        Call<Userinfo> call = api.userinfo("Bearer "+bearer,randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Userinfo>() {
                         @Override
                         public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                             if (response.body() != null && response.body().getStatus() != null) {
                                 Integer status = response.body().getStatus();
                                 //Double saldo = response.body().getSaldo();
                                 Userinfo data = response.body().getData();
                                 Double saldo = data.getSaldo();
                                 Integer poin = data.getPoin();
                                 String message = data.getMessage();

                                 String pengumuman = data.getPengumuman();
                                 if (status.equals(1)) {
                                     saldoakuns.setText(Enkripsi.rupiah(saldo));
                                     poinakuns.setText(poin.toString());
                                     linearakun.setVisibility(View.VISIBLE);
                                     pengumumans.setText(pengumuman);

                                     if (!pengumuman.equals("kosong"))
                                         linearpengumuman.setVisibility(View.VISIBLE);

                                 } else {
                                     Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                 }


                                 if (intent.getStringExtra("updateapk") != null && intent.getStringExtra("updateapk").equals("3")) {
                                     linearupdate.setVisibility(View.VISIBLE);
                                     pesanupdate.setText(intent.getStringExtra("pesan"));
                                 }
                             }
                             else {
                                 Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                             }
                             swLayout.setRefreshing(false);
                             shimmer.stopShimmer();
                             linear_shimmer.setVisibility(View.GONE);
                         }
            @Override
            public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                swLayout.setRefreshing(false);
                shimmer.stopShimmer();
                linear_shimmer.setVisibility(View.GONE);
            }
                     });
        //Call this method if you want to add images from URL .
        AddImagesUrlOnline();

        //Call this method to add images from local drawable folder .
        //AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();

        for(String name : HashMapForURL.keySet()){

            DefaultSliderView DefaultSliderView = new DefaultSliderView(context);

            DefaultSliderView
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            DefaultSliderView.bundle(new Bundle());

            DefaultSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(DefaultSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(1000);

        sliderLayout.addOnPageChangeListener(MainActivity.this);









        //Mengatur Navigasi View Item yang akan dipanggil untuk menangani item klik menu navigasi

    }


    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<>();

        HashMapForURL.put("3", "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/img/banner1.jpg");
        HashMapForURL.put("5", "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/img/banner2.jpg");
        HashMapForURL.put("4", "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/img/banner3.jpg");
        HashMapForURL.put("2", "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/img/banner4.jpg");
        HashMapForURL.put("1", "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com/img/banner5.jpg");
    }

//    private void checkUpdate() {
//        appUpdateManager = AppUpdateManagerFactory.create(this);
//
//        appUpdateManager.registerListener(installStateUpdatedListener);
//
//        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
//
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){
//
//                try {
//                    appUpdateManager.startUpdateFlowForResult(
//                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/,MainActivity.this, FLEXIBLE_APP_UPDATE_REQ_CODE);
//
//                } catch (IntentSender.SendIntentException e) {
//                    e.printStackTrace();
//                }
//
//            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
//                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
//                popupSnackBarForCompleteUpdate();
//            }
//            else {
//                // Log.e(TAG, "checkForAppUpdateAvailability: something else");
//            }
//        });
//    }

    //    private void startUpdateFlow(AppUpdateInfo appUpdateInfo) {
//        try {
//            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, Flexible.FLEXIBLE_APP_UPDATE_REQ_CODE);
//        } catch (IntentSender.SendIntentException e) {
//            e.printStackTrace();
//        }
//    };
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == FLEXIBLE_APP_UPDATE_REQ_CODE) {
//            if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), "Update canceled by user! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//            } else if (resultCode == RESULT_OK) {
//                Toast.makeText(getApplicationContext(),"Update success! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(getApplicationContext(), "Update Failed! Result Code: " + resultCode, Toast.LENGTH_LONG).show();
//                checkUpdate();
//            }
//        }
//    }



    private void removeInstallStateUpdateListener() {
        if (appUpdateManager != null) {
            appUpdateManager.unregisterListener(installStateUpdatedListener);
        }
    }
    private void popupSnackBarForCompleteUpdate() {
        Snackbar.make(findViewById(android.R.id.content).getRootView(), "New app is ready!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Install", view -> {
                    if (appUpdateManager != null) {
                        appUpdateManager.completeUpdate();
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.bg_color))
                .show();
    }



    InstallStateUpdatedListener installStateUpdatedListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED){
                        //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                        popupSnackBarForCompleteUpdate();
                    } else if (state.installStatus() == InstallStatus.INSTALLED){
                        if (appUpdateManager != null){
                            appUpdateManager.unregisterListener(installStateUpdatedListener);
                        }

                    } else {
                        //  Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                        //  Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
                        Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
                    }
                }
            };

}
