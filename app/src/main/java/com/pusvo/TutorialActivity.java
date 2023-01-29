package com.pusvo;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialActivity extends AppCompatActivity {

    private Toolbar toolbar;
    //@BindView(R.id.halaman) TextView halaman;
    @BindView(R.id.webdata) WebView webview;
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
        setContentView(R.layout.halamanweb);
        ButterKnife.bind(this);
       // halaman.setText(R.string.deskripsi_bantuan);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Tutorial");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);
        String URLWEB = "https://pusvo.com/tutorial";
        webview.loadUrl(URLWEB);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webview.evaluateJavascript("enable();", null);
        } else {
            webview.loadUrl("javascript:enable();");
        }
    }


}