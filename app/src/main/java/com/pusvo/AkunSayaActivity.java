package com.pusvo;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AkunSayaActivity extends AppCompatActivity {
    BluetoothAdapter mBluetoothAdapter;
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private Toolbar toolbar;
    BluetoothDevice mBluetoothDevice;
    SharedPrefManager sharedPrefManager;
    GoogleSignInClient mGoogleSignInClient;
    Context context = this;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                }
                else {
                    Toast.makeText(context, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
            });


    public void onBackPressed() {
        startActivity(new Intent(AkunSayaActivity.this, MainActivity.class));
    }

    ;

    @OnClick(R.id.home)
    void home() {
        startActivity(new Intent(AkunSayaActivity.this, MainActivity.class));
    }

    ;

    @OnClick(R.id.riwayattransaksi)
    void riwayattransaksi() {
        startActivity(new Intent(AkunSayaActivity.this, LayarUtama.class));
    }

    ;

    @OnClick(R.id.kyc)
    void kyc() {
        startActivity(new Intent(AkunSayaActivity.this, KYCActivity.class));
    }

    @OnClick(R.id.daftarharga)
    void deposit2() {
        startActivity(new Intent(AkunSayaActivity.this, CekHargaActivity.class));
    }

    ;

    @OnClick(R.id.kontakkami)
    void kontakkami() {
        startActivity(new Intent(AkunSayaActivity.this, KontakKamiActivity.class));
    }

    @OnClick(R.id.tentangkami)
    void tentangkami() {
        startActivity(new Intent(AkunSayaActivity.this, TentangKamiActivity.class));
    }

    @OnClick(R.id.sk)
    void sk() {
        startActivity(new Intent(AkunSayaActivity.this, KetentuanLayananActivity.class));
    }

    ;

    @OnClick(R.id.bantuan)
    void bantuan() {
        startActivity(new Intent(AkunSayaActivity.this, BantuanActivity.class));
    }

    ;

    @OnClick(R.id.testimoni)
    void testimoni() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pusvo")));
    }

    ;

    @OnClick(R.id.tutorial)
    void tutorial() {
        startActivity(new Intent(AkunSayaActivity.this, TutorialActivity.class));
    }

    ;

    @OnClick(R.id.keamanan)
    void keamanan() {
        startActivity(new Intent(AkunSayaActivity.this, Keamanan.class));
    }

    ;

    @OnClick(R.id.resetauthenticator)
    void gantipin() {
        startActivity(new Intent(AkunSayaActivity.this, ResetAuthenticator.class));
    }

    ;

    @OnClick(R.id.printer_setting)
    void printer_setting() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth tidak didukung", Toast.LENGTH_SHORT).show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(AkunSayaActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_ENABLE_BT);
                    return;
                }
            }

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activityResultLauncher.launch(enableBtIntent);
            } else {
                Intent connectIntent = new Intent(context,DeviceListActivity.class);
                activityResultLauncher.launch(connectIntent);
            }
        }

    }

    ;

    @BindView(R.id.detail)
    LinearLayout detail;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    @BindView(R.id.btdevice)
    TextView btname;
    @BindView(R.id.editTexthp)
    TextView editTexthp;
    @BindView(R.id.editTextnama)
    TextView editTextnama;
    @BindView(R.id.editTextemail)
    TextView editTextemail;
    @BindView(R.id.editTextusername)
    TextView editTextusername;
    @BindView(R.id.editTextsaldo)
    TextView editTextsaldo;
    @BindView(R.id.singkatan)
    TextView singkatan;
    @BindView(R.id.tekskyc)
    TextView tekskyc;
    @BindView(R.id.versiaplikasi)
    TextView versiaplikasi;
    @BindView(R.id.bottombar)
    BottomBar mBottomBar;
    @BindView(R.id.profile_photo)
    ImageView profile_photo;
    @BindView(R.id.linearsingkatan)
    LinearLayout linearsingkatan;
    //@BindView(R.id.radioProduk) RadioGroup radioGroup2;


    @OnClick({R.id.buttonSubmit, R.id.buttonSubmit2})
    void ubah() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        String api_key = sharedPrefManager.getSPApikey();
        String api_secret = Enkripsi.api_secret(api_key, sharedPrefManager.getSPUsername());
        Call<Userinfo> call = api.keluar(api_key, api_secret);


        call.enqueue(new Callback<Userinfo>() {
                         @Override
                         public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                             if (response.body() != null && response.body().getStatus() != null) {
                                 Integer status = response.body().getStatus();
                                 if (status.equals(1)) {

                                     GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                             .requestIdToken("764817079788-ccbba1k7752qr0jv489f79n3nl1n9che.apps.googleusercontent.com")
                                             .requestEmail()
                                             .build();
                                     mGoogleSignInClient = GoogleSignIn.getClient(context, gso);
                                     mGoogleSignInClient.signOut();
                                     sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                                     sharedPrefManager.saveSPString(SharedPrefManager.SP_USERNAME, "ss");
                                     sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, "User");
                                     sharedPrefManager.saveSPString(SharedPrefManager.SP_API_KEY, "DD");
                                     Toast.makeText(AkunSayaActivity.this, "Berhasil logout", Toast.LENGTH_SHORT).show();
                                     startActivity(new Intent(AkunSayaActivity.this, MasukActivity.class)
                                             .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                     finish();
                                 }
                                 if (status.equals(0)) {
                                     Toast.makeText(AkunSayaActivity.this, "Gagal logout", Toast.LENGTH_SHORT).show();
                                 }
                             } else {
                                 Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();

                             }
                         }

            @Override
                         public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(AkunSayaActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                         }
                     }
        );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akunsaya);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Akun");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);


        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        setupBottomBar();
        PackageInfo versionInfo = getPackageInfo();
        final ShimmerFrameLayout shimmer = (ShimmerFrameLayout) findViewById(R.id.shimmer);
        shimmer.startShimmer();
        final ShimmerFrameLayout saldoshimmer = (ShimmerFrameLayout) findViewById(R.id.saldoshimmer);
        saldoshimmer.startShimmer();

        sharedPrefManager = new SharedPrefManager(this);
        String api_key = sharedPrefManager.getSPApikey();
        String bearer = sharedPrefManager.getSPBearer();
        String namabt = sharedPrefManager.getSPBtname();
        versiaplikasi.setText("v " + versionInfo.versionName);
        if (btname.equals("null"))
            btname.setText("Belum diatur");
        else
            btname.setText(namabt);
        String api_secret = Enkripsi.api_secret(api_key, sharedPrefManager.getSPUsername());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Userinfo> call = api.userinfo("Bearer " + bearer, randomUUIDString, SHA1, api_key, api_secret);

        call.enqueue(new Callback<Userinfo>() {
            @Override
            public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
                saldoshimmer.stopShimmer();
                saldoshimmer.setVisibility(View.GONE);

                if (response.body() != null && response.body().getStatus() != null) {
                    Integer status = response.body().getStatus();
                    Userinfo data = response.body().getData();
                    Double saldo = data.getSaldo();
                    String nama = data.getNama();
                    String hp = data.getHp();
                    String username = data.getUsername();
                    String email = data.getEmail();
                    String pesan = data.getPesan();
                    Integer kycstatus = data.getIdxKyc();
                    if(data.getProfile_photo()!=null) {
                        Picasso.with(context).load(data.getProfile_photo()).into(profile_photo);
                        profile_photo.setVisibility(View.VISIBLE);
                        linearsingkatan.setVisibility(View.GONE);
                    }
                    else {
                        linearsingkatan.setVisibility(View.VISIBLE);
                        profile_photo.setVisibility(View.GONE);
                    }

                    if (kycstatus.equals(2))
                        tekskyc.setText("Tambah Rekening/E-Wallet");
                    if (status.equals(1)) {
                        editTextemail.setText(email);
                        editTexthp.setText(hp);
                        editTextnama.setText(nama);
                        editTextusername.setText(username);
                        editTextsaldo.setText(Enkripsi.rupiah(saldo));
                        singkatan.setText(nama.substring(0, 1));
                        detail.setVisibility(View.VISIBLE);
                        editTextsaldo.setVisibility(View.VISIBLE);

                    } else {
                        Toast.makeText(AkunSayaActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        detail.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(AkunSayaActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPrefManager = new SharedPrefManager(this);
        String namabt = sharedPrefManager.getSPBtname();
        if (btname.equals("null"))
            btname.setText("Belum diatur");
        else
            btname.setText(namabt);
    }

    private void setupBottomBar() {


        for (int i = 0; i < mBottomBar.getTabCount(); i++) {
            BottomBarTab tab = mBottomBar.getTabAtPosition(i);
            tab.setGravity(Gravity.CENTER);

            View icon = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_icon);
            icon.setPadding(0, icon.getPaddingTop(), 0, icon.getPaddingTop());

            View title = tab.findViewById(com.roughike.bottombar.R.id.bb_bottom_bar_title);
            title.setVisibility(View.VISIBLE);
        }
    }


    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    Intent connectIntent = new Intent(context,DeviceListActivity.class);
                    activityResultLauncher.launch(connectIntent);

                } else {
                    Toast.makeText(context, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }





    private PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = this.getPackageManager().getPackageInfo(
                    this.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }
}
