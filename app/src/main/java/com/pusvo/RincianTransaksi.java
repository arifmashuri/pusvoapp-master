package com.pusvo;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.VISIBLE;
import static com.pusvo.PrintActivity.intToByteArray;

public class RincianTransaksi extends AppCompatActivity implements Runnable {
    BluetoothAdapter mBluetoothAdapter;

    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    SharedPrefManager sharedPrefManager;
    CountDownTimer mCountDownTimer;
    Handler handler = new Handler();
    Context context = this;
    Runnable runnable;


    @BindView(R.id.detail)
    LinearLayout detail;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";

    @BindView(R.id.hp)
    TextView Texthp;
    @BindView(R.id.waktu)
    TextView Textwaktu;
    @BindView(R.id.waktu_bayar)
    TextView Textwaktubayar;
    @BindView(R.id.waktu_sukses)
    TextView Textwaktusukses;
    @BindView(R.id.textproduk)
    TextView Textproduk;
    @BindView(R.id.id_transaksi)
    TextView Texttransaction_id;
    @BindView(R.id.meter)
    TextView TextMeter;
    @BindView(R.id.va)
    TextView TextVA;
    @BindView(R.id.LinearMeter)
    LinearLayout LinearMeter;
    @BindView(R.id.nama)
    TextView TextNama;
    @BindView(R.id.jumlahbulan)
    TextView TextJumlahBulan;
    @BindView(R.id.bulan)
    TextView TextBulan;
    @BindView(R.id.sn)
    TextView TextSN;
    @BindView(R.id.voucher)
    TextView TextVoucher;
    @BindView(R.id.sukses)
    TextView sukses;
    @BindView(R.id.proses)
    TextView proses;
    @BindView(R.id.gagal)
    TextView gagal;
    @BindView(R.id.jumlahorang)
    TextView TextJumlahOrang;
    @BindView(R.id.LinearNama)
    LinearLayout LinearNama;
    @BindView(R.id.LinearJumlahBulan)
    LinearLayout LinearJumlahBulan;
    @BindView(R.id.LinearBulan)
    LinearLayout LinearBulan;
    @BindView(R.id.LinearSN)
    LinearLayout LinearSN;
    @BindView(R.id.LinearVA)
    LinearLayout LinearVA;
    @BindView(R.id.LinearVoucher)
    LinearLayout LinearVoucher;
    @BindView(R.id.LinearJumlahOrang)
    LinearLayout LinearJumlahOrang;
    @BindView(R.id.Linearwaktusukses)
    LinearLayout Linearwaktusukses;
    @BindView(R.id.Linearwaktubayar)
    LinearLayout Linearwaktubayar;
    @BindView(R.id.print)
    ImageView print;
    @BindView(R.id.kirimwa)
    ImageView kirimwa;
    //    @BindView(R.id.whatsapp) ImageView whatsapp;
//    @BindView(R.id.telegram) ImageView telegram;
    @BindView(R.id.bantuan)
    LinearLayout bantuan;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress2)
    ProgressBar progress2;
    @BindView(R.id.swlayout)
    SwipeRefreshLayout swLayout;

    @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;


    @OnClick(R.id.telegram)
    void telegram() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://telegram.me/pusvoretail"));
        final String appName = "org.telegram.messenger";
        i.setPackage(appName);
        this.startActivity(i);

    }

    @OnClick(R.id.whatsapp)
    void whatsapp() {
        PackageManager pm = this.getPackageManager();
        String transaction_id = Texttransaction_id.getText().toString();
        String hp = Texthp.getText().toString();
        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.setAction(Intent.ACTION_VIEW);
        sendIntent.setPackage("com.whatsapp");
        //                String url = "https://api.whatsapp.com/send?phone=" + "6283844662524" + "&text=" + "your message";
        String url = "whatsapp://send?phone=6283844662524&text=Halo Pusvo, tolong cek transaksi di nomor " + hp + " id order " + transaction_id;
        sendIntent.setData(Uri.parse(url));
        if (Enkripsi.isPackageInstalled("com.whatsapp", pm)) {
            this.startActivity(sendIntent);
        } else {
            Toast.makeText(this, "Aplikasi Whatsapp belum terinstal", Toast.LENGTH_SHORT).show();
        }

    }

    Toolbar toolbar;


    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rincian_transaksi);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rincian Transaksi");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        //final ShimmerFrameLayout shimmer =(ShimmerFrameLayout) findViewById(R.id.shimmer);


        lihatrincian();
        // Menginisiasi  NavigationView


        // get the selected dropdown list value
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        //swLayout=(SwipeRefreshLayout) findViewById(R.id.swlayout);


        swLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        lihatrincian();
                    }
                });
            }
        });


    }

    @OnClick(R.id.copysn)
    void copysn() {
        String keterangan = TextSN.getText().toString();
        setClipboard(this, keterangan);
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }

    ;

    @OnClick(R.id.copyvoucher)
    void copyvoucher() {
        String keterangan = TextVoucher.getText().toString();
        setClipboard(this, keterangan);
        Toast.makeText(this, "Berhasil disalin", Toast.LENGTH_SHORT).show();
    }

    ;

    @OnClick(R.id.kirimwa)
    void kirimwa() {

        String sn = TextSN.getText().toString();
        String hp = Texthp.getText().toString().substring(1);
        String produk = Textproduk.getText().toString();
        if (sn != null && !sn.equals("")) {
            PackageManager pm = RincianTransaksi.this.getPackageManager();

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "whatsapp://send?phone=62" + hp + "&text=Transaksi " + produk + " sudah sukses dengan SN/Voucher : " + sn;
            sendIntent.setData(Uri.parse(url));
            if (Enkripsi.isPackageInstalled("com.whatsapp", pm)) {
                startActivity(sendIntent);
            } else {
                Toast.makeText(RincianTransaksi.this, "Aplikasi Whatsapp belum terinstal", Toast.LENGTH_SHORT).show();
            }
        } else {
            PackageManager pm = RincianTransaksi.this.getPackageManager();
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "whatsapp://send?phone=62" + hp + "&text=Transaksi " + produk + " sudah sukses dengan SN : " + sn;
            sendIntent.setData(Uri.parse(url));
            if (Enkripsi.isPackageInstalled("com.whatsapp", pm)) {
                startActivity(sendIntent);
            } else {
                Toast.makeText(RincianTransaksi.this, "Aplikasi Whatsapp belum terinstal", Toast.LENGTH_SHORT).show();
            }
        }


    }

    @OnClick(R.id.print)
    void print() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 11

            );
        }

        sharedPrefManager = new SharedPrefManager(this);
        String mDeviceAddress = sharedPrefManager.getSPBtdevice();
        if (mDeviceAddress==null || mDeviceAddress.isEmpty()) {
            Toast.makeText(RincianTransaksi.this, "Printer " + mDeviceAddress + "belum diatur, cek menu Akun->Pengaturan printer", Toast.LENGTH_SHORT).show();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(RincianTransaksi.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
            } else {
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent,
                            REQUEST_ENABLE_BT);
                } else {

                    //Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(RincianTransaksi.this,
                            "Sedang mencetak...", mBluetoothDevice.getName(), true, true);
                    Thread mBlutoothConnectThread = new Thread();
                    try {
                        mBlutoothConnectThread.start();
                    } finally {
                        Thread t = new Thread() {
                            public void run() {
printing();
                            }

                        };

                        t.start();
                    }

                }


            }

        }
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                //do something
                refreshrincian();
                handler.postDelayed(runnable, 5000);
            }
        }, 5000);

        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    //Bundle mExtra = mDataIntent.getExtras();
                    //String mDeviceAddress = mExtra.getString("DeviceAddress");

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        {
                            ActivityCompat.requestPermissions(RincianTransaksi.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                            return;
                        }

                    }
                    String mDeviceAddress = sharedPrefManager.getSPBtdevice();
                    mBluetoothDevice = mBluetoothAdapter
                            .getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(this,
                            "Menyimpan...", mBluetoothDevice.getName() + " : "
                                    + mBluetoothDevice.getAddress(), true, false);
                    Thread mBlutoothConnectThread = new Thread(this);
                    mBlutoothConnectThread.start();
                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress dialog with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
