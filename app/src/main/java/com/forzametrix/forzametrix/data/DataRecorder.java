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

public class DataRecorder implements DataRecorderContract.DataRecorder{

    public void DataRecorder(){
    }

    protected void onCreate(Bundle savedInstanceState) {


    }



    public boolean start(){
      Log.v("Model:","Recording Started");

        return true;
    }

    public boolean stop(){
      Log.v("Model:","Recording Stopped");

        return true;
    }
}
