package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class LupaUsernameActivity extends AppCompatActivity {
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    public void onBackPressed() {
        startActivity(new Intent(context, MasukActivity.class));
    }
    Context context=this;

    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.btnLogin) Button btnLogin;
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
    ProgressDialog progress;



    @OnClick(R.id.btnLupaPassword)
    public void ubah() {
        if (etEmail.getText().toString().length() > 6 ) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
            Sprite doubleBounce = new ThreeBounce();
            doubleBounce.setColor(R.color.bg_colordark);
            progress.setIndeterminateDrawable(doubleBounce);
            progress.show();

            String email=etEmail.getText().toString();
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            String SHA1 = Enkripsi.access_key(randomUUIDString);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Userinfo> call = api.lupausername(email,randomUUIDString,SHA1);
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                    progress.dismiss();
                    if (response.body() != null && response.body().getPesan() != null) {
                        String pesan = response.body().getPesan();
                        Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }

        });
        }
        else {
            if(etEmail.getText().toString().length()<6)
                Toast.makeText(context, "Email tidak lengkap", Toast.LENGTH_SHORT).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_username);
        ButterKnife.bind(this);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MasukActivity.class));

            }
        });

        // Code berikut berfungsi untuk mengecek session, Jika session true ( sudah login )
        // maka langsung memulai MainActivity.

    }


}
