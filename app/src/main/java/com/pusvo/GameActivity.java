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
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.lang.String;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
    };


    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    public static final int PICK_CONTACT_REQUEST = 1;
    private Toolbar toolbar;
    private ProgressDialog progress;
    Integer idproduk;
    @BindView(R.id.NamaKontak) TextView NamaKontak;
    @BindView(R.id.notice) TextView notice;
    @BindView(R.id.LinearNamaKontak) LinearLayout LinearNamaKontak;
    @BindView(R.id.LinearOperator) LinearLayout LinearOperator;
    @BindView(R.id.LinearProduk) LinearLayout LinearProduk;
    @BindView(R.id.editTexthp) EditText editTexthp;
    @BindView(R.id.sk) TextView sk;
    @BindView(R.id.LinearMeter) LinearLayout LinearMeter;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.hpcek) ImageView hpcek;
    @BindView(R.id.deskripsiproduk) TextView deskripsiproduk;
    @BindView(R.id.LinearHarga) LinearLayout LinearHarga;
    @BindView(R.id.LinearDeskripsi) LinearLayout LinearDeskripsi;
    @BindView(R.id.pengumuman) TextView pengumuman;

    List<Produk> listproduk;
    List<Produk> terfilter;
    List<Operator> listoperator;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerProduk)
    RecyclerView recyclerProduk;
    @BindView(R.id.recyclerOperator)
    RecyclerView recyclerOperator;
    @BindView(R.id.loadingbaru) LinearLayout loadingbaru;
    @BindView(R.id.isihalaman)
    NestedScrollView isihalaman;
    List<Metode> listmetode;
    Double hargaset;
    Integer metodekirim;
    ItemClickListener itemClickListener;
    ProdukClickListener produkClickListener;
    OperatorClickListener operatorClickListener;
    private MetodeAdapter metodeAdapter;
    private ProdukAdapter produkAdapter;
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
        //membuat progress dialog


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
            Call<Data> call = api.simpantransaksi( "Bearer "+sharedPrefManager.getSPBearer(),hp,"", idproduk, metodekirim,Enkripsi.vapps(context), randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                    progress.dismiss();

                    if (response.body() != null && response.body().getStatus() != null) {
                        Integer status = response.body().getStatus();
                        if (status.equals(1)) {
                            Data data = response.body().getData();
                            Intent i = new Intent(context, BayarActivity.class);
                            i.putExtra("transaction_kode", data.getKode());
                            startActivity(i);

                        } else {
                            String message = response.body().getPesan();
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Nomor HP tidak lengkap", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
        ButterKnife.bind(this);
        sharedPrefManager = new SharedPrefManager(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Beli Voucher Game");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
       LinearMeter.setVisibility(View.GONE);
        editTexthp.addTextChangedListener(HPWatcher);


        loadingbaru.setVisibility(View.VISIBLE);
        isihalaman.setVisibility(View.GONE);






        metodekirim = sharedPrefManager.getSPMetodeBaru();
        produkClickListener =new ProdukClickListener() {
            @Override
            public void onClick(Integer id,Double harga,String deskripsi) {
                idproduk=id;
                hargaset=harga;

                model1(editTexthp.getText().toString());
                metodeAdapter = new MetodeAdapter(context,itemClickListener, listmetode,hargaset,sharedPrefManager.getSPMetodeBaru());
                recyclerView.setAdapter(metodeAdapter);
                deskripsiproduk.setText(deskripsi);

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

                produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,sharedPrefManager.getSPMetodeBaru(),idproduk);
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
                sharedPrefManager.saveSPInt(SharedPrefManager.SP_METODEBARU, s);
                model1(editTexthp.getText().toString());
                produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,s,idproduk);
                recyclerProduk.setAdapter(produkAdapter);
            }
        };

        sk.setText(R.string.skvouchergame);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.dataproduk(3);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                loadingbaru.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData() != null) {
                    listoperator = response.body().getData().getOperator();
                    listproduk = response.body().getData().getProduk();
                    listmetode = response.body().getData().getMetodetersedia();

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
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                loadingbaru.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

            }
        });
    }




    public final TextWatcher HPWatcher = new TextWatcher() {
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
