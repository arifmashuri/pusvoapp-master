package com.pusvo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class PulsaActivity extends AppCompatActivity {
    public void onBackPressed() {
        startActivity(new Intent(context, MainActivity.class));
    };
Context context=this;
    SharedPrefManager sharedPrefManager;
    private ProdukAdapter produkAdapter;
    ItemClickListener itemClickListener;
    ProdukClickListener produkClickListener;
    public static final int PICK_CONTACT_REQUEST = 1;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private MetodeAdapter metodeAdapter;
    private Toolbar toolbar;
    private ProgressDialog progress;
    Double hargaset;
    Integer idproduk;
    Integer metodekirim;
    List<Produk> listproduk;
    List<Produk> terfilter;
    List<Metode> listmetode;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerProduk)
    RecyclerView recyclerProduk;

    @BindView(R.id.editTexthp) EditText editTexthp;
    @BindView(R.id.operator) TextView operator;
    @BindView(R.id.operatorimg) ImageView operatorimg;
    @BindView(R.id.NamaKontak) TextView NamaKontak;
    @BindView(R.id.LinearNamaKontak) LinearLayout LinearNamaKontak;
    @BindView(R.id.LinearOperator) LinearLayout LinearOperator;
    @BindView(R.id.LinearProduk) LinearLayout LinearProduk;
    @BindView(R.id.LinearDeskripsi) LinearLayout LinearDeskripsi;
    @BindView(R.id.sk) TextView sk;
     @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.hpcek) ImageView hpcek;
    @BindView(R.id.produkcek) ImageView produkcek;
    @BindView(R.id.deskripsiproduk) TextView deskripsiproduk;
    @BindView(R.id.LinearHarga) LinearLayout LinearHarga;
    @BindView(R.id.pengumuman) TextView pengumuman;
    @BindView(R.id.notice) TextView notice;
    @BindView(R.id.loadingbaru) LinearLayout loadingbaru;
    @BindView(R.id.isihalaman) NestedScrollView isihalaman;


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
        pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }
    private final static String HEX = "0123456789abcdef";

    private static void appendHex(final StringBuffer sb, final byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f))
                .append(HEX.charAt(b & 0x0f));
    }
    @OnClick(R.id.buttonSubmit) void ubah() {

        if (editTexthp.getText().toString().length()>7 && !operator.getText().toString().isEmpty() && operator!=null && idproduk!=null) {
            notice.setVisibility(View.GONE);
            String hp = editTexthp.getText().toString();
            progress = new ProgressDialog(this);
            Sprite doubleBounce = new ThreeBounce();

            doubleBounce.setColor(R.color.bg_colordark);

            progress.setIndeterminateDrawable(doubleBounce);

            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
            progress.show();

            //TsODO
           // Toast.makeText(context, idproduk+"tes"+metodekirim, Toast.LENGTH_SHORT).show();
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

                    if(response.body()!=null && response.body().getStatus()!=null) {
                        if (response.body().getStatus().equals(1)) {
                            Data data = response.body().getData();
                            Intent i = new Intent(context, BayarActivity.class);
                            i.putExtra("transaction_kode", data.getKode());
                            startActivity(i);


                        }
                        if (response.body().getStatus().equals(0)) {
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
        }

        else {
            Toast.makeText(context, "Nomor HP tidak lengkap", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_pembelian1_qris);
        sharedPrefManager = new SharedPrefManager(this);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        editTexthp.setText("");
        editTexthp.addTextChangedListener(HPWatcher);






        Intent intent = getIntent();
        String jenisorder = intent.getStringExtra("jenisorder");
        Integer kodedata = 5;
        if(jenisorder.equals("pulsa")) {
            kodedata = 5;
            getSupportActionBar().setTitle("Beli Pulsa");
            sk.setText(R.string.skpulsa);
        }
        if(jenisorder.equals("sms")) {
            kodedata=6;
            getSupportActionBar().setTitle("Beli Paket SMS");
            sk.setText(R.string.skpaketsms);
        }
        if(jenisorder.equals("kuota")) {
            kodedata = 2;
            getSupportActionBar().setTitle("Beli Paket Internet");
            sk.setText(R.string.skpaket);
        }
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





loadingbaru.setVisibility(View.VISIBLE);
isihalaman.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.dataproduk(kodedata);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                loadingbaru.setVisibility(View.GONE);


                if(response.body()!=null && response.body().getData()!=null) {
                    isihalaman.setVisibility(View.VISIBLE);
                List<Operator> listoperator = response.body().getData().getOperator();

                listproduk = response.body().getData().getProduk();
                listmetode = response.body().getData().getMetodetersedia();


                produkAdapter = new ProdukAdapter(context,produkClickListener, listproduk,sharedPrefManager.getSPMetodeBaru(),idproduk);

                metodeAdapter = new MetodeAdapter(context,itemClickListener, listmetode,hargaset,sharedPrefManager.getSPMetodeBaru());

                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(metodeAdapter);
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri contactUri = data.getData();
                String[] projection = {Phone.NUMBER};
                String[] projection2 = {Phone.DISPLAY_NAME};
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                Cursor cursor2 = getContentResolver()
                        .query(contactUri, projection2, null, null, null);
                cursor2.moveToFirst();

                int column2 = cursor2.getColumnIndex(Phone.DISPLAY_NAME);
                String nama = cursor2.getString(column2);
                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(Phone.NUMBER);
                String numbers = cursor.getString(column);
                // Do something with the phone number...
                String number = numbers.replace("+62", "0");
                editTexthp.setText(number.replaceAll("[^0-9]", ""));
                NamaKontak.setText(nama);
                NamaKontak.setVisibility(View.VISIBLE);
                LinearNamaKontak.setVisibility(View.VISIBLE);
//                Toast.makeText(getApplicationContext(),nama,Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void njajal() {

        String hp = editTexthp.getText().toString();
        String nomor = hp.substring(0,4);
        String nomor2=hp.substring(0,5);
      //  for( Produk a : listproduk) {

     terfilter = new ArrayList<>();

            if (nomor.equals("0831") || nomor.equals("0838") || nomor.equals("0832") || nomor.equals("0833") || nomor2.equals("08591") || nomor2.equals("08598")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(24)) {
                        terfilter.add(b);
                    }
                }

                operatorimg.setImageResource(R.drawable.ic_axis);
                showOperator("AXIS");
            }
            if (nomor.equals("0816") || nomor.equals("0814") || nomor.equals("0855") || nomor.equals("0815") || nomor.equals("0858") || nomor.equals("0856") || nomor.equals("0857")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(25)) {
                        terfilter.add(b);
                    }
                }
                operatorimg.setImageResource(R.drawable.ic_indosat);
                showOperator("INDOSAT");

            }
            if (nomor.equals("0896") || nomor.equals("0897") || nomor.equals("0899") || nomor.equals("0898") || nomor.equals("0895")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(26)) {
                        terfilter.add(b);
                    }
                }
                operatorimg.setImageResource(R.drawable.ic_three);
                showOperator("THREE");
            }
            if (nomor.equals("0812") || nomor.equals("0813") || nomor.equals("0821") || nomor.equals("0822") || nomor.equals("0823") || nomor.equals("0852") || nomor.equals("0853")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(27)) {
                        terfilter.add(b);
                    }
                }
                operatorimg.setImageResource(R.drawable.ic_telkomsel);
                showOperator("TELKOMSEL");
            }
            if (nomor.equals("0817") || nomor.equals("0818") || nomor.equals("0819") || nomor.equals("0877") || nomor.equals("0878") || nomor2.equals("08592") || nomor2.equals("08593") || nomor2.equals("08594") || nomor2.equals("08595") || nomor2.equals("08596") || nomor2.equals("08597") || nomor2.equals("08599") || nomor2.equals("08590")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(28)) {
                        terfilter.add(b);
                    }
                }
                operatorimg.setImageResource(R.drawable.ic_xl);
                showOperator("XL");
            }
            if (nomor.equals("0881") || nomor.equals("0882") || nomor.equals("0883") || nomor.equals("0884") || nomor.equals("0885") || nomor.equals("0886") || nomor.equals("0887") || nomor.equals("0888") || nomor.equals("0889")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(29)) {
                        terfilter.add(b);
                    }
                }
                operatorimg.setImageResource(R.drawable.ic_smartfren);
                showOperator("Smartfren");
            }

            if (nomor.equals("0851")) {
                for( Produk b : listproduk) {
                    if (b.getOperatorId().equals(60)) {
                        terfilter.add(b);
                    }
                }
               operatorimg.setImageResource(R.drawable.ic_byu);
                showOperator("By.u");
            }



        if(terfilter!=null) {
            produkAdapter = new ProdukAdapter(context,produkClickListener, terfilter,sharedPrefManager.getSPMetodeBaru(),idproduk);
            recyclerProduk.setAdapter(produkAdapter);
        }


    };


    public void showOperator(String operator2) {
        operator.setText(operator2);
        LinearOperator.setVisibility(View.VISIBLE);
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
            if (s.length() > 4)
                njajal();
            else
                idproduk=null;
            
            model1(s.toString());

            LinearNamaKontak.setVisibility(View.GONE);
        }


    };



    public void model1(String hp) {
        if(hp.length()>4) {
            boolean hpbenar = Enkripsi.validator(8,14,"nomorhp",hp);

            boolean produkbenar = idproduk != null;

            if (hpbenar) {
                hpcek.setImageResource(R.drawable.centang);
                hpcek.setVisibility(View.VISIBLE);
                LinearProduk.setVisibility(View.VISIBLE);
                LinearOperator.setVisibility(View.VISIBLE);
                if (produkbenar) {
                    LinearDeskripsi.setVisibility(View.VISIBLE);
                    LinearHarga.setVisibility(View.VISIBLE);
                    buttonSubmit.setVisibility(View.VISIBLE);
                } else {
                    LinearDeskripsi.setVisibility(View.GONE);
                    LinearHarga.setVisibility(View.GONE);
                    buttonSubmit.setVisibility(View.GONE);
                }
            } else {
                hpcek.setImageResource(R.drawable.salah);
                hpcek.setVisibility(View.VISIBLE);
                LinearDeskripsi.setVisibility(View.GONE);
                LinearHarga.setVisibility(View.GONE);
                buttonSubmit.setVisibility(View.GONE);
                LinearProduk.setVisibility(View.GONE);
                LinearOperator.setVisibility(View.GONE);
                operatorimg.setImageResource(R.drawable.ic_bantuan);
            }
        }
        else {

            hpcek.setImageResource(R.drawable.salah);
            hpcek.setVisibility(View.VISIBLE);
            LinearProduk.setVisibility(View.GONE);
            LinearOperator.setVisibility(View.GONE);
            LinearDeskripsi.setVisibility(View.GONE);
            LinearHarga.setVisibility(View.GONE);
            buttonSubmit.setVisibility(View.GONE);
            operatorimg.setImageResource(R.drawable.ic_bantuan);
        }
    }


}
