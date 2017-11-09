package com.forzametrix.forzametrix.pager;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.logger.LoggerFragment;
import com.forzametrix.forzametrix.logger.LoggerPresenter;
import com.forzametrix.forzametrix.velocity.VelocityFragment;

import java.util.logging.Logger;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class FragmentPagerActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 2;
    Adapter mAdapter;
    ViewPager mPager;
    static LoggerFragment lf;
    static LoggerPresenter mLoggerPresenter;
    static DataRecorder mDataRecorder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pager);

        int MY_PERMISSION_REQUEST_EXTERNAL_WRITE = 0;

        //permission check. this needs to be implemented better
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PERMISSION_DENIED){
            //request permissions
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_EXTERNAL_WRITE);

        }

        lf = new LoggerFragment();
        mDataRecorder = new DataRecorder();
        mLoggerPresenter = new LoggerPresenter(mDataRecorder ,lf);
        mAdapter = new Adapter(getSupportFragmentManager());

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

    }


    public static class Adapter extends FragmentPagerAdapter {
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0)
                return lf;
            return new VelocityFragment();
        }
    }
}