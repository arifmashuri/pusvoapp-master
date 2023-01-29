package com.pusvo;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BantuanActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @BindView(R.id.halaman) TextView halaman;
    public static final String URL = "https://api540cb4bc94a51e48766cc69d8a646bf.pusvo.com";
    public static String SHA1(final String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("UTF-8"),
                    0, text.length());
            byte[] sha1hash = md.digest();

            return toHex(sha1hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHex(final byte[] buf) {
        if (buf == null) return "";

        int l = buf.length;
        StringBuffer result = new StringBuffer(2 * l);

        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789abcdef";

    private static void appendHex(final StringBuffer sb, final byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f))
                .append(HEX.charAt(b & 0x0f));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.halamanteks);
        ButterKnife.bind(this);
        halaman.setText(R.string.deskripsi_bantuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Bantuan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
    }


}