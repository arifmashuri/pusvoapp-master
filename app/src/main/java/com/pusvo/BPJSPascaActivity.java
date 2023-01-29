package com.pusvo;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.VISIBLE;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;


public class BPJSPascaActivity extends AppCompatActivity {
    public void onBackPressed() {
        startActivity(new Intent(BPJSPascaActivity.this, MainActivity.class));
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


    SharedPrefManager sharedPrefManager;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    public static final int PICK_CONTACT_REQUEST = 1;
    public static final int PICK_CONTACT_REQUEST2 = 2;
    private ProgressDialog progress;
private Toolbar toolbar;
    Integer idproduk;

    @BindView(R.id.NamaKontak) TextView NamaKontak;
    @BindView(R.id.notice) TextView notice;
    @BindView(R.id.LinearNamaKontak) LinearLayout LinearNamaKontak;
    @BindView(R.id.LinearNamaMeter) LinearLayout LinearNamaMeter;
    @BindView(R.id.NamaMeter) TextView NamaMeter;
    @BindView(R.id.editTexthp) EditText editTexthp;
    @BindView(R.id.editTextMeter) EditText editTextMeter;
    @BindView(R.id.jenismeter) TextView jenismeter;
    @BindView(R.id.buttonPaste) ImageView buttonPaste;
    @BindView(R.id.sk) TextView sk;
    @BindView(R.id.buttonSubmit) Button buttonSubmit;
    @BindView(R.id.hpcek) ImageView hpcek;
    @BindView(R.id.metercek) ImageView metercek;
    @BindView(R.id.LinearHarga) LinearLayout LinearHarga;
    List<Produk> listproduk;
    List<Produk> terfilter;
    List<Operator> listoperator;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.loadingbaru) LinearLayout loadingbaru;
    @BindView(R.id.isihalaman)
    NestedScrollView isihalaman;

