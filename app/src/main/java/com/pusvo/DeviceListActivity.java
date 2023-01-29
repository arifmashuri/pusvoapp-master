package com.pusvo;


import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DeviceListActivity extends AppCompatActivity {
    protected static final String TAG = "TAG";
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    SharedPrefManager sharedPrefManager;
    private Toolbar toolbar;
    Context context=this;

    public void onBackPressed() {
        startActivity(new Intent(DeviceListActivity.this, AkunSayaActivity.class));
    }

    @Override
    protected void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.device_list);
        ButterKnife.bind(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("Pengaturan Printer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);


        sharedPrefManager = new SharedPrefManager(this);
        setResult(Activity.RESULT_CANCELED);
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        ListView mPairedListView = (ListView) findViewById(R.id.paired_devices);
        mPairedListView.setAdapter(mPairedDevicesArrayAdapter);
        mPairedListView.setOnItemClickListener(mDeviceClickListener);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                ActivityCompat.requestPermissions(DeviceListActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();

        if (mPairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice mDevice : mPairedDevices) {
                mPairedDevicesArrayAdapter.add(mDevice.getName() + "\n" + mDevice.getAddress());
            }
        } else {
            String mNoDevices = "Belum ada perangkat bluetooth yang disambungkan";//getResources().getText(R.string.none_paired).toString();
            mPairedDevicesArrayAdapter.add(mNoDevices);
            mBluetoothAdapter.startDiscovery();
        }

        Switch switchbt = (Switch) findViewById(R.id.switchbt);
        switchbt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.v("Switch State=", ""+isChecked);
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_BT_OFF, isChecked);
//                Toast.makeText(DeviceListActivity.this, "Switch berubah"+isChecked, Toast.LENGTH_SHORT).show();
            }

        });
        if (sharedPrefManager.getSPBtoff())
            switchbt.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(DeviceListActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);
                    return;
                }
            }
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> mAdapterView, View mView, int mPosition, long mLong) {


            String mDeviceInfo = ((TextView) mView).getText().toString();
            String mDeviceAddress = mDeviceInfo.substring(mDeviceInfo.length() - 17);
            String devicename=mDeviceInfo.substring(0,(mDeviceInfo.length()-mDeviceAddress.length())-1);

            //Bundle mBundle = new Bundle();
            sharedPrefManager.saveSPString(SharedPrefManager.SP_BTDEVICE, mDeviceAddress);
            sharedPrefManager.saveSPString(SharedPrefManager.SP_BTNAME, devicename);
            //    mBundle.putString("DeviceAddress", mDeviceAddress);
            Intent mBackIntent = new Intent();
            //mBackIntent.putExtras(mBundle);
            setResult(Activity.RESULT_OK, mBackIntent);
            finish();

        }
    };



}