package com.forzametrix.forzametrix.logger;

import android.content.Context;
import android.drm.DrmManagerClient;
import android.hardware.SensorEvent;
import android.media.MediaDrm;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import com.forzametrix.forzametrix.R;

import com.forzametrix.forzametrix.data.DataRecorder;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerFragment extends Fragment implements LoggerContract.View,SensorEventListener {

    LoggerContract.Presenter mPresenter;
    NumberPicker ones;
    NumberPicker tens;
    NumberPicker hundreds;
    Switch record;
    ProgressBar forceBar;
    SensorManager mSensorManager;
    Sensor mAccelSensor;


    public LoggerFragment(){

    }

    public static LoggerFragment newInstance(){
        return new LoggerFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.logger_fragment, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){


        tens = (NumberPicker) view.findViewById(R.id.numberPicker_tens);
        ones = (NumberPicker) view.findViewById(R.id.numberPicker_ones);
        hundreds = (NumberPicker) view.findViewById(R.id.numberPicker_hundreds);
        record = (Switch)view.findViewById(R.id.switch_record);
        forceBar = (ProgressBar)view.findViewById(R.id.progressBar_force);

        forceBar.setMax(100);
        forceBar.setProgress(50);

        ones.setMinValue(0);
        tens.setMinValue(0);
        hundreds.setMinValue(0);
        ones.setMaxValue(9);
        tens.setMaxValue(9);
        hundreds.setMaxValue(9);
        ones.setWrapSelectorWheel(true);
        tens.setWrapSelectorWheel(true);
        hundreds.setWrapSelectorWheel(true);


        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        //get a reference to the accelerometer
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            int delay = mAccelSensor.getMinDelay();
        } else {
            //No accelerometers are available;
        }


        record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    //The toggle is enabled
                    mPresenter.beginRecording();
                    start();
                }else{
                    //the toggle is not enabled
                    mPresenter.endRecording();
                    stop();
                }
            }
        });

    }

    public void setPresenter(@NonNull LoggerContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }


    public void updateForce(int force){
        forceBar.setProgress(force);
    }

    public void updateForce(float force){
        int forceInt = (int)(force * 10);
        forceBar.setProgress(forceInt);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        //Do something here.
        Log.v("Accel:","Accuracy Changed!");
    }

    //move this to the presenter in the future
    @Override
    public final void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        Log.v("Accel:","X:"+event.values[0]);

        updateForce(event.values[0]);
    }


    public boolean stop(){
        Log.v("Model:","Recording Stopped");
        mSensorManager.unregisterListener(this);
        return true;
    }


    public boolean start(){
        Log.v("Model:","Recording Started");
        mSensorManager.registerListener(this,checkNotNull(mAccelSensor),mSensorManager.SENSOR_DELAY_NORMAL);
        return true;
    }



}
