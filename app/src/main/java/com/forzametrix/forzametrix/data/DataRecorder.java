package com.forzametrix.forzametrix.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class DataRecorder implements DataRecorderContract.DataRecorder,SensorEventListener{

    private Sensor mAccelSensor;
    private Sensor mGyroSensor;
    private SensorManager mSensorManager;
    private Context mContext;


    void DataRecorder(SensorManager sensorManager){
        mSensorManager = sensorManager;
    }

    protected void onCreate(Bundle savedInstanceState) {
       // mSensorManager = (SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
        mGyroSensor = null;

        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            mAccelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            int delay = mAccelSensor.getMinDelay();
        } else {
            //No accelerometers are available;
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            int delay = mGyroSensor.getMinDelay();
        }

    }


    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        Log.v("Accel:","X:"+event.values[0]);
    }

    public void onAccuracyChanged(Sensor sensor,int accuracy){

    }

    public boolean start(){
        Log.v("Model:","Recording Started");
        mSensorManager.registerListener(this,checkNotNull(mAccelSensor),mSensorManager.SENSOR_DELAY_NORMAL);
        return true;
    }

    public boolean stop(){
        Log.v("Model:","Recording Stopped");
        mSensorManager.unregisterListener(this);
        return true;
    }
}
