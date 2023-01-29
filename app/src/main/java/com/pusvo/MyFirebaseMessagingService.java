package com.pusvo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.installations.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";





    @Override
    public void onMessageReceived(RemoteMessage message) {

        String click_action = message.getNotification().getClickAction();
           Intent intent = new Intent(click_action);
        sharedPrefManager = new SharedPrefManager(this);
        //On click of notification it redirect to this Activity
        if (sharedPrefManager.getSPSudahLogin()){





           // Intent intent = new Intent(this, LayarUtama.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           // startActivity(intent);
            PendingIntent pendingIntent;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getActivity(this,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            }else {
                pendingIntent = PendingIntent.getActivity(this,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            }
          //  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


//            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher))
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(judul)
//                    .setContentText(message2)
//                    .setAutoCancel(true)
//                    .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://" +this.getPackageName()+"/"+R.raw.pusvo))
//                    .setContentIntent(pendingIntent);

//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManager.notify(0, notificationBuilder.build());

            String channelId =message.getNotification().getChannelId();
            if(channelId==null)
                channelId="1";
            Uri soundUri=null;
            if(channelId.equals("1"))
                soundUri =  Uri.parse("android.resource://com.pusvo/raw/pusvo");
            if(channelId.equals("2"))
                soundUri =  Uri.parse("android.resource://com.pusvo/raw/pusvo2");
            if(channelId.equals("3") || channelId.equals("4"))
                soundUri =  Uri.parse("android.resource://com.pusvo/raw/zpusvo");
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notifikasi)
                    .setSound(soundUri)
                    .setColor(ContextCompat.getColor(this, R.color.background))
                    .setContentTitle(message.getNotification().getTitle())
                    .setContentText(message.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(1, builder.build());
        }

    }


    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            String api_key = sharedPrefManager.getSPApikey();
            //For registration of token
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Userinfo> call = api.kirimtoken(api_key, token+"");
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(Call<Userinfo> call, retrofit2.Response<Userinfo> response) {

                }

                @Override
                public void onFailure(Call<Userinfo> call, Throwable t) {
                    t.printStackTrace();
                    //progress.dismiss();
                    //Toast.makeText(BelanjaActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
                // Log and toast
            });
            //To displaying token on logcat
            // Log.d("TOKEN: ", token);

        }
        else {

            //For registration of token
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Userinfo> call = api.kirimtoken("hurihuri", "toloken");
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(Call<Userinfo> call, retrofit2.Response<Userinfo> response) {

                }

                @Override
                public void onFailure(Call<Userinfo> call, Throwable t) {
                    t.printStackTrace();
                    //progress.dismiss();
                    //Toast.makeText(BelanjaActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
                // Log and toast
            });
            //To displaying token on logcat
            // Log.d("TOKEN: ", token);

        }

    }
}