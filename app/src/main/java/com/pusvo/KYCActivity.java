package com.pusvo;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

public class KYCActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    private ProgressDialog progress;
    private Toolbar toolbar;
    Context context=this;
    Integer kycstatus;
    boolean emailbenar;
    boolean alamatbenar;
    boolean tanggalbenar;
    boolean kecamatanbenar;
    boolean desabenar;
    boolean adayangsalah;
    boolean setuju;
    private String id_desa;
    private String id_kecamatan;
    private String bank1;
    private String bank2;
    private String bank3;
    private String bank4;
    private String bank5;
    private AkunbankAdapter viewAdapter;
    private List<Result> results = new ArrayList<>();
    @BindView(R.id.editTanggal_lahir) EditText editTanggal_lahir;
    @BindView(R.id.editAlamat) EditText editAlamat;
    @BindView(R.id.etEmailIndodax) EditText etEmailIndodax;
    @BindView(R.id.gambar) ImageView gambar;
    @BindView(R.id.tanggalcek) ImageView tanggalcek;
    @BindView(R.id.alamatcek) ImageView alamatcek;
    @BindView(R.id.emailcek) ImageView emailcek;
    @BindView(R.id.LinearTanggalLahir) LinearLayout LinearTanggalLahir;
    @BindView(R.id.spinnerkabupaten) Spinner spinnerkabupaten;
    @BindView(R.id.spinnerbank1) Spinner spinnerbank1;
    @BindView(R.id.spinnerbank2) Spinner spinnerbank2;
    @BindView(R.id.spinnerbank3) Spinner spinnerbank3;
    @BindView(R.id.spinnerbank4) Spinner spinnerbank4;
    @BindView(R.id.spinnerbank5) Spinner spinnerbank5;
    @BindView(R.id.spinnerkecamatan) Spinner spinnerkecamatan;
    @BindView(R.id.spinnerdesa) Spinner spinnerdesa;
    @BindView(R.id.LinearProvinsi) LinearLayout LinearProvinsi;
    @BindView(R.id.LinearKabupaten) LinearLayout LinearKabupaten;
    @BindView(R.id.LinearDesa) LinearLayout LinearDesa;
    @BindView(R.id.LinearKecamatan) LinearLayout LinearKecamatan;
    @BindView(R.id.LinearEmail) LinearLayout LinearEmail;
    @BindView(R.id.LinearAlamat) LinearLayout LinearAlamat;
    @BindView(R.id.LinearBank1) LinearLayout LinearBank1;
    @BindView(R.id.LinearBank2) LinearLayout LinearBank2;
    @BindView(R.id.LinearBank3) LinearLayout LinearBank3;
    @BindView(R.id.LinearBank4) LinearLayout LinearBank4;
    @BindView(R.id.LinearBank5) LinearLayout LinearBank5;
    @BindView(R.id.tambahkan1)  TextView tambahkan1;
    @BindView(R.id.tambahkan2)  TextView tambahkan2;
    @BindView(R.id.tambahkan3)  TextView tambahkan3;
    @BindView(R.id.tambahkan4)  TextView tambahkan4;
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.editTextNorek1)  EditText editTextNorek1;
    @BindView(R.id.editTextNorek2)  EditText editTextNorek2;
    @BindView(R.id.editTextNorek3)  EditText editTextNorek3;
    @BindView(R.id.editTextNorek4)  EditText editTextNorek4;
    @BindView(R.id.editTextNorek5)  EditText editTextNorek5;
    @BindView(R.id.nama_user)  TextView nama_user;
    @BindView(R.id.detail) LinearLayout detail;
    @BindView(R.id.kycproses) LinearLayout kycproses;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;

    @OnClick(R.id.gambar)
    public void gambar() {
        if (!setuju) {
            setuju=true;
            gambar.setImageResource(R.drawable.cek);

        }
        else {
            setuju=false;
            gambar.setImageResource(R.drawable.kotak);
        }
        tampiltombol();
    }

    @OnClick(R.id.tambahkan1)
    public void tambahkan1() {
        LinearBank2.setVisibility(View.VISIBLE);
        tambahkan1.setVisibility(View.GONE);
    }
    @OnClick(R.id.tambahkan2)
    public void tambahkan2() {
        LinearBank3.setVisibility(View.VISIBLE);
        tambahkan2.setVisibility(View.GONE);
    }
    @OnClick(R.id.tambahkan3)
    public void tambahkan3() {
        LinearBank4.setVisibility(View.VISIBLE);
        tambahkan3.setVisibility(View.GONE);
    }
    @OnClick(R.id.tambahkan4)
    public void tambahkan4() {
        LinearBank5.setVisibility(View.VISIBLE);
        tambahkan4.setVisibility(View.GONE);
    }



    @OnClick(R.id.tampilkan)
    public void tampilkan() {
        if (!setuju) {
            setuju=true;
            gambar.setImageResource(R.drawable.cek);

        } else {
            setuju=false;
            gambar.setImageResource(R.drawable.kotak);
        }
        tampiltombol();
    }



    public void onBackPressed() {
        startActivity(new Intent(context, AkunSayaActivity.class));
    };

    @OnClick(R.id.pilihtanggal) public void pilihtanggal2() {
        openDateRangePicker();
    }
    @OnClick(R.id.LinearTanggalLahir) public void pilihtanggal() {
        openDateRangePicker();
    };
    @OnClick(R.id.editTanggal_lahir) public void pilihtanggal3() {
        openDateRangePicker();
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






    @OnClick(R.id.buttonSubmit)
    public void ubah() {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
        Sprite doubleBounce = new ThreeBounce();
        doubleBounce.setColor(R.color.bg_colordark);
        progress.setIndeterminateDrawable(doubleBounce);
            progress.show();



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
        Call<Data> call = api.simpankyc("Bearer "+sharedPrefManager.getSPBearer(),etEmailIndodax.getText().toString(),editTanggal_lahir.getText().toString(),bank1,bank2,bank3,bank4,bank5,editTextNorek1.getText().toString(),editTextNorek2.getText().toString(),editTextNorek3.getText().toString(),editTextNorek4.getText().toString(),editTextNorek5.getText().toString(),id_kecamatan,id_desa,editAlamat.getText().toString(),randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                    progress.dismiss();
                    if (response.body() != null && response.body().getPesan() != null) {
                        String message = response.body().getPesan();
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("KYC");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        editAlamat.addTextChangedListener(AlamatWatcher);
        editTextNorek1.addTextChangedListener(NorekWatcher);
        final  RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recycler_view);
        etEmailIndodax.addTextChangedListener(EmailWatcher);
        editTanggal_lahir.addTextChangedListener(TanggalWatcher);
        progress = new ProgressDialog(context);
        progress.setCancelable(false);
        progress.setMessage("Sedang memuat...");
        Sprite doubleBounce = new ThreeBounce();
        doubleBounce.setColor(R.color.bg_colordark);
        progress.setIndeterminateDrawable(doubleBounce);
        progress.show();
        setuju=false;

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
                             progress.dismiss();
                             if (response.body() != null && response.body().getData().getKYCStatus() != null) {
                                 kycstatus = response.body().getData().getKYCStatus();
                                 String nama = sharedPrefManager.getSPNama();
                                 List<Listkyc> listkyc = response.body().getData().getListkyc();
                                 List<Bankdetail> listbank = response.body().getData().getListbank();

                                 BankAdapter bankAdapter = new BankAdapter(context,
                                         R.layout.state_list, R.id.spinnerText, listbank);
                                 spinnerbank1.setAdapter(bankAdapter);

                                 spinnerbank1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         Bankdetail idproduks2 = bankAdapter.getItem(position);
                                         bank1 = idproduks2.getId().toString();
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }

                                 });

                                 spinnerbank2.setAdapter(bankAdapter);
                                 spinnerbank2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         Bankdetail idproduks2 = bankAdapter.getItem(position);
                                         bank2 = idproduks2.getId().toString();
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }

                                 });

                                 spinnerbank3.setAdapter(bankAdapter);
                                 spinnerbank3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         Bankdetail idproduks2 = bankAdapter.getItem(position);
                                         bank3 = idproduks2.getId().toString();
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }

                                 });

                                 spinnerbank4.setAdapter(bankAdapter);
                                 spinnerbank4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         Bankdetail idproduks2 = bankAdapter.getItem(position);
                                         bank4 = idproduks2.getId().toString();
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }

                                 });

                                 spinnerbank5.setAdapter(bankAdapter);
                                 spinnerbank5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                     @Override
                                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                         Bankdetail idproduks2 = bankAdapter.getItem(position);
                                         bank5 = idproduks2.getId().toString();
                                     }

                                     @Override
                                     public void onNothingSelected(AdapterView<?> parent) {

                                     }

                                 });


                                 nama_user.setText(nama);


                                 if (kycstatus.equals(0)) {

                                     LinearProvinsi.setVisibility(View.VISIBLE);
                                     LinearAlamat.setVisibility(View.VISIBLE);
                                     LinearTanggalLahir.setVisibility(View.VISIBLE);
                                     LinearEmail.setVisibility(View.VISIBLE);
                                     detail.setVisibility(View.VISIBLE);
                                 }
                                 if (kycstatus.equals(1)) {

                                     detail.setVisibility(View.GONE);
                                     kycproses.setVisibility(View.VISIBLE);
                                 }
                                 if (kycstatus.equals(2)) {

                                     viewAdapter = new AkunbankAdapter(context, listkyc);
                                     RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                     recyclerView.setLayoutManager(mLayoutManager);
                                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                                     recyclerView.setAdapter(viewAdapter);
                                     detail.setVisibility(View.VISIBLE);
                                 }




                             }
                             else {
                                 Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();

                             }
                         }
                         
            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                progress.dismiss();
                t.printStackTrace();
                Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();

            }

        });


        List<String> categories = new ArrayList<String>();
        categories.add("Pilih provinsi");
        categories.add("Aceh");
        categories.add("Bali");
        categories.add("Banten");
        categories.add("Bengkulu");
        categories.add("DI Yogyakarta");
        categories.add("DKI Jakarta");
        categories.add("Gorontalo");
        categories.add("Jambi");
        categories.add("Jawa Barat");
        categories.add("Jawa Tengah");
        categories.add("Jawa Timur");
        categories.add("Kalimantan Barat");
        categories.add("Kalimantan Selatan");
        categories.add("Kalimantan Tengah");
        categories.add("Kalimantan Timur");
        categories.add("Kalimantan Utara");
        categories.add("Kepulauan Bangka Belitung");
        categories.add("Kepulauan Riau");
        categories.add("Lampung");
        categories.add("Maluku");
        categories.add("Maluku Utara");
        categories.add("Nusa Tenggara Barat");
        categories.add("Nusa Tenggara Timur");
        categories.add("Papua");
        categories.add("Papua Barat");
        categories.add("Riau");
        categories.add("Sulawesi Barat");
        categories.add("Sulawesi Selatan");
        categories.add("Sulawesi Tengah");
        categories.add("Sulawesi Tenggara");
        categories.add("Sulawesi Utara");
        categories.add("Sumatera Barat");
        categories.add("Sumatera Selatan");
        categories.add("Sumatera Utara");
        List<String> idprov = new ArrayList<String>();
        idprov.add("0");
        idprov.add("11");
        idprov.add("51");
        idprov.add("36");
        idprov.add("17");
        idprov.add("34");
        idprov.add("31");
        idprov.add("75");
        idprov.add("15");
        idprov.add("32");
        idprov.add("33");
        idprov.add("35");
        idprov.add("61");
        idprov.add("63");
        idprov.add("62");
        idprov.add("64");
        idprov.add("65");
        idprov.add("19");
        idprov.add("21");
        idprov.add("18");
        idprov.add("81");
        idprov.add("82");
        idprov.add("52");
        idprov.add("53");
        idprov.add("94");
        idprov.add("91");
        idprov.add("14");
        idprov.add("76");
        idprov.add("73");
        idprov.add("72");
        idprov.add("74");
        idprov.add("71");
        idprov.add("13");
        idprov.add("16");
        idprov.add("12");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);

        sharedPrefManager = new SharedPrefManager(this);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String provinsi = dataAdapter.getItem(i);
                String provinsi=idprov.get(i);

                if (!provinsi.equals("0")) {
                    progress = new ProgressDialog(context);
                    progress.setCancelable(false);
                    progress.setMessage("Sedang memuat...");
                    Sprite doubleBounce = new ThreeBounce();
                    doubleBounce.setColor(R.color.bg_colordark);
                    progress.setIndeterminateDrawable(doubleBounce);
                    progress.show();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    API api = retrofit.create(API.class);
                    //String provinsi = spinner.getSelectedItem().toString();
                    Call<Data> call = api.kabupatenkota("Bearer "+sharedPrefManager.getSPBearer(),provinsi);
                    call.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                            progress.dismiss();
                            if (response.body() != null && response.body().getData().getListkabupaten()!=null) {
                                List<Listkabupaten> semuakabupaten = response.body().getData().getListkabupaten();
                                semuakabupaten.add(0, null);
                                final KabupatenAdapter kabupatenAdapter = new KabupatenAdapter(context,
                                        R.layout.state_list, R.id.spinnerText, semuakabupaten);

                                spinnerkabupaten.setAdapter(kabupatenAdapter);
                                LinearKabupaten.setVisibility(View.VISIBLE);
                                spinnerkabupaten.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if ((int) position != 0) {

                                            progress = new ProgressDialog(context);
                                            progress.setCancelable(false);
                                            progress.setMessage("Sedang memuat...");
                                            Sprite doubleBounce = new ThreeBounce();
                                            doubleBounce.setColor(R.color.bg_colordark);
                                            progress.setIndeterminateDrawable(doubleBounce);
                                            progress.show();
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(URL)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            API api = retrofit.create(API.class);
                                            Call<Data> call = api.kecamatan("Bearer " + sharedPrefManager.getSPBearer(), Long.toString(kabupatenAdapter.getItem(position).getId()));
                                            call.enqueue(new Callback<Data>() {
                                                @Override
                                                public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {

                                                    progress.dismiss();
                                                    if (response.body() != null && response.body().getData().getListkecamatan()!=null) {
                                                        List<Listkabupaten> semuakecamatan = response.body().getData().getListkecamatan();
                                                        //Listkabupaten cityDetails = alamatAdapter.getItem(position);
                                                        semuakecamatan.add(0, null);
                                                        final KabupatenAdapter kecamatanAdapter = new KabupatenAdapter(context,
                                                                R.layout.state_list, R.id.spinnerText, semuakecamatan);
                                                        spinnerkecamatan.setAdapter(kecamatanAdapter);
                                                        LinearKecamatan.setVisibility(View.VISIBLE);
                                                        spinnerkecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                            @Override
                                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                                if ((int) position != 0) {
                                                                    id_kecamatan = Long.toString(kecamatanAdapter.getItem(position).getId());

                                                                    progress = new ProgressDialog(context);
                                                                    progress.setCancelable(false);
                                                                    progress.setMessage("Sedang memuat...");
                                                                    Sprite doubleBounce = new ThreeBounce();
                                                                    doubleBounce.setColor(R.color.bg_colordark);
                                                                    progress.setIndeterminateDrawable(doubleBounce);
                                                                    progress.show();
                                                                    Retrofit retrofit = new Retrofit.Builder()
                                                                            .baseUrl(URL)
                                                                            .addConverterFactory(GsonConverterFactory.create())
                                                                            .build();
                                                                    API api = retrofit.create(API.class);
                                                                    Call<Data> call = api.desa("Bearer " + sharedPrefManager.getSPBearer(), Long.toString(kecamatanAdapter.getItem(position).getId()));
                                                                    call.enqueue(new Callback<Data>() {
                                                                        @Override
                                                                        public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                                                                            progress.dismiss();
                                                                            if (response.body() != null && response.body().getData().getListdesa() != null) {
                                                                                List<Listkabupaten> semuadesa = response.body().getData().getListdesa();
                                                                                //Listkabupaten cityDetails = alamatAdapter.getItem(position);
                                                                                semuadesa.add(0, null);
                                                                                final KabupatenAdapter desaAdapter = new KabupatenAdapter(context,
                                                                                        R.layout.state_list, R.id.spinnerText, semuadesa);
                                                                                spinnerdesa.setAdapter(desaAdapter);
                                                                                spinnerdesa.setPrompt("Pilih desa/kelurahan");
                                                                                LinearDesa.setVisibility(View.VISIBLE);
                                                                                spinnerdesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                    @Override
                                                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                                                        if ((int) position != 0) {
                                                                                            //id_desav.setText(Long.toString(desaAdapter.getItem(position).getId()));
                                                                                            id_desa = Long.toString(desaAdapter.getItem(position).getId());

                                                                                        } else {
                                                                                            id_desa = "";
                                                                                        }
                                                                                        tampiltombol();
                                                                                    }

                                                                                    @Override
                                                                                    public void onNothingSelected(AdapterView<?> parent) {
                                                                                    }
                                                                                });

                                                                            }
                                                                            else {
                                                                                Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<Data> call, Throwable t) {
                                                                            progress.dismiss();
                                                                            t.printStackTrace();
                                                                            Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });


                                                                } else {
                                                                    id_kecamatan = "";
                                                                    id_desa = "";
                                                                    LinearDesa.setVisibility(View.GONE);
                                                                }
                                                                tampiltombol();
                                                            }

                                                            @Override
                                                            public void onNothingSelected(AdapterView<?> parent) {
                                                                tampiltombol();
                                                            }
                                                        });

                                                    }
                                                    else {
                                                        Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                                                    }
                                                }


                                                @Override
                                                public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                                                    progress.dismiss();
                                                    t.printStackTrace();
                                                    Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                        } else {
                                            id_kecamatan = "";
                                            id_desa = "";
                                            LinearKecamatan.setVisibility(View.GONE);
                                            LinearDesa.setVisibility(View.GONE);
                                        }
                                        tampiltombol();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                        tampiltombol();
                                    }
                                });


                                progress.dismiss();

                            }
                            else {
                                Toast.makeText(context, R.string.error500, Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                            progress.dismiss();
                            t.printStackTrace();
                            Toast.makeText(context, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    id_kecamatan="";
                    id_desa="";
                    LinearKabupaten.setVisibility(View.GONE);
                    LinearKecamatan.setVisibility(View.GONE);
                    LinearDesa.setVisibility(View.GONE);
                }
                tampiltombol();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tampiltombol();
            }
        });
    }




    public final TextWatcher NorekWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            tampiltombol();
        }
    };




    public final TextWatcher AlamatWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Pattern mPattern = Pattern.compile("^[A-Za-z0-9-.,/)(& ]{9,160}$");
            Matcher matcher = mPattern.matcher(s.toString());
            if(matcher.find())
            {
                alamatcek.setImageResource(R.drawable.centang);
                alamatcek.setVisibility(View.VISIBLE);
                alamatbenar=true;
            }
            else {
                alamatcek.setImageResource(R.drawable.salah);
                alamatcek.setVisibility(View.VISIBLE);
                alamatbenar=false;
            }
            tampiltombol();

        }
    };


    public final TextWatcher EmailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if(!TextUtils.isEmpty(s.toString()) && Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches())
            {
                emailcek.setImageResource(R.drawable.centang);
                emailcek.setVisibility(View.VISIBLE);
                emailbenar=true;
            }
            else {
                emailcek.setImageResource(R.drawable.salah);
                emailcek.setVisibility(View.VISIBLE);
                emailbenar=false;
            }
            tampiltombol();
        }
    };


    public final TextWatcher TanggalWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().length()>9)
                tanggalbenar= Integer.parseInt(s.toString().substring(6, 10)) < 2004;