//                    try{
//                    ListPairedDevices();
//                    Intent connectIntent = new Intent(PrintActivity.this,
//                            DeviceListActivity.class);
//                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);

                    String mDeviceAddress = sharedPrefManager.getSPBtdevice();


                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(RincianTransaksi.this,
                            "Sedang mencetak B...", mBluetoothDevice.getName(), true, true);
                    Thread mBlutoothConnectThread = new Thread();
                    try {
                        mBlutoothConnectThread.start();
                    } finally {
                        Thread t = new Thread() {
                            public void run() {
                                printing();
                            }
                        };
                        t.start();
                    }


                } else {
                    Toast.makeText(RincianTransaksi.this, "Dibatalkan", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void run() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(RincianTransaksi.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CONNECT_DEVICE);
                    return;
                }
            }
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothSocket.connect();
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(RincianTransaksi.this, "Berhasil2", Toast.LENGTH_SHORT).show();
            mBluetoothSocket.close();

            if (sharedPrefManager.getSPBtoff() && mBluetoothAdapter != null)
                mBluetoothAdapter.disable();
        } catch (IOException eConnectException) {
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            // Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            //Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    public String jadikansatu() {

        String hp = Texthp.getText().toString();
        String meter = TextMeter.getText().toString();
        String jumlahbulan = TextJumlahBulan.getText().toString();
        String jumlahorang = TextJumlahOrang.getText().toString();
        String bulan = TextBulan.getText().toString();
        String nama = TextNama.getText().toString();
        String produk = Textproduk.getText().toString();
        String id_order = Texttransaction_id.getText().toString();
        String sn = TextSN.getText().toString();
        String voucher = TextVoucher.getText().toString();
        String keseluruhan = "";
        if (id_order != null) {
            keseluruhan = keseluruhan + "ID Order : " + pecahstring(id_order);
        }
        if (meter != null && meter.length() > 4) {
            keseluruhan = keseluruhan + "No Meter : " + pecahstring(meter);
        }
        if (nama != null && !nama.equals("")) {
            keseluruhan = keseluruhan + "Nama     : " + pecahstring(nama);
        }
        if (jumlahorang != null && !jumlahorang.equals("")) {
            keseluruhan = keseluruhan + "Jml Orang: " + pecahstring(jumlahorang);
        }
        if (jumlahbulan != null && !jumlahbulan.equals("")) {
            keseluruhan = keseluruhan + "Jml Bulan: " + pecahstring(jumlahbulan);
        }

        if (bulan != null && !bulan.equals("")) {
            keseluruhan = keseluruhan + "Bulan    : " + pecahstring(bulan);
        }
        if (hp != null) {
            keseluruhan = keseluruhan + "No HP    : " + pecahstring(hp);
        }
        if (produk != null) {
            keseluruhan = keseluruhan + "Produk   : " + pecahstring(produk);
        }

        if (sn != null) {
            keseluruhan = keseluruhan + "SN/Vcr   : " + pecahstring(sn);
        }


        return keseluruhan;
    }

    public String pecahstring(String snlengkap) {
        if (snlengkap.length() > 21) {
            if (snlengkap.length() <= 42) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n";
            }
            if (snlengkap.length() <= 63) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, 42);
                String sn3 = snlengkap.substring(42, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n           " + sn3 + "\n";
            }
            if (snlengkap.length() <= 84) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, 42);
                String sn3 = snlengkap.substring(42, 63);
                String sn4 = snlengkap.substring(63, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n           " + sn3 + "\n           " + sn4 + "\n";
            }
            if (snlengkap.length() <= 105) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, 42);
                String sn3 = snlengkap.substring(42, 63);
                String sn4 = snlengkap.substring(63, 84);
                String sn5 = snlengkap.substring(84, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n           " + sn3 + "\n           " + sn4 + "\n           " + sn5;
            }

            if (snlengkap.length() <= 126) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, 42);
                String sn3 = snlengkap.substring(42, 63);
                String sn4 = snlengkap.substring(63, 84);
                String sn5 = snlengkap.substring(84, 105);
                String sn6 = snlengkap.substring(105, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n           " + sn3 + "\n           " + sn4 + "\n           " + sn5 + "\n           " + sn6;
            }
            if (snlengkap.length() <= 147) {
                String sn1 = snlengkap.substring(0, 21);
                String sn2 = snlengkap.substring(21, 42);
                String sn3 = snlengkap.substring(42, 63);
                String sn4 = snlengkap.substring(63, 84);
                String sn5 = snlengkap.substring(84, 105);
                String sn6 = snlengkap.substring(105, 126);
                String sn7 = snlengkap.substring(126, snlengkap.length());
                return sn1 + "\n           " + sn2 + "\n           " + sn3 + "\n           " + sn4 + "\n           " + sn5 + "\n           " + sn6 + "\n           " + sn7;
            }
        } else {
            return snlengkap + "\n";
        }
        return null;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(RincianTransaksi.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CONNECT_DEVICE);
                    return;
                }
            }

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothConnectProgressDialog.dismiss();
            Toast.makeText(RincianTransaksi.this, "Berhasil", Toast.LENGTH_SHORT).show();
            if (sharedPrefManager.getSPBtoff() && mBluetoothAdapter != null)
            mBluetoothAdapter.disable();
        }
    };




    public  void setClipboard(Context context, String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("" +
                    " telah dicopy", text);
            clipboard.setPrimaryClip(clip);
        }
    }


    public void lihatrincian() {
        shimmer.startShimmer();
        shimmer.setVisibility(VISIBLE);
        detail.setVisibility(View.GONE);
        swLayout.setRefreshing(false);
        Intent intent = getIntent();
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        String transaction_kode = intent.getStringExtra("transaction_kode");
        sharedPrefManager = new SharedPrefManager(this);
        String api_key = sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.transaksidetail("Bearer "+sharedPrefManager.getSPBearer(),transaction_kode,randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                shimmer.stopShimmer();
                shimmer.setVisibility(View.GONE);
                if (response.body() != null && response.body().getStatus() != null) {
                    if (response.body().getStatus()==1) {
                        Data data = response.body().getData();
                        String transaction_id = data.getId().toString();
                        getSupportActionBar().setTitle("Transaksi #" + transaction_id);
                        long waktu = data.getWaktu();
                        long waktu_bayar = data.getWaktuBayar();
                        long waktu_sukses = data.getWaktuSukses();
                        String hp = data.getHp();
                        Produkdetail produkdetail = data.getProdukdetail();
                        Orderdetail orderdetail = data.getOrderdetail();
                        String produk = produkdetail.getNama();
                        String meter = data.getMeter();
                        String nama = "";
                        String jumlahbulan = "";
                        String jumlahorang = "";
                        String bulan = "";
                        if (orderdetail != null) {
                            if (orderdetail.getNama() != null)
                                nama = orderdetail.getNama();
                            else
                                nama = "";
                            if (orderdetail.getJumlahbulan() != null)
                                jumlahbulan = orderdetail.getJumlahbulan();
                            else
                                jumlahbulan = "";
                            if (orderdetail.getJumlahorang() != null)
                                jumlahorang = orderdetail.getJumlahorang();
                            else
                                jumlahorang = "";
                            if (orderdetail.getBulan() != null)
                                bulan = orderdetail.getBulan();
                            else
                                bulan = "";


                        }
                        Integer statusorder = data.getStatus();
                        String sn = data.getSN();



                            Texthp.setText(hp);

                            Textproduk.setText(produk);
                            Textwaktu.setText(Enkripsi.getDate(waktu));
                            Textwaktubayar.setText(Enkripsi.getDate(waktu_bayar));
                            Textwaktusukses.setText(Enkripsi.getDate(waktu_sukses));
                            if (statusorder.equals(3)) {
                                progress.setVisibility(View.GONE);
                                progress2.setVisibility(View.GONE);
                                sukses.setVisibility(VISIBLE);
                                proses.setVisibility(View.GONE);
                                gagal.setVisibility(View.GONE);
                                print.setVisibility(VISIBLE);
                                kirimwa.setVisibility(VISIBLE);
                                Linearwaktusukses.setVisibility(VISIBLE);
                                bantuan.setVisibility(VISIBLE);
                            }
                            if (statusorder.equals(2)) {
                                progress.setVisibility(VISIBLE);
                                progress2.setVisibility(View.VISIBLE);
                                proses.setVisibility(VISIBLE);
                                sukses.setVisibility(View.GONE);
                                gagal.setVisibility(View.GONE);
                                print.setVisibility(View.GONE);
                                kirimwa.setVisibility(View.GONE);
                                Linearwaktusukses.setVisibility(View.GONE);

                                long now = System.currentTimeMillis() / 1000;
                                int detik = ((int) waktu_bayar + 600) - (int) now;
                                if (detik > 1) {
                                    mCountDownTimer = new CountDownTimer(detik * 1000, 1000) {

                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            int detak = (int) millisUntilFinished / 1000;
                                            progress.setProgress((600 - detak) * 100 / 600);
                                            int menit = (detak) / 60;
                                            int detiks = (detak) - (menit * 60);
                                            String menits;
                                            String detiks2;
                                            if (menit < 10)
                                                menits = "0" + Integer.toString(menit);
                                            else
                                                menits = Integer.toString(menit);
                                            if (detiks < 10)
                                                detiks2 = "0" + Integer.toString(detiks);
                                            else
                                                detiks2 = Integer.toString(detiks);
                                            proses.setText("Sedang diproses, estimasi waktu : " + menits + ":" + detiks2);

                                        }

                                        @Override
                                        public void onFinish() {
                                            //Do what you want
                                            proses.setText("Order Anda sedang diproses, apabila saat ini pesanan belum masuk Anda dapat menghubungi kami");
                                            progress.setProgress(100);
                                            bantuan.setVisibility(VISIBLE);
                                            progress.setVisibility(View.GONE);
                                        }
                                    };
                                    mCountDownTimer.start();
                                } else {
                                    proses.setText("Order Anda sedang diproses, apabila saat ini pesanan belum masuk Anda dapat menghubungi kami");
                                    progress.setVisibility(View.GONE);
                                    progress2.setVisibility(View.GONE);
                                    bantuan.setVisibility(VISIBLE);
                                }

                            }
                            if (statusorder.equals(4)) {
                                progress.setVisibility(View.GONE);
                                progress2.setVisibility(View.GONE);
                                proses.setVisibility(View.GONE);
                                sukses.setVisibility(View.GONE);
                                gagal.setVisibility(VISIBLE);
                                print.setVisibility(View.GONE);
                                kirimwa.setVisibility(View.GONE);
                                bantuan.setVisibility(VISIBLE);
                            }
                            if (statusorder.equals(6)) {
                                progress.setVisibility(View.GONE);
                                progress2.setVisibility(View.GONE);
                                proses.setVisibility(View.GONE);
                                sukses.setVisibility(View.GONE);
                                gagal.setVisibility(VISIBLE);
                                gagal.setText(R.string.transaksi_refund);
                                print.setVisibility(View.GONE);
                                kirimwa.setVisibility(View.GONE);
                            }

                            Texttransaction_id.setText(transaction_id);
                            TextMeter.setText(meter);
                            if (meter != null && meter.length() > 5)
                                LinearMeter.setVisibility(VISIBLE);

                            TextNama.setText(nama);
                            if (nama != null && nama.length() > 0)
                                LinearNama.setVisibility(VISIBLE);
                            TextJumlahBulan.setText(jumlahbulan);
                            if (jumlahbulan != null && jumlahbulan.length() > 0)
                                LinearJumlahBulan.setVisibility(VISIBLE);
                            TextSN.setText(sn);
                            if (sn != null)
                                LinearSN.setVisibility(VISIBLE);

                            TextJumlahOrang.setText(jumlahorang);
                            if (jumlahorang != null && jumlahorang.length() > 0)
                                LinearJumlahOrang.setVisibility(VISIBLE);
                            TextBulan.setText(bulan);
                            if (bulan != null && bulan.length() > 0)
                                LinearBulan.setVisibility(VISIBLE);
                            detail.setVisibility(VISIBLE);

                        }
                    if (response.body().getStatus()==0) {
                            Toast.makeText(RincianTransaksi.this,  response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        }
                    }
                else {
                    Toast.makeText(RincianTransaksi.this, R.string.error500, Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(RincianTransaksi.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void refreshrincian() {
        Intent intent = getIntent();
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String SHA1 = Enkripsi.access_key(randomUUIDString);

        String transaction_kode = intent.getStringExtra("transaction_kode");

        sharedPrefManager = new SharedPrefManager(this);
        String api_key = sharedPrefManager.getSPApikey();
        String api_secret =Enkripsi.api_secret(api_key,sharedPrefManager.getSPUsername());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Data> call = api.transaksidetail("Bearer "+sharedPrefManager.getSPBearer(),transaction_kode,randomUUIDString, SHA1, api_key, api_secret);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                if (response.body() != null && response.body().getStatus() != null) {

                    if (response.body().getStatus()==1) {
                    Data data = response.body().getData();
                    String transaction_id = data.getId().toString();
                    long waktu = data.getWaktu();
                    long waktu_bayar = data.getWaktuBayar();
                    long waktu_sukses = data.getWaktuSukses();
                    String hp = data.getHp();
                    Produkdetail produkdetail = data.getProdukdetail();
                    Orderdetail orderdetail = data.getOrderdetail();
                    String produk = produkdetail.getNama();
                    String meter = data.getMeter();
                    Integer statusorder = data.getStatus();
                    String nama = "";
                    String jumlahbulan = "";
                    String jumlahorang = "";
                    String bulan = "";
                    if (orderdetail != null) {
                        if (orderdetail.getNama() != null)
                            nama = orderdetail.getNama();
                        else
                            nama = "";
                        if (orderdetail.getJumlahbulan() != null)
                            jumlahbulan = orderdetail.getJumlahbulan();
                        else
                            jumlahbulan = "";
                        if (orderdetail.getJumlahorang() != null)
                            jumlahorang = orderdetail.getJumlahorang();
                        else
                            jumlahorang = "";
                        if (orderdetail.getBulan() != null)
                            bulan = orderdetail.getBulan();
                        else
                            bulan = "";


                    }

                    String sn = data.getSN();



                        Texthp.setText(hp);

                        Textproduk.setText(produk);
                        Textwaktu.setText(Enkripsi.getDate(waktu));
                        Textwaktubayar.setText(Enkripsi.getDate(waktu_bayar));
                        Textwaktusukses.setText(Enkripsi.getDate(waktu_sukses));
                        if (statusorder.equals(3)) {
                            handler.removeCallbacks(runnable);
                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                            sukses.setVisibility(VISIBLE);
                            proses.setVisibility(View.GONE);
                            gagal.setVisibility(View.GONE);
                            print.setVisibility(VISIBLE);
                            kirimwa.setVisibility(VISIBLE);
                            Linearwaktusukses.setVisibility(VISIBLE);
                            bantuan.setVisibility(VISIBLE);
                        }
                        if (statusorder.equals(2)) {
                            progress.setVisibility(VISIBLE);
                            progress2.setVisibility(View.VISIBLE);
                            proses.setVisibility(VISIBLE);
                            sukses.setVisibility(View.GONE);
                            gagal.setVisibility(View.GONE);
                            print.setVisibility(View.GONE);
                            kirimwa.setVisibility(View.GONE);
                            Linearwaktusukses.setVisibility(View.GONE);


                        }
                        if (statusorder.equals(4)) {
                            handler.removeCallbacks(runnable);
                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                            proses.setVisibility(View.GONE);
                            sukses.setVisibility(View.GONE);
                            gagal.setVisibility(VISIBLE);
                            print.setVisibility(View.GONE);
                            kirimwa.setVisibility(View.GONE);
                            bantuan.setVisibility(VISIBLE);
                        }
                        if (statusorder.equals(6)) {
                            handler.removeCallbacks(runnable);
                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                            proses.setVisibility(View.GONE);
                            sukses.setVisibility(View.GONE);
                            gagal.setVisibility(VISIBLE);
                            gagal.setText(R.string.transaksi_refund);
                            print.setVisibility(View.GONE);
                            kirimwa.setVisibility(View.GONE);
                        }

                        Texttransaction_id.setText(transaction_id);
                        TextMeter.setText(meter);
                        if (meter != null && meter.length() > 5)
                            LinearMeter.setVisibility(VISIBLE);

                        TextNama.setText(nama);
                        if (nama != null && nama.length() > 0)
                            LinearNama.setVisibility(VISIBLE);
                        TextJumlahBulan.setText(jumlahbulan);
                        if (jumlahbulan != null && jumlahbulan.length() > 0)
                            LinearJumlahBulan.setVisibility(VISIBLE);
                        TextSN.setText(sn);
                        if (sn != null)
                            LinearSN.setVisibility(VISIBLE);

                        TextJumlahOrang.setText(jumlahorang);
                        if (jumlahorang != null && jumlahorang.length() > 0)
                            LinearJumlahOrang.setVisibility(VISIBLE);
                        TextBulan.setText(bulan);
                        if (bulan != null && bulan.length() > 0)
                            LinearBulan.setVisibility(VISIBLE);
                        detail.setVisibility(VISIBLE);
                        shimmer.stopShimmer();
                        shimmer.setVisibility(View.GONE);
                    }
                    if (response.body().getStatus()==0) {
                        Toast.makeText(RincianTransaksi.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RincianTransaksi.this, R.string.error500, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(RincianTransaksi.this, "Gangguan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
        swLayout.setRefreshing(false);
    }

private void printing() {
    try {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(RincianTransaksi.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CONNECT_DEVICE);
                return;
            }
        }
        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
        mBluetoothSocket.connect();
        mHandler.sendEmptyMessage(0);
        String keseluruhan = jadikansatu();
        String waktu = Textwaktu.getText().toString();

        OutputStream os = mBluetoothSocket.getOutputStream();
        String BILL = "";
        String judul = "";

        BILL = "        " + waktu + "        \n================================\n" + keseluruhan;
        byte[] center = {0x1b, 'a', 0x01};
        os.write(center);
        byte[] format = {27, 33, 0};

        format[2] = ((byte) (0x10 | format[2]));
        os.write(format);
        os.write(judul.getBytes(), 0, judul.getBytes().length);


        // Setting height
        int gs = 29;
        os.write(intToByteArray(gs));
        int h = 104;
        os.write(intToByteArray(h));
        int n = 162;
        os.write(intToByteArray(n));

        // Setting Width
        int gs_width = 29;
        os.write(intToByteArray(gs_width));
        int w = 119;
        os.write(intToByteArray(w));
        int n_width = 2;
        os.write(intToByteArray(n_width));


        OutputStream outputStream = mBluetoothSocket.getOutputStream();

        byte[] format3 = new byte[]{27, 33, 0};
        //byte[] change = new byte[]{27,33, 0};

        outputStream.write(format3);

        outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
        outputStream.write(BILL.getBytes());
        outputStream.write(PrinterCommands.LF);

        String pesanpenjual2 = "          Terima kasih          \n\n\n";
        OutputStream otput = mBluetoothSocket.getOutputStream();
        otput.write(PrinterCommands.ESC_ALIGN_CENTER);
        otput.write(pesanpenjual2.getBytes());
        otput.write(PrinterCommands.LF);


        mBluetoothSocket.close();
    } catch (Exception e) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.e("RincianTransaksi", "Exe ", e);
        mBluetoothConnectProgressDialog.dismiss();
        if (sharedPrefManager.getSPBtoff() && mBluetoothAdapter != null)
            mBluetoothAdapter.disable();

    }

}

}