    List<Metode> listmetode;
    Double hargaset;
    Integer metodekirim;
    ItemClickListener itemClickListener;
    private MetodeAdapter metodeAdapter;

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
    @OnClick(R.id.buttonPaste) void losgin() {
        Object clipboardService = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager = (ClipboardManager) clipboardService;
        if (clipboardManager == null) return;
        ClipData clipData = clipboardManager.getPrimaryClip();
        if (clipData == null) return;
            // Get source text.
            ClipData.Item item = clipData.getItemAt(0);
            String text = item.getText().toString();
                if (item.getText().toString().length() > 10 && text.matches("[0-9]+")) {
                    // Set the text to target textview.
                    editTextMeter.setText(text);
                } else {
                    Toast.makeText(BPJSPascaActivity.this, "Periksa format nomor VA", Toast.LENGTH_SHORT).show();

                }

    }
    @OnClick(R.id.buttonSubmit) public void ubah() {
        //membuat progress dialog
        if (editTexthp.getText().toString().length() > 7 && editTextMeter.getText().toString().length() > 7) {
            progress = new ProgressDialog(this);
            progress.setCancelable(false);
            progress.setMessage("Sedang memuat...");
            Sprite doubleBounce = new ThreeBounce();
            doubleBounce.setColor(R.color.bg_colordark);
            progress.setIndeterminateDrawable(doubleBounce);
            progress.show();

notice.setVisibility(View.GONE);
            //mengambil data dari edittext
            String hp = editTexthp.getText().toString();
            String meter = editTextMeter.getText().toString();
          //  String vendor = (State) spinner1.getSelectedItem().toString();
        //    String produk = spinner.getSelectedItem().toString();
//        String produk = editTexthp.getText().toString();

            // int selectedId2 = radioGroup2.getCheckedRadioButtonId();
            // mencari id radio button

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
            Call<Data> call = api.simpantransaksi("Bearer "+sharedPrefManager.getSPBearer(),hp,meter,idproduk, metodekirim,Enkripsi.vapps(context), randomUUIDString, SHA1, api_key, api_secret);
            call.enqueue(new Callback<Data>() {
                @Override
                public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {
                    progress.dismiss();
                    if (response.body() != null && response.body().getStatus() != null) {
                        Integer status = response.body().getStatus();
                        if (status.equals(1)) {
                            Data data = response.body().getData();
                            String kode = data.getKode();
                            Intent i = new Intent(BPJSPascaActivity.this, BayarActivity.class);
                            i.putExtra("transaction_kode", kode);
                            startActivity(i);

                        } else {
                            String message = response.body().getPesan();
                            Toast.makeText(BPJSPascaActivity.this, message, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BPJSPascaActivity.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(BPJSPascaActivity.this, "Nomor HP atau nomor VA tidak lengkap", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
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
                NamaKontak.setVisibility(VISIBLE);
                LinearNamaKontak.setVisibility(VISIBLE);
//                Toast.makeText(getApplicationContext(),nama,Toast.LENGTH_SHORT).show();

            }
        }
        if (requestCode == PICK_CONTACT_REQUEST2) {
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
                editTextMeter.setText(number.replaceAll("[^0-9]", ""));
                NamaMeter.setText(nama);
                NamaMeter.setVisibility(VISIBLE);
                LinearNamaMeter.setVisibility(VISIBLE);
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

        idproduk=1052;

        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bayar Tagihan BPJS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        sk.setText(R.string.skbpjs);
        jenismeter.setText(R.string.no_va);
        editTexthp.addTextChangedListener(HPWatcher);
        editTextMeter.addTextChangedListener(MeterWatcher);
        buttonPaste.setVisibility(VISIBLE);
        metodekirim = sharedPrefManager.getSPMetodeBaru();

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Integer s) {
                metodekirim=s;
                sharedPrefManager.saveSPInt(SharedPrefManager.SP_METODEBARU, s);
                model1(editTexthp.getText().toString(),editTextMeter.getText().toString());
            }
        };


        loadingbaru.setVisibility(View.VISIBLE);
        isihalaman.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.dataproduk(10);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull retrofit2.Response<Data> response) {

                loadingbaru.setVisibility(View.GONE);
                if (response.body() != null && response.body().getData() != null) {

                    listoperator = response.body().getData().getOperator();
                    listproduk = response.body().getData().getProduk();
                    listmetode = response.body().getData().getMetodetersedia();
                    metodeAdapter = new MetodeAdapter(context, itemClickListener, listmetode, hargaset, sharedPrefManager.getSPMetodeBaru());
                    recyclerView.setAdapter(metodeAdapter);
                    LinearHarga.setVisibility(View.VISIBLE);
                    isihalaman.setVisibility(View.VISIBLE);
                    //LinearProduk.setVisibility(View.VISIBLE);
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



    /**
     * Helps in downloading the state and city details
     * and populating the spinner
     */
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
            model1(s.toString(),editTextMeter.getText().toString());
        }
    };
    public final TextWatcher MeterWatcher = new TextWatcher() {
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
            model1(editTexthp.getText().toString(),s.toString());
        }
    };



    public void model1(String hp, String meter) {
        if (hp.length() > 4 || meter.length() > 4) {
            boolean hpbenar = Enkripsi.validator(8,14,"nomorhp",hp);
            boolean meterbenar = Enkripsi.validator(10,16,"nomor",meter);


            if (hpbenar) {
                hpcek.setImageResource(R.drawable.centang);
                hpcek.setVisibility(View.VISIBLE);
            } else {
                hpcek.setImageResource(R.drawable.salah);
                hpcek.setVisibility(View.VISIBLE);
            }
            if (meterbenar) {
                metercek.setImageResource(R.drawable.centang);
                metercek.setVisibility(View.VISIBLE);
            } else {
                metercek.setImageResource(R.drawable.salah);
                metercek.setVisibility(View.VISIBLE);
            }
            if(hp.isEmpty())
                hpcek.setVisibility(View.GONE);
            if(meter.isEmpty())
                metercek.setVisibility(View.GONE);

            if(hpbenar && meterbenar)
                buttonSubmit.setVisibility(View.VISIBLE);
            else
                buttonSubmit.setVisibility(View.GONE);
        }
    }


}
