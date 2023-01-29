package com.pusvo;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import butterknife.OnClick;

public class LayarUtama extends AppCompatActivity {
    public void onBackPressed() {
        startActivity(new Intent(LayarUtama.this, MainActivity.class));
    };

    @OnClick(R.id.profile) void profile() {
        startActivity(new Intent(LayarUtama.this, AkunSayaActivity.class));
    };
    @OnClick(R.id.tutorial) void deposit() {
        startActivity(new Intent(LayarUtama.this, TutorialActivity.class));
    };
    @OnClick(R.id.riwayattransaksi) void riwayattransaksi() {
        //
    };
    @OnClick(R.id.daftarharga) void deposit2() {
        startActivity(new Intent(LayarUtama.this, CekHargaActivity.class));
    };
    @OnClick(R.id.home) void home() {
        startActivity(new Intent(LayarUtama.this, MainActivity.class));
    };

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utama);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 //      ActionBar actionBar = getSupportActionBar(toolbar);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Riwayat Transaksi");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.kembali);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), LayarUtama.this);
        viewPager.setAdapter(pagerAdapter);

     final   TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tabLayout.setEnabled(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        tabLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
        for(int i = 0; i < tabLayout.getTabCount(); i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

    }

    @Override
    public void onResume() { super.onResume();}

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

//        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);

    }

    class PagerAdapter extends FragmentPagerAdapter {

        String tabTitles[] = new String[]{"Semua","Pembayaran","Proses","Sukses", "Gagal"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new BlankFragmentBaru(0);
                case 1:
                    return new BlankFragmentBaru(1);
                case 2:
                    return new BlankFragmentBaru(2);
                case 3:
                    return new BlankFragmentBaru(3);
                case 4:
                    return new BlankFragmentBaru(4);
            }

            return null;
        }


    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

    public View getTabView(int position){
        View tab = LayoutInflater.from(LayarUtama.this).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
        tv.setText(tabTitles[position]);
        return tab;
    }
}
}