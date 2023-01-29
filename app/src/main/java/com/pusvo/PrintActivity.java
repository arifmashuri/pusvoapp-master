package com.pusvo;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class PrintActivity extends Activity implements Runnable {
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;
    SharedPrefManager sharedPrefManager;
    TextView btdevice;
    @Override
    public void onCreate(Bundle mSavedInstanceState) {
        super.onCreate(mSavedInstanceState);
        setContentView(R.layout.activity_print);
        mScan = (Button) findViewById(R.id.Scan);
        btdevice = (TextView) findViewById(R.id.btdevice);
        sharedPrefManager = new SharedPrefManager(this);
        String devad=sharedPrefManager.getSPBtdevice();
        btdevice.setText(devad);
        mScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(PrintActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(PrintActivity.this,
                                DeviceListActivity.class);
                        startActivityForResult(connectIntent,
                                REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });

        mPrint = (Button) findViewById(R.id.mPrint);
        mPrint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(PrintActivity.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    }
                    else {

                        String mDeviceAddress=sharedPrefManager.getSPBtdevice();
                        String tester="tester" + mDeviceAddress;
                        btdevice.setText(tester);
                        Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                        mBluetoothConnectProgressDialog = ProgressDialog.show(PrintActivity.this,
                                "Sedang mencetak mprint...", mBluetoothDevice.getName(), true, true);
                        Thread mBlutoothConnectThread = new Thread();
                        try {
                            mBlutoothConnectThread.start();
                        }
                        finally {
                            Thread t = new Thread() {
                                public void run() {
                                    try {
                                        mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                                        mBluetoothAdapter.cancelDiscovery();
                                        mBluetoothSocket.connect();
                                        mHandler.sendEmptyMessage(0);
                                        String sn="tester";
                                        OutputStream os = mBluetoothSocket.getOutputStream();
                                        String BILL = "";

                                        BILL = "      HURI RESEARCH CENTER      \n";
                                        BILL = BILL + "\n" + String.format("%1$-10s %2$10s", "Keterangan",": "+ sn);
                                        BILL = BILL + "\n";
                                        os.write(BILL.getBytes());
                                        //This is printer specific code you can comment ==== > Start

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

                                        mBluetoothSocket.close();
                                    } catch (Exception e) {
                                        Log.e("PrintActivity", "Exe ", e);
                                    }
                                }
                            };
                            t.start();
                        }
                    }

                }



            }
        });

        mDisc = (Button) findViewById(R.id.dis);
        mDisc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View mView) {
                if (mBluetoothAdapter != null)
                    mBluetoothAdapter.disable();
            }
        });

    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (mBluetoothSocket != null)
                mBluetoothSocket.close();
        } catch (Exception e) {
            Log.e("Tag", "Exe ", e);
        }
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (mResultCode == Activity.RESULT_OK) {
                    //Bundle mExtra = mDataIntent.getExtras();
                    //String mDeviceAddress = mExtra.getString("DeviceAddress");
                    String mDeviceAddress=sharedPrefManager.getSPBtdevice();
                    btdevice.setText(mDeviceAddress);
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
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

                    String mDeviceAddress=sharedPrefManager.getSPBtdevice();
                    String tester="tester" + mDeviceAddress;
                    btdevice.setText(tester);
                    Log.v(TAG, "Coming incoming address " + mDeviceAddress);
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    mBluetoothConnectProgressDialog = ProgressDialog.show(PrintActivity.this,
                            "Sedang mencetak bt enable...", mBluetoothDevice.getName(), true, true);
                    Thread mBlutoothConnectThread = new Thread();
                    try {
                        mBlutoothConnectThread.start();
                    }
                    finally {
                        Thread t = new Thread() {
                            public void run() {
                                try {
                                    mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(applicationUUID);
                                    mBluetoothAdapter.cancelDiscovery();
                                    mBluetoothSocket.connect();
                                    mHandler.sendEmptyMessage(0);
                                    String sn="tester";
                                    OutputStream os = mBluetoothSocket.getOutputStream();
                                    String BILL = "";

                                    BILL = "      HURI RESEARCH CENTER      \n";
//                                    + "testing tes njajal gan\n" +
//                                    "       24/02/2019 15:41:02      \n";
//                            BILL = BILL + "--------------------------------\n";


//                            BILL = BILL + String.format("%1$-10s %2$10s", "Item", "Qty");
//                            BILL = BILL + "\n";
//                            BILL = BILL + "--------------------------------";
//                            BILL = BILL + "\n" + String.format("%1$-10s %2$10s", "item-001", "5");
//                            BILL = BILL + "\n" + String.format("%1$-10s %2$10s", "No HP", ": 089631018409");
                                    BILL = BILL + "\n" + String.format("%1$-10s %2$10s", "Keterangan",": "+ sn);
//                            BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-002", "10", "5", "50.00");
//                            BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-003", "20", "10", "200.00");
//                            BILL = BILL + "\n " + String.format("%1$-10s %2$10s %3$11s %4$10s", "item-004", "50", "10", "500.00");

                                    BILL = BILL + "\n";
//                            BILL = BILL + "\n\n ";

//                            BILL = BILL + "                   Total Qty:" + "      " + "85" + "\n";
//                            BILL = BILL + "                   Total Value:" + "     " + "700.00" + "\n";

//                            BILL = BILL
//                                    + "-----------------------------------------------\n";
//                            BILL = BILL + "\n\n ";
                                    os.write(BILL.getBytes());
                                    //This is printer specific code you can comment ==== > Start

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

                                    mBluetoothSocket.close();
                                } catch (Exception e) {
                                    Log.e("PrintActivity", "Exe ", e);
                                }
                            }
                        };
                        t.start();
                    }



                } else {
                    Toast.makeText(PrintActivity.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
                .getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.v(TAG, "PairedDevices: " + mDevice.getName() + "  "
                        + mDevice.getAddress());
            }
        }
    }

    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket nOpenSocket) {
        try {
            nOpenSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mBluetoothConnectProgressDialog.dismiss();
            //    Toast.makeText(PrintActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
        }
    };

    public static byte intToByteArray(int value) {
        byte[] b = ByteBuffer.allocate(4).putInt(value).array();

        for (int k = 0; k < b.length; k++) {
            System.out.println("Selva  [" + k + "] = " + "0x"
                    + UnicodeFormatter.byteToHex(b[k]));
        }

        return b[3];
    }

}