package com.forzametrix.forzametrix.logger;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.os.SystemClock;
import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.data.DataRecorderContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Random;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerPresenter implements LoggerContract.Presenter {

    private String data;
    private long startTime;

    private final DataRecorderContract.DataRecorder mDataRecorder;

    private final LoggerContract.View mLoggerView;

    public LoggerPresenter(@NonNull DataRecorderContract.DataRecorder dataRecorder,@NonNull LoggerContract.View loggerView){
        mDataRecorder = checkNotNull(dataRecorder,"dataRecorder cannot be null");
        mLoggerView = checkNotNull(loggerView,"loggerView cannot be null");

        mLoggerView.setPresenter(this);
    }


    public final void onSensorChanged(SensorEvent event){
        Log.v("Presenter:", "OnSensorChanged");
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
       // Log.v("Accel:","X:"+event.values[0]);

        long currentTime = System.nanoTime() - startTime;
        data = data + currentTime + "," + event.values[0] + "," + event.values[1]  + ","+ event.values[2]  + "\n";

        int forceInt = (int)(event.values[0] * 10);

        mLoggerView.updateForce(forceInt);
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        Log.v("Presenter:", "OnAccuracyChanged");
    }

    public void beginRecording(){
        Log.v("Presenter:","Begin Recording.");
        //reset the data string to blank
        startTime = System.nanoTime();
        data = "";
    }

    public void endRecording(){
        Log.v("Presenter:","End Recording.");
        mDataRecorder.writeToFile(data);
        data = "";
    }
}
