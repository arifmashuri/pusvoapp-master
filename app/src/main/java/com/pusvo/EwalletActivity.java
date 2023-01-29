package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;
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


public class EwalletActivity extends AppCompatActivity {
    public void onBackPressed() {
        startActivity(new Intent(EwalletActivity.this, MainActivity.class));
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    public static final int PICK_CONTACT_REQUEST = 1;

    Integer idproduk;
    private Toolbar toolbar;
    SharedPrefManager sharedPrefManager;
    private ProgressDialog progress;
    @BindView(R.id.NamaKontak) TextView NamaKontak;
    @BindView(R.id.LinearNamaKontak) LinearLayout LinearNamaKontak;
    @BindView(R.id.LinearOperator) LinearLayout LinearOperator;
    @BindView(R.id.LinearProduk) LinearLayout LinearProduk;
    @BindView(R.id.deskripsiproduk) TextView deskripsiproduk;
    @BindView(R.id.judul_nohp) TextView judul_nohp;

    @BindView(R.id.LinearMeter) LinearLayout LinearMeter;
    @BindView(R.id.editTexthp) EditText editTexthp;
    @BindView(R.id.sk) TextView sk;

    @BindView(R.id.notice) TextView notice;

    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.hpcek) ImageView hpcek;
    @BindView(R.id.LinearHarga) LinearLayout LinearHarga;
    @BindView(R.id.LinearDeskripsi) LinearLayout LinearDeskripsi;
    List<Produk> listproduk;
    List<Produk> terfilter;
    List<Operator> listoperator;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerProduk)
    RecyclerView recyclerProduk;
    @BindView(R.id.recyclerOperator)
    RecyclerView recyclerOperator;
    @BindView(R.id.recyclerKyc)
    RecyclerView recyclerKyc;
    List<Metode> listmetode;
    List<Listkyc> listkyc;
    @BindView(R.id.loadingbaru) LinearLayout loadingbaru;
    @BindView(R.id.isihalaman)
    NestedScrollView isihalaman;

    Double hargaset;
    Integer metodekirim;
    ItemClickListener itemClickListener;
    ProdukClickListener produkClickListener;
    KYCClickListener kycClickListener;
    OperatorClickListener operatorClickListener;
    private MetodeAdapter metodeAdapter;
    private ProdukAdapter produkAdapter;
    private KYCAdapter kycAdapter;
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

    @OnClick(R.id.buttonSubmit2) void ubadsh() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    @OnClick(R.id.buttonSubmit) public void ubah() {
         if (editTexthp.getText().toString().length() > 7 && idproduk!=null) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
             Sprite doubleBounce = new ThreeBounce();
             doubleBounce.setColor(R.color.bg_colordark);
             progress.setIndeterminateDrawable(doubleBounce);
            progress.show();

             notice.setVisibility(View.GONE);
            String hp = editTexthp.getText().toString();


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
             Call<Data> call = api.simpantransaksi( "Bearer "+sharedPrefManager.getSPBearer(),hp,"", idproduk, metodekirim, Enkripsi.vapps(context),randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                    progress.dismiss();
                    if(response.body()!=null && response.body().getStatus()!=null) {
                        Integer status = response.body().getStatus();


                        if (status.equals(1)) {
                            Data data = response.body().getData();

                            Intent i = new Intent(EwalletActivity.this, BayarActivity.class);
                            i.putExtra("transaction_kode", data.getKode());
                            startActivity(i);

                        } else {
                            //Toast.makeText(EwalletActivity.this, message, Toast.LENGTH_SHORT).show();
                            notice.setText(response.body().getPesan());
                            notice.setVisibility(View.VISIBLE);
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
                    Toast.makeText(EwalletActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(EwalletActivity.this, "Nomor HP tidak lengkap", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
                String[] projection2 = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                Cursor cursor2 = getContentResolver()
                        .query(contactUri, projection2, null, null, null);
                cursor2.moveToFirst();

                int column2 = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String nama = cursor2.getString(column2);
                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String numbers = cursor.getString(column);
                // Do something with the phone number...
                String number = numbers.replace("+62", "0");
                editTexthp.setText(number.replaceAll("[^0-9]", ""));
                NamaKontak.setText(nama);
                LinearNamaKontak.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(),nama,Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pembelian2_qris);
        sharedPrefManager = new SharedPrefManager(this);
        ButterKnife.bind(this);
        LinearMeter.setVisibility(View.GONE);
        judul_nohp.setText("Nomor E-Wallet");
        editTexthp.setText("");
        //displayLoader();
        //loadStateCityDetails();

        editTexthp.addTextChangedListener(HPWatcher);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Top Up E-Wallet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);


        loadingbaru.setVisibility(View.VISIBLE);
        isihalaman.setVisibility(View.GONE);





        UUID uuid2 = UUID.randomUUID();
        String randomUUIDString2 = uuid2.toString();
        String SHA12 = Enkripsi.access_key(randomUUIDString2);
        String api_key = sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api2 = retrofit2.create(API.class);
        Call<Data> call2 = api2.kycstatus("Bearer "+sharedPrefManager.getSPBearer(),randomUUIDString2, SHA12, api_key, api_secret);
        call2.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                loadingbaru.setVisibility(View.GONE);

                if (response.body() != null && response.body().getData().getKYCStatus() != null) {
                    Integer status = response.body().getData().getKYCStatus();
                    List<Listkyc> listkyc2 = response.body().getData().getListkyc();
                    listkyc = new ArrayList<>();
                    for (Listkyc b : listkyc2) {
                        if (b.getBankdetail().getEwallet() == 1) {
                            listkyc.add(b);
                        }
                    }

                    kycAdapter = new KYCAdapter(context, kycClickListener, listkyc, sharedPrefManager.getSPMetodeTopup(), null);
                    if (!status.equals(0)) {
                        recyclerKyc.setAdapter(kycAdapter);
                    }
                    isihalaman.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }
            }


        @Override
        public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
            t.printStackTrace();
            Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            loadingbaru.setVisibility(View.GONE);
        }
    });



        loadingbaru.setVisibility(View.VISIBLE);
        isihalaman.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.dataproduk(9);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                loadingbaru.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData() != null) {
                    listoperator = response.body().getData().getOperator();
                    listproduk = response.body().getData().getProduk();
                    List<Metode> listmetode2 = response.body().getData().getMetodetersedia();
                    listmetode = new ArrayList<>();
                    for (Metode b : listmetode2) {
                        if (b.getWd() == 1) {
                            listmetode.add(b);
                        }
                    }
                    OperatorAdapter operatorAdapter = new OperatorAdapter(context, operatorClickListener, listoperator);
                    recyclerOperator.setAdapter(operatorAdapter);
                    LinearOperator.setVisibility(View.VISIBLE);
                    isihalaman.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                loadingbaru.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(EwalletActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

            }
        });


        metodekirim = sharedPrefManager.getSPMetodeTopup();
        produkClickListener =new ProdukClickListener() {
            @Override
            public void onClick(Integer id,Double harga,String deskripsi) {
                idproduk=id;
                hargaset=harga;

                model1(editTexthp.getText().toString());
                metodeAdapter = new MetodeAdapter(context,itemClickListener, listmetode,hargaset,sharedPrefManager.getSPMetodeTopup());
                recyclerView.setAdapter(metodeAdapter);
                deskripsiproduk.setText(deskripsi);

            }
        };



        kycClickListener =new KYCClickListener() {
            @Override
            public void onClick(Integer id,Integer id_bank2, String nohp) {
                editTexthp.setText(nohp);
                terfilter = new ArrayList<>();
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(id_bank2)) {
                        terfilter.add(b);
                    }
                }
                produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,sharedPrefManager.getSPMetodeTopup(),idproduk);
                recyclerProduk.setAdapter(produkAdapter);
                LinearOperator.setVisibility(View.GONE);
                LinearProduk.setVisibility(View.VISIBLE);
                idproduk=null;
                model1(editTexthp.getText().toString());

            }
        };



        operatorClickListener =new OperatorClickListener() {
            @Override
            public void onClick(Integer id) {
                terfilter = new ArrayList<>();
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(id)) {
                        terfilter.add(b);
                    }
                }
                produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,sharedPrefManager.getSPMetodeTopup(),idproduk);
                recyclerProduk.setAdapter(produkAdapter);
                LinearProduk.setVisibility(View.VISIBLE);
                idproduk=null;
                model1(editTexthp.getText().toString());
            }
        };



        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Integer s) {
                metodekirim=s;
                sharedPrefManager.saveSPInt(SharedPrefManager.SP_METODETOPUP, s);
                model1(editTexthp.getText().toString());
                produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,s,idproduk);
                recyclerProduk.setAdapter(produkAdapter);
            }
        };


        sk.setText(R.string.skojol);





    }




    public final TextWatcher HPWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }


        @Override
        public void afterTextChanged(Editable s) {
            model1(s.toString());
            LinearNamaKontak.setVisibility(View.GONE);
        }
    };


    public void model1(String hp) {
        boolean produkbenar = idproduk != null;

        if (produkbenar) {
            LinearDeskripsi.setVisibility(View.VISIBLE);
            LinearHarga.setVisibility(View.VISIBLE);
        } else {

            LinearDeskripsi.setVisibility(View.GONE);
            LinearHarga.setVisibility(View.GONE);

        }


        if (hp.length() > 4) {
            boolean hpbenar = Enkripsi.validator(8,14,"nomorhp",hp);


            if (hpbenar) {
                hpcek.setImageResource(R.drawable.centang);
                hpcek.setVisibility(View.VISIBLE);
            } else {
                hpcek.setImageResource(R.drawable.salah);
                hpcek.setVisibility(View.VISIBLE);
            }


            if(hpbenar && produkbenar)
                buttonSubmit.setVisibility(View.VISIBLE);
            else
                buttonSubmit.setVisibility(View.GONE);



        }
        else {

            hpcek.setImageResource(R.drawable.salah);
            hpcek.setVisibility(View.VISIBLE);
            buttonSubmit.setVisibility(View.GONE);

        }
    }

}
