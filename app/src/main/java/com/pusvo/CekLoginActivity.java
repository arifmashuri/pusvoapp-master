package com.pusvo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CekLoginActivity extends AppCompatActivity {
    @BindView(R.id.versiaplikasi)
    TextView versiaplikasi;
    Context mContext;

    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    SharedPrefManager sharedPrefManager;
    private AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


    //    appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());


//        installStateUpdatedListener = state -> {
//            if (state.installStatus() == InstallStatus.DOWNLOADED) {
//                popupSnackBarForCompleteUpdate();
//            } else if (state.installStatus() == InstallStatus.INSTALLED) {
//                removeInstallStateUpdateListener();
//            } else {
//                Toast.makeText(getApplicationContext(), "InstallStateUpdatedListener: state: " + state.installStatus(), Toast.LENGTH_LONG).show();
//            }
//        };


            PackageInfo versionInfo = getPackageInfo();
        mContext = this;
        sharedPrefManager = new SharedPrefManager(this);
        versiaplikasi.setText("v "+versionInfo.versionName);

        // Code berikut berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.
logindata();
    }

    @Override
    public void onResume() {
        super.onResume();
        logindata();
    }


    private void logindata() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        sharedPrefManager = new SharedPrefManager(this);
        String api_key = sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);
        Call<Userinfo> call = api.ceklogin(Enkripsi.vapps(mContext),randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Userinfo>() {
            @Override
            public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                if(response.body()!=null && response.body().getStatus()!=null) {
                    Integer status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status.equals(1)) {
                        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationChannel mChannel;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mChannel = new NotificationChannel("1", "Pemberitahuan", NotificationManager.IMPORTANCE_HIGH);
                            mChannel.setLightColor(Color.GRAY);
                            mChannel.enableLights(true);
                            mChannel.setDescription("Pemberitahuan terkait transaksi");
                            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .build();
                            Uri soundUri =  Uri.parse("android.resource://com.pusvo/raw/pusvo");
                            mChannel.setSound(soundUri, audioAttributes);

                            if (mNotificationManager != null) {
                                mNotificationManager.createNotificationChannel( mChannel );
                            }
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mChannel = new NotificationChannel("2", "Pemberitahuan 2", NotificationManager.IMPORTANCE_HIGH);
                            mChannel.setLightColor(Color.GRAY);
                            mChannel.enableLights(true);
                            mChannel.setDescription("Pemberitahuan terkait transaksi");
                            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .build();
                            Uri soundUri =  Uri.parse("android.resource://com.pusvo/raw/pusvo2");
                            mChannel.setSound(soundUri, audioAttributes);

                            if (mNotificationManager != null) {
                                mNotificationManager.createNotificationChannel( mChannel );
                            }
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mChannel = new NotificationChannel("3", "Pengumuman", NotificationManager.IMPORTANCE_HIGH);
                            mChannel.setLightColor(Color.GRAY);
                            mChannel.enableLights(true);
                            mChannel.setDescription("Pengumuman terkait layanan");
                            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .build();
                            Uri soundUri =  Uri.parse("android.resource://com.pusvo/raw/zpusvo");
                            mChannel.setSound(soundUri, audioAttributes);

                            if (mNotificationManager != null) {
                                mNotificationManager.createNotificationChannel( mChannel );
                            }
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            mChannel = new NotificationChannel("4", "Promosi", NotificationManager.IMPORTANCE_HIGH);
                            mChannel.setLightColor(Color.GRAY);
                            mChannel.enableLights(true);
                            mChannel.setDescription("Pengumuman terkait layanan");
                            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                                    .build();
                            Uri soundUri =  Uri.parse("android.resource://com.pusvo/raw/zpusvo");
                            mChannel.setSound(soundUri, audioAttributes);

                            if (mNotificationManager != null) {
                                mNotificationManager.createNotificationChannel( mChannel );
                            }
                        }




                        FirebaseMessaging.getInstance().getToken()
                                .addOnCompleteListener(new OnCompleteListener<String>() {
                                    @Override
                                    public void onComplete(@NonNull Task<String> task) {
                                        if (!task.isSuccessful()) {
                                            return;
                                        }

                                        // Get new Instance ID token
                                        String token = task.getResult();
//                                                    String refreshedToken = FirebaseInstanceId.getInstance().getId();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(URL)
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        API api = retrofit.create(API.class);
                                        Call<Userinfo> call = api.kirimtoken(api_key, token);
                                        call.enqueue(new Callback<Userinfo>() {
                                                         @Override
                                                         public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {

                                                         }

                                                         @Override
                                                         public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                                                             t.printStackTrace();
                                                             //progress.dismiss();

                                                         }
                                                         // Log and toast
                                                     }
                                        );
                                        //                                              String msg = getString(R.string.msg_token_fmt, token);
//                                                    Log.d(TAG, msg);
                                        //Toast.makeText(MasukActivity.this, token, Toast.LENGTH_SHORT).show();
                                    }
                                });


                        startActivity(new Intent(CekLoginActivity.this, MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }
                    if (status.equals(2)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.pusvo")));
                        finish();
                    }
                    if (status.equals(3)) {
                        Intent i = new Intent(CekLoginActivity.this, MainActivity.class);
                        i.putExtra("updateapk", "3");
                        i.putExtra("pesan", pesan);
                        startActivity(i);

                    }


                    if (!status.equals(1) && !status.equals(2) && !status.equals(3)) {
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(CekLoginActivity.this, MasukActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();

                    }
                }
                else {
                    Toast.makeText(CekLoginActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Userinfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CekLoginActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
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