package com.pusvo;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Keamanan extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private ProgressDialog progress;
    private Toolbar toolbar;
    Context context=this;
    @BindView(R.id.editPassword) TextView editPassword;
    @BindView(R.id.editPasswordBaru) TextView editPasswordBaru;
    @BindView(R.id.editPasswordBaru2) TextView editPasswordBaru2;
    @BindView(R.id.tampilkanpw)  TextView showpw;
    @BindView(R.id.gambar) ImageView gambar;
    @BindView(R.id.passwordcek) ImageView passwordcek;
    @BindView(R.id.passwordbarucek) ImageView passwordbarucek;
    @BindView(R.id.passwordbaru2cek) ImageView passwordbaru2cek;

    public void onBackPressed() {
        startActivity(new Intent(Keamanan.this, AkunSayaActivity.class));
    };

    @OnClick(R.id.tampilkan)
    public void tampilkanpw() {
        if (showpw.getText().toString().equals("1")) {
            editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPasswordBaru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPasswordBaru2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showpw.setText("0");
            gambar.setImageResource(R.drawable.cek);

        } else {
            editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPasswordBaru.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPasswordBaru2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showpw.setText("1");
            gambar.setImageResource(R.drawable.kotak);
        }
    }

    @OnClick(R.id.gambar)
    public void gambar() {
        if (showpw.getText().toString().equals("1")) {
            editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPasswordBaru.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            editPasswordBaru2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            showpw.setText("0");
            gambar.setImageResource(R.drawable.cek);

        } else {
            editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPasswordBaru.setTransformationMethod(PasswordTransformationMethod.getInstance());
            editPasswordBaru2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            showpw.setText("1");
            gambar.setImageResource(R.drawable.kotak);
        }
    }

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
                    || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }





    @OnClick(R.id.buttonSubmit)
    public void ubah() {


        // int selectedId2 = radioGroup2.getCheckedRadioButtonId();
        // mencari id radio button
        //membuat progress dialog
        if (editPassword.getText().toString().length() > 6 && editPasswordBaru.getText().toString().length() > 6 && editPasswordBaru.getText().toString().equals(editPasswordBaru2.getText().toString())) {
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
            String password=editPassword.getText().toString();
            String passwordbaru=editPasswordBaru.getText().toString();
            String passwordbaru2=editPasswordBaru2.getText().toString();

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
            Call<Userinfo> call = api.gantipassword("Bearer "+sharedPrefManager.getSPBearer(),password,passwordbaru,passwordbaru2,randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Userinfo>() {
                @Override
                public void onResponse(@NonNull Call<Userinfo> call, @NonNull Response<Userinfo> response) {
                    progress.dismiss();

                    if (response.body() != null && response.body().getPesan() != null) {
                        String message = response.body().getPesan();

                        Toast.makeText(Keamanan.this, message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<Userinfo> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(Keamanan.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            if(!editPasswordBaru.getText().toString().equals(editPasswordBaru2.getText().toString()))
                Toast.makeText(Keamanan.this, "Password baru tidak cocok dengan konfirmasi password baru", Toast.LENGTH_SHORT).show();
            if(editPasswordBaru.getText().toString().length()<7)
                Toast.makeText(Keamanan.this, "Password minimal 7 karakter", Toast.LENGTH_SHORT).show();
            if(editPassword.getText().toString().length()<7)
                Toast.makeText(Keamanan.this, "Password minimal 7 karakter", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keamanan);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Keamanan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        editPassword.addTextChangedListener(PasswordWatcher);
        editPasswordBaru.addTextChangedListener(PasswordBaruWatcher);
        editPasswordBaru2.addTextChangedListener(PasswordBaru2Watcher);
    }

    public final TextWatcher PasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            //Pattern mPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$");
            if(s.length()>=7)
            {
                passwordcek.setImageResource(R.drawable.centang);
                passwordcek.setVisibility(View.VISIBLE);
            }
            else {
                passwordcek.setImageResource(R.drawable.salah);
                passwordcek.setVisibility(View.VISIBLE);
            }


        }
    };

    public final TextWatcher PasswordBaruWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            //Pattern mPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$");
            Pattern mPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,32}$");
            Matcher matcher = mPattern.matcher(s.toString());
            if(matcher.find())
            {
                passwordbarucek.setImageResource(R.drawable.centang);
                passwordbarucek.setVisibility(View.VISIBLE);
                if(s.toString().equals(editPasswordBaru2.getText().toString()))
                {
                    passwordbaru2cek.setImageResource(R.drawable.centang);
                    passwordbaru2cek.setVisibility(View.VISIBLE);
                }
                else {
                    if (editPasswordBaru2.getText().toString().length() >= 1) {
                        passwordbaru2cek.setImageResource(R.drawable.salah);
                        passwordbaru2cek.setVisibility(View.VISIBLE);
                    }
                }
            }
            else {
                passwordbarucek.setImageResource(R.drawable.salah);
                passwordbarucek.setVisibility(View.VISIBLE);
                if (editPasswordBaru2.getText().toString().length() >= 1) {
                    passwordbaru2cek.setImageResource(R.drawable.salah);
                    passwordbaru2cek.setVisibility(View.VISIBLE);
                }
            }


        }
    };



    public final TextWatcher PasswordBaru2Watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().equals(editPasswordBaru.getText().toString()) && s.length()>=8)
            {
                passwordbaru2cek.setImageResource(R.drawable.centang);
                passwordbaru2cek.setVisibility(View.VISIBLE);
            }
            else {
                passwordbaru2cek.setImageResource(R.drawable.salah);
                passwordbaru2cek.setVisibility(View.VISIBLE);
            }
        }
    };


}
