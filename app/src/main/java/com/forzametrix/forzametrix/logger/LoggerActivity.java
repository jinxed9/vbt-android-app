package com.forzametrix.forzametrix.logger;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.data.DataRecorderContract;
import com.forzametrix.forzametrix.util.ActivityUtils;
import com.forzametrix.forzametrix.R;




import com.forzametrix.forzametrix.R;

import javax.inject.Inject;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerActivity extends AppCompatActivity{

    LoggerPresenter mLoggerPresenter;
    DataRecorder mDataRecorder;
    SensorManager mSensorManager;
    Sensor mAccelSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_activity);
        int MY_PERMISSION_REQUEST_EXTERNAL_WRITE = 0;

        //adding the fragment to the container by swapping out a placeholder
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoggerFragment lf = new LoggerFragment();
        ft.replace(R.id.placeholder,lf);
        ft.commit();

        //permission check. this needs to be implemented better
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permissionCheck == PERMISSION_DENIED){
            //request permissions
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSION_REQUEST_EXTERNAL_WRITE);
        }

        mDataRecorder = new DataRecorder();

        mLoggerPresenter = new LoggerPresenter(mDataRecorder ,lf);

    }


}
