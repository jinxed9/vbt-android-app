package com.forzametrix.forzametrix.pager;

import android.Manifest;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.data.RepsDatabase;
import com.forzametrix.forzametrix.logger.LoggerFragment;
import com.forzametrix.forzametrix.logger.LoggerPresenter;
import com.forzametrix.forzametrix.summary.SummaryContract;
import com.forzametrix.forzametrix.summary.SummaryFragment;
import com.forzametrix.forzametrix.summary.SummaryPresenter;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

public class FragmentPagerActivity extends AppCompatActivity {
    static final int NUM_ITEMS = 2;
    Adapter mAdapter;
    ViewPager mPager;
    static RepsDatabase mDatabase;
    static LoggerFragment lf;
    static LoggerPresenter mLoggerPresenter;
    static DataRecorder mDataRecorder;

    //----------------------
    static SummaryFragment sf;
    static SummaryPresenter mSummaryPresenter;




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

        //logger MVP stuff
        lf = new LoggerFragment();
        mDataRecorder = new DataRecorder();
        mLoggerPresenter = new LoggerPresenter(mDataRecorder ,lf);

        //setup the main pager
        mAdapter = new Adapter(getSupportFragmentManager());
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);


        //summary MVP stuff
        sf = new SummaryFragment();
        mDatabase = new RepsDatabase(getApplicationContext());
        mSummaryPresenter = new SummaryPresenter(mDatabase,sf);


        mDatabase.create(1,2,"30-20-2017",14,4.322,135,"bench press");
        int set = mDatabase.readSet(1);
        String date = mDatabase.readDate(1);
        int rep = mDatabase.readReps(1);
        float vel = mDatabase.readVelocity(1);
        int weight = mDatabase.readWeight(1);
        String type = mDatabase.readType(1);

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
            return sf;
        }
    }
}