package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DepositActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private RadioButton radioSexButton;
    private Toolbar toolbar;
    Context context=this;
    private ProgressDialog progress;
    @BindView(R.id.editTextjumlah)
    EditText editTextjumlah;
    @BindView(R.id.radioMetode)
    RadioGroup radioGroup;
    @BindView(R.id.radioBCA)
    RadioButton radioBCA;
    @BindView(R.id.radioBNI)
    RadioButton radioBNI;
    @BindView(R.id.radioMandiri)
    RadioButton radioMandiri;
    @BindView(R.id.radioOvo)
    RadioButton radioOvo;
    @BindView(R.id.radioIndodax)
    RadioButton radioIndodax;

    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
    };
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



    @OnClick(R.id.history)
    public void history() {
        Intent i = new Intent(context, HistoryDepoActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.buttonSubmit)
    public void ubah() {
        //membuat progress dialog
        if (editTextjumlah.getText().toString().length() > 3) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
            Sprite doubleBounce = new ThreeBounce();
            doubleBounce.setColor(R.color.bg_colordark);
            progress.setIndeterminateDrawable(doubleBounce);
            progress.show();

            //mengambil data dari edittext
            String jumlah = editTextjumlah.getText().toString();
            //  String vendor = (State) spinner1.getSelectedItem().toString();
            //    String produk = spinner.getSelectedItem().toString();
//        String produk = editTexthp.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();
            // int selectedId2 = radioGroup2.getCheckedRadioButtonId();
            // mencari id radio button
            radioSexButton = (RadioButton) findViewById(selectedId);
            Integer metode=Enkripsi.getMetodeId(radioSexButton.getText().toString());

            //  radioSexButton2 = (RadioButton) findViewById(selectedId2);
            // String produk = radioSexButton2.getText().toString();
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
            Call<Data> call = api.simpandeposit("Bearer "+sharedPrefManager.getSPBearer(),jumlah, metode, randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                    progress.dismiss();
                    if(response.body() != null && response.body().getStatus() != null) {
                        Integer status = response.body().getStatus();
                        String message = response.body().getPesan();

                        String transaction_kode = response.body().getKode();


                        if (status.equals(1)) {
//                        String jumlah = idr(Integer.parseInt(response.body().getJumlah()));
//                        String harga = Double.toString(response.body().getHarga());
                            if (metode.equals(3)) {
                                Intent i = new Intent(context, BayarDepoActivity.class);
                                i.putExtra("transaction_kode", transaction_kode);
//                            i.putExtra("transaction_id", transaction_id);
//                            i.putExtra("harga", harga);
//                            i.putExtra("jumlah", jumlah);
//                            i.putExtra("id_depo", id_depo);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(context, BCADepoActivity.class);
                                i.putExtra("transaction_kode", transaction_kode);
//                            i.putExtra("transaction_id", transaction_id);
//                            i.putExtra("harga", harga);
//                            i.putExtra("jumlah", jumlah);
//                            i.putExtra("metode", metode);
//                            i.putExtra("id_depo", id_depo);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Data> call, Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Jumlah dan metode deposit wajib diisi", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Deposit");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);

        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPMetode().equals("IDK On-Chain"))
            radioIndodax.setChecked(true);
        if (sharedPrefManager.getSPMetode().equals("BCA"))
            radioBCA.setChecked(true);
        if (sharedPrefManager.getSPMetode().equals("BNI"))
            radioBNI.setChecked(true);
        if (sharedPrefManager.getSPMetode().equals("Mandiri"))
            radioMandiri.setChecked(true);
        if (sharedPrefManager.getSPMetode().equals("OVO"))
            radioOvo.setChecked(true);
        if (!sharedPrefManager.getSPMetode().equals("IDK On-Chain") && !sharedPrefManager.getSPMetode().equals("BCA") && !sharedPrefManager.getSPMetode().equals("BNI") && !sharedPrefManager.getSPMetode().equals("Mandiri") && !sharedPrefManager.getSPMetode().equals("OVO"))
            radioBNI.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioSexButton = (RadioButton) findViewById(selectedId);
                String metode = radioSexButton.getText().toString();
                sharedPrefManager.saveSPString(SharedPrefManager.SP_METODE, metode);
            }
        });


    }


    public String idr(int idr) {
        return "Rp "+ NumberFormat.getNumberInstance(Locale.US).format(idr).replace(',', '.');
    }
}
