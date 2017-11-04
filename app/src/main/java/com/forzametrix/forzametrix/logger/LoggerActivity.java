package com.forzametrix.forzametrix.logger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.data.DataRecorderContract;
import com.forzametrix.forzametrix.util.ActivityUtils;
import com.forzametrix.forzametrix.R;




import com.forzametrix.forzametrix.R;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerActivity extends AppCompatActivity implements LoggerContract.View {

  //  private LoggerContract.Presenter mPresenter;

  //  private NumberPicker ones;
  //  private NumberPicker tens;
  //  private NumberPicker hundreds;
  //  private ProgressBar forceBar;
  //  private Switch record;
  //  private TextView forceNum;
  //  private DataRecorder mDataRecorder;

  //  private int plusOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_activity);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.placeholder,new LoggerFragment());
        ft.commit();



    }



    public void setPresenter(@NonNull LoggerContract.Presenter presenter){
      //  mPresenter = checkNotNull(presenter);
    }


    public void updateForce(int force){


    }

}
