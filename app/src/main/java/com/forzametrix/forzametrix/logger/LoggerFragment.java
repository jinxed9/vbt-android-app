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

public class LoggerFragment extends Fragment implements LoggerContract.View{

    LoggerContract.Presenter mPresenter;
    NumberPicker ones;
    NumberPicker tens;
    NumberPicker hundreds;
    Switch record;
    ProgressBar forceBar;
    SensorManager mSensorManager;
    Sensor mAccelSensor;
    Sensor mGyroSensor;
    Sensor mGravitySensor;
    TextView forceView,repsView,velocityView,setView;


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
        //forceView = (TextView)view.findViewById(R.id.textView_force);
        velocityView = (TextView)view.findViewById(R.id.textView_velocityActual);
        repsView = (TextView)view.findViewById(R.id.textView_repsActual);
        setView = (TextView)view.findViewById(R.id.textView_set);


        forceBar.setMax(100);
        forceBar.setProgress(0);

        setView.setText("- - -");
        mPresenter.getLastSet();

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
            Log.v("View:","No accelerometers available");
            //No accelerometers are available;
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        } else {
            Log.v("View:","No gyroscope available");
            //No gyros available;
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            mGravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        } else{
            Log.v("View:","No gravity sensor available");
            //No gravity sensors availble;
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

    public void updateRep(String rep){
        repsView.setText(rep);
    }

    public void updateSet(String set){ setView.setText(set);}

    public void updateVelocity(String velocity){
        velocityView.setText(velocity);
    }

    private void stop(){
        Log.v("Model:","Recording Stopped");
        mSensorManager.unregisterListener(mPresenter);
    }


    private void start(){
        Log.v("Model:","Recording Started");
        mSensorManager.registerListener(mPresenter,checkNotNull(mAccelSensor),mSensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(mPresenter,checkNotNull(mGravitySensor),mSensorManager.SENSOR_DELAY_FASTEST);
    }


    public int getWeight(){
       int one = ones.getValue();
       int ten = tens.getValue() *10;
       int hundred = hundreds.getValue() * 100;

       return hundred + ten + one;
    }


}
