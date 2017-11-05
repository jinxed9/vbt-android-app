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
        Log.v("Accel:","X:"+event.values[0]);

        data = data + event.values[0] + ",\n";

        int forceInt = (int)(event.values[0] * 10);

        mLoggerView.updateForce(forceInt);
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        Log.v("Presenter:", "OnAccuracyChanged");
    }

    public boolean beginRecording(){
        Log.v("Presenter:","Begin Recording.");

        //reset the data string to blank
        data = "";
        return mDataRecorder.start();
    }

    public boolean endRecording(){
        Log.v("Presenter:","End Recording.");
        writeToFile(data);
        data = "";
        return mDataRecorder.stop();
    }


    public void writeToFile(String data){



        File dir = new File(Environment.getExternalStorageDirectory(),"forzametrix");
        boolean dirExists;
        if(!dir.exists()){
            dirExists = dir.mkdirs();
        }

        Random generator = new Random();
        int num = 10000;
        num = generator.nextInt(num);

      try {
        File file = new File(dir,"data" + num + ".txt");
        if(!file.exists()) {
            file.createNewFile();
        }



          FileOutputStream f = new FileOutputStream(file);
          f.write(data.getBytes());
          f.flush();
          f.close();

      }catch(IOException e){
          e.printStackTrace();
      }
    }


}
