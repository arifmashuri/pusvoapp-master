package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

public class TopupActivity extends AppCompatActivity{


    SharedPrefManager sharedPrefManager;
        public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    private ProgressDialog progress;
    public static final int PICK_CONTACT_REQUEST = 1;
    private Toolbar toolbar;
    private boolean manual;
    private boolean matrik;
    int feedefa;
    Integer idkyc;
    Integer id_bank;
    @BindView(R.id.estimasi) TextView estimasi;
    @BindView(R.id.notice) TextView notice;
    @BindView(R.id.tampilkan) TextView tampilkan;
    @BindView(R.id.editTextJumlah) EditText editTextJumlah;
    @BindView(R.id.warning) TextView warning;
    @BindView(R.id.jumlahcek) ImageView jumlahcek;
    @BindView(R.id.gambar) ImageView gambar;
    @BindView(R.id.gambar2) ImageView gambar2;

    List<Produk> listproduk;
    List<Produk> terfilter;
    List<Operator> listoperator;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerProduk)
    RecyclerView recyclerProduk;
//    @BindView(R.id.recyclerOperator)
//    RecyclerView recyclerOperator;
    List<Metode> listmetode;
    Double hargaset;
    Integer metodekirim;
    ItemClickListener itemClickListener;
    KYCClickListener kycClickListener;
    OperatorClickListener operatorClickListener;
    private MetodeAdapter metodeAdapter;
    private KYCAdapter kycAdapter;
    Context context=this;

    @BindView(R.id.buttonSubmit)
    Button buttonSubmit;
    @BindView(R.id.detail) LinearLayout detail;
    @BindView(R.id.layoutmanual) LinearLayout layoutmanual;
    @BindView(R.id.shimmer) ShimmerFrameLayout shimmerfb;
    @OnClick(R.id.gambar)
    public void gambar() {
        if (!manual) {
            manual=true;
            gambar.setImageResource(R.drawable.cek);

        } else {
            manual=false;
            gambar.setImageResource(R.drawable.kotak);
        }
        model1(editTextJumlah.getText().toString());
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
                    || y < w.getTop() || y > w.getBottom()) ) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }
    //@BindView(R.id.radioProduk) RadioGroup radioGroup2;

    @OnClick(R.id.buttonSubmit) void ubah() {
        if (editTextJumlah.getText().toString().length() > 4) {
            //membuat progress dialog
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
            Sprite doubleBounce = new ThreeBounce();
            doubleBounce.setColor(R.color.bg_colordark);
            progress.setIndeterminateDrawable(doubleBounce);
            progress.show();
            notice.setVisibility(View.GONE);

            //mengambil data dari edittextf

            String jumlah = editTextJumlah.getText().toString();
//            Listkyc state = (Listkyc) spinner1.getSelectedItem();
//            Integer id_bank = state.getId();
//
            int manuals;
            if(manual)
                manuals=1;
            else
                manuals=0;




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
            Call<Data> call = api.simpantransaksitopup("Bearer "+sharedPrefManager.getSPBearer(), jumlah,manuals, idkyc, metodekirim,Enkripsi.vapps(context),randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                    progress.dismiss();
                    if (response.body() != null && response.body().getStatus() != null) {
                        Integer status = response.body().getStatus();

                        if (status.equals(1)) {
                            String transaction_kode = response.body().getData().getKode();
//                            Integer metode = response.body().getData().getMetode();

                                Intent i = new Intent(TopupActivity.this, BayarTopupActivity.class);
                                i.putExtra("transaction_kode", transaction_kode);
                                startActivity(i);

                        } else {
                            String message = response.body().getPesan();
                            Toast.makeText(TopupActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        notice.setText(R.string.error500);
                        notice.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    progress.dismiss();
                    Toast.makeText(TopupActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(TopupActivity.this, "Nomor HP/No Meter tidak lengkap", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topupmanual);
        sharedPrefManager = new SharedPrefManager(this);
        ButterKnife.bind(this);
        //spinner1 = (Spinner) findViewById(R.id.spinner1);
        matrik=false;
        feedefa=3500;
        editTextJumlah.addTextChangedListener(inputPasswordWatcher);


        metodekirim = sharedPrefManager.getSPMetodeTopup();
        kycClickListener =new KYCClickListener() {
            @Override
            public void onClick(Integer id,Integer id_bank2,String norek) {
                idkyc=id;
                id_bank=id_bank2;
                metodeAdapter = new MetodeAdapter(context,itemClickListener, listmetode,null,sharedPrefManager.getSPMetodeTopup());
                recyclerView.setAdapter(metodeAdapter);
                model1(editTextJumlah.getText().toString());
                if ((id_bank.equals(41) || id_bank.equals(42) || id_bank.equals(43) || id_bank.equals(44) || id_bank.equals(45) || id_bank.equals(52) || id_bank.equals(54) || id_bank.equals(57) || id_bank.equals(59)) && !matrik) {
                    manual = false;
                    gambar.setImageResource(R.drawable.kotak);
                    gambar.setVisibility(View.VISIBLE);
                    gambar2.setVisibility(View.GONE);
                    tampilkan.setText(R.string.manual);
                    layoutmanual.setVisibility(View.VISIBLE);
                    model1(editTextJumlah.getText().toString());
                } else {
                    manual = false;
                    gambar.setVisibility(View.GONE);
                    gambar2.setVisibility(View.VISIBLE);
                    tampilkan.setText(R.string.manual2);
//                    if(id_bank.equals(41) || id_bank.equals(42) || id_bank.equals(43) || id_bank.equals(44) || id_bank.equals(45) || id_bank.equals(52) || id_bank.equals(54) || id_bank.equals(57) || id_bank.equals(59))
//                        layoutmanual.setVisibility(View.VISIBLE);
//                    else
//                        layoutmanual.setVisibility(View.GONE);

                    layoutmanual.setVisibility(View.GONE);
                    model1(editTextJumlah.getText().toString());
                }

            }
        };

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Integer s) {
                metodekirim=s;
                sharedPrefManager.saveSPInt(SharedPrefManager.SP_METODETOPUP, s);
                model1(editTextJumlah.getText().toString());
            }
        };


        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Withdraw Indodax dan Binance");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);

        shimmerfb.startShimmer();

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
        Call<Data> call = api.kycstatus("Bearer "+sharedPrefManager.getSPBearer(),randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                shimmerfb.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData().getKYCStatus() != null) {
                    Integer status = response.body().getData().getKYCStatus();
                    Servicestatus matriks = response.body().getData().getServicestatus();
                    feedefa = matriks.getFeeWd();
                    if (matriks.getMatrik().equals(1))
                        matrik = true;
                    String message = response.body().getPesan();
                    List<Listkyc> listkyc = response.body().getData().getListkyc();
                    List<Metode> listmetode2 = response.body().getData().getMetodetersedia();
                    listmetode = new ArrayList<>();
                    for (Metode b : listmetode2) {
                        if (b.getWd() == 1) {
                            listmetode.add(b);
                        }
                    }

                    kycAdapter = new KYCAdapter(context, kycClickListener, listkyc, sharedPrefManager.getSPMetodeTopup(), idkyc);
                    if (!status.equals(0)) {
                        recyclerProduk.setAdapter(kycAdapter);
//                    spinner1.setAdapter(kycAdapter);


//                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                            //Populate City list to the second spinner when
//                            // a state is chosen from the first spinner
//                            Listkyc cityDetails = kycAdapter.getItem(position);
//                            String hprek = cityDetails.getNorek();
//                            Integer id_bank = cityDetails.getBankId();
//                            String namarek = cityDetails.getNama();
//                            editTexthp.setText(hprek);
//                            editTextNama.setText(namarek);
//                            if ((id_bank.equals(41) || id_bank.equals(42) || id_bank.equals(43) || id_bank.equals(44) || id_bank.equals(45) || id_bank.equals(52) || id_bank.equals(54) || id_bank.equals(57) || id_bank.equals(59)) && !matrik) {
//                                manual = false;
//                                gambar.setImageResource(R.drawable.kotak);
//                                gambar.setVisibility(View.VISIBLE);
//                                gambar2.setVisibility(View.GONE);
//                                tampilkan.setText(R.string.manual);
//                                layoutmanual.setVisibility(View.VISIBLE);
//                                model1(editTexthp.getText().toString(), editTextJumlah.getText().toString());
//                            } else {
//                                manual = false;
//                                gambar.setVisibility(View.GONE);
//                                gambar2.setVisibility(View.VISIBLE);
//                                tampilkan.setText(R.string.manual2);
//                                if(id_bank.equals(41) || id_bank.equals(42) || id_bank.equals(43) || id_bank.equals(44) || id_bank.equals(45) || id_bank.equals(52) || id_bank.equals(54) || id_bank.equals(57) || id_bank.equals(59))
//                                layoutmanual.setVisibility(View.VISIBLE);
//                                else
//                                    layoutmanual.setVisibility(View.GONE);
//                                model1(editTexthp.getText().toString(), editTextJumlah.getText().toString());
//                            }
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
                    }
                    detail.setVisibility(View.VISIBLE);


                    if (status.equals(0)) {
                        warning.setVisibility(View.VISIBLE);
                        warning.setText(message);
                        editTextJumlah.setVisibility(View.GONE);
                        startActivity(new Intent(TopupActivity.this, KYCActivity.class));
                        finish();
                    }
                }
                else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }
            }
            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(TopupActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

            }
        });


    }







    // get the selected dropdown list value

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public final TextWatcher inputPasswordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            //method ini  wajib ada meskipun disini kosong valuenya
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // textView.setVisibility(View.VISIBLE);
            //menset textview dalam mode visible terlihat

        }



        @Override
        public void afterTextChanged(Editable s) {


            model1(s.toString());

        }
    };


    public int hargafix(int harga) {
   int hargah = harga*1002/1000;

String harganya="";
        //String harganya=Float.toString((float) (int) hargah);
        String correctstring[] = Integer.toString(hargah).split(".");
        if(Integer.toString(hargah).contains("."))
           harganya = correctstring[0];
        else
            harganya=Integer.toString(hargah);
int hargalength=harganya.length();
              int sub1= Integer.parseInt(harganya.substring(hargalength-2,hargalength));
        int sub2= Integer.parseInt(harganya.substring(0,hargalength-2));
        String baku=sub2+"00";
        int baku2=Integer.parseInt(baku);

        if(sub1>=51) {
            return baku2+100;
        }
        else {
            return baku2;
        }

    }
    public String idr(int idr) {
       return "Rp "+NumberFormat.getNumberInstance(Locale.US).format(idr).replace(',', '.');
    }


    public void model1(String jumlah) {
        if (jumlah.length() > 1) {

            Pattern mPattern2 = Pattern.compile("^[0-9]{5,7}$");
            Matcher matcher2 = mPattern2.matcher(jumlah);

            boolean jumlahbenar = matcher2.find() && Integer.parseInt(jumlah) >= 25000 && Integer.parseInt(jumlah) <= 1000000;

            if (jumlahbenar) {
                jumlahcek.setImageResource(R.drawable.centang);
                jumlahcek.setVisibility(View.VISIBLE);
            } else {
                jumlahcek.setImageResource(R.drawable.salah);
                jumlahcek.setVisibility(View.VISIBLE);
            }

            if(idkyc!=null && metodekirim!=null && jumlahbenar)
                buttonSubmit.setVisibility(View.VISIBLE);
            else
                buttonSubmit.setVisibility(View.GONE);


        }


        if (jumlah.length()>= 5 && Integer.parseInt(jumlah)>=25000){
            int jumlah2=Integer.parseInt(jumlah);
            //Listkyc state = (Listkyc) spinner1.getSelectedItem();
            //Integer id_bank = state.getBankId();
            int fee=feedefa;

            if(manual) {
                if (id_bank.equals(42) || id_bank.equals(45) || id_bank.equals(52))
                    fee = 1000;
                if (id_bank.equals(41) || id_bank.equals(43) || id_bank.equals(44) || id_bank.equals(54) || id_bank.equals(57) || id_bank.equals(59))
                    fee = 0;
            }

            int setelahfee=0;
            if(jumlah2<=1000000)
                setelahfee=jumlah2+700+fee;
            if(jumlah2<=900000)
                setelahfee=jumlah2+800+fee;
            if(jumlah2<=800000)
                setelahfee=jumlah2+900+fee;
            if(jumlah2<=700000)
                setelahfee=jumlah2+1000+fee;
            if(jumlah2<=600000)
                setelahfee=jumlah2+1100+fee;
            if(jumlah2<=500000)
                setelahfee=jumlah2+1200+fee;
            if(jumlah2<=400000)
                setelahfee=jumlah2+1400+fee;
            if(jumlah2<=300000)
                setelahfee=jumlah2+1600+fee;
            if(jumlah2<=200000)
                setelahfee=jumlah2+1800+fee;
            if(jumlah2<=100000)
                setelahfee=jumlah2+2000+fee;
            if(jumlah2<=50000)
                setelahfee=jumlah2+2200+fee;

            if(jumlah2>1000000) {
                estimasi.setText("Melebihi limit");
            }
            else
                estimasi.setText(idr(hargafix(setelahfee)));
            //    estimasi.setText(idr(hargafix(setelahfee))+" - "+idr(hargafix(setelahfee)+1000));

//}

            //   textView.setText("Password kalian : "+editText2.getText());
            //atau jika  tidak maka akan terlihat dan menampilkan text password kalian
            //berdasarkan inputan pada object ediText 2 di bagian password

        }

        else {
            estimasi.setText("Minimal Rp 25.000");
        }



    }


}
