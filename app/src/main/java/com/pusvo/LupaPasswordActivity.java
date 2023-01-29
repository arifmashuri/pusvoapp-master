package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class LupaPasswordActivity extends AppCompatActivity {
    public void onBackPressed() {
        startActivity(new Intent(context, MasukActivity.class));
    }

    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    @BindView(R.id.etEmail) EditText etEmail;
    @BindView(R.id.etPin) EditText etPin;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.btnLupaPassword) Button btnLupaPassword;
    @BindView(R.id.group16_two_constraint_layout) ConstraintLayout layoutpin;
    Context context=this;
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
    private Toolbar toolbar;
    Context mContext;


    SharedPrefManager sharedPrefManager;
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

            //mengambil data dari edittext
            //  String vendor = (State) spinner1.getSelectedItem().toString();
            //    String produk = spinner.getSelectedItem().toString();
//        String produk = editTexthp.getText().toString();
            // int selectedId2 = radioGroup2.getCheckedRadioButtonId();
            // mencari id radio button
            //  radioSexButton2 = (RadioButton) findViewById(selectedId2);
            // String produk = radioSexButton2.getText().toString();
            String pin=etPin.getText().toString();
            String email=etEmail.getText().toString();
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();
            String SHA1 = Enkripsi.access_key(randomUUIDString);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<Userinfo> call = api.lupapassword(email,pin,randomUUIDString,SHA1);
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                    progress.dismiss();
                    if (response.body() != null && response.body().getStatus() != null) {
                        String pesan = response.body().getPesan();
                        Integer status = response.body().getStatus();
                        if (status.equals(0) || status.equals(1))
                            Toast.makeText(context, pesan, Toast.LENGTH_SHORT).show();
                        if (status.equals(2))
                            layoutpin.setVisibility(View.VISIBLE);
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
            if(etPin.getText().toString().length()!=6)
                Toast.makeText(context, "PIN Google Authenticator minimal 6 karakter", Toast.LENGTH_SHORT).show();
            if(etEmail.getText().toString().length()<6)
                Toast.makeText(context, "Email tidak lengkap", Toast.LENGTH_SHORT).show();
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_password);
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