else
    tanggalbenar=false;

if(tanggalbenar)
    tanggalcek.setImageResource(R.drawable.centang);
else
    tanggalcek.setImageResource(R.drawable.salah);
            tanggalcek.setVisibility(View.VISIBLE);

            tampiltombol();
        }
    };



    private void openDateRangePicker(){
        Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                editTanggal_lahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();


    }

//    private void tampiltombol() {
//        buttonSubmit.setVisibility(View.VISIBLE);
//    }
    private void tampiltombol() {

        if(kycstatus.equals(2)) {
            if(editTextNorek1.length()>=8 || editTextNorek2.length()>=8 || editTextNorek3.length()>=8 || editTextNorek4.length()>=8 || editTextNorek5.length()>=8) {
                if (adayangsalah) {
                    buttonSubmit.setVisibility(View.GONE);
                    // Toast.makeText(context, "tes2", Toast.LENGTH_SHORT).show();
                }
                else  {
                    if (setuju)
                        buttonSubmit.setVisibility(View.VISIBLE);
                    else
                        buttonSubmit.setVisibility(View.GONE);
                }
                    //Toast.makeText(context, "tes3", Toast.LENGTH_SHORT).show();
            }
            else {
                buttonSubmit.setVisibility(View.GONE);
            }
        }
        if(kycstatus.equals(0)) {
            desabenar=id_desa!=null && id_desa.length() > 5;
            kecamatanbenar=id_kecamatan!=null && id_kecamatan.length() > 5;
            if(editTextNorek1.length()>=8 || editTextNorek2.length()>=8 || editTextNorek3.length()>=8 || editTextNorek4.length()>=8 || editTextNorek5.length()>=8) {
                if (tanggalbenar && emailbenar && alamatbenar && kecamatanbenar && desabenar && setuju)
                    buttonSubmit.setVisibility(View.VISIBLE);
                else
                    buttonSubmit.setVisibility(View.GONE);
            }
            else {
                buttonSubmit.setVisibility(View.GONE);
            }
            }



        }
    }

