package com.forzametrix.forzametrix;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Sensor mAccelSensor;
    private Sensor mGyroSensor;
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelSensor = null;
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
}
