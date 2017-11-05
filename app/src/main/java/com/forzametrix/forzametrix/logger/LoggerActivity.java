package com.forzametrix.forzametrix.logger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;

import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.data.DataRecorderContract;
import com.forzametrix.forzametrix.util.ActivityUtils;
import com.forzametrix.forzametrix.R;




import com.forzametrix.forzametrix.R;

import javax.inject.Inject;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerActivity extends AppCompatActivity{

  //  LoggerPresenter mLoggerPresenter;
  //  DataRecorder mDataRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_activity);

        //adding the fragment to the container by swapping out a placeholder
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoggerFragment lf = new LoggerFragment();
        ft.replace(R.id.placeholder,lf);

   //     mDataRecorder = new DataRecorder();

    //    mLoggerPresenter = new LoggerPresenter(mDataRecorder ,lf);

    }


}
