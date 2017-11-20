package com.forzametrix.forzametrix.logger;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
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
import com.forzametrix.forzametrix.data.RepsDatabaseContract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.support.v4.util.Preconditions.checkNotNull;
import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerPresenter implements LoggerContract.Presenter {

    private float a,prevOutput;
    private float accelX,accelY,accelZ;
    private float gravX,gravY,gravZ;
    private String gravData, accelData,data;
    private long startTime;
    private boolean logging;
    private int rep,set,weight;
    private float cumVelocity,prevAccelMagnitude,cumAccelMagnitude;
    int samples;
    private String fileName,date;
    boolean accelReady;

    private final RepsDatabaseContract.Database mRepsDatabase;

    private final DataRecorderContract.DataRecorder mDataRecorder;

    private final LoggerContract.View mLoggerView;

    public LoggerPresenter(@NonNull DataRecorderContract.DataRecorder dataRecorder, @NonNull RepsDatabaseContract.Database database, @NonNull LoggerContract.View loggerView){
        mDataRecorder = checkNotNull(dataRecorder,"dataRecorder cannot be null");
        mRepsDatabase = checkNotNull(database,"database cannot be null");
        mLoggerView = checkNotNull(loggerView,"loggerView cannot be null");

        mLoggerView.setPresenter(this);
        prevOutput = 1;
        a = (float)0.043;
        logging = false;
        accelReady = false;
        rep = 0;
        set = 0;

        cumVelocity = 0.0f;
        prevAccelMagnitude = 0.0f;
        cumAccelMagnitude = 0.0f;
        samples = 0;
        fileName = "";
        date = "";



    }


    public final void onSensorChanged(SensorEvent event){
       // Log.v("Presenter:", "OnSensorChanged");
        long currentTime = System.nanoTime() - startTime;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //update with the latest accel data;
            accelX = event.values[0];
            accelY = event.values[1];
            accelZ = event.values[2];
            accelData = event.values[0] + "," + event.values[1] + "," + event.values[2];
            accelReady = true;
        }
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            //update with the latest grav data;
            gravX = event.values[0];
            gravY = event.values[1];
            gravZ = event.values[2];
            gravData = event.values[0] + "," + event.values[1] + "," + event.values[2];
        }




        //calculate the dot product;
        float dp = accelX*gravX + accelY*gravY + accelZ*gravZ;

        //low pass filter the dot product
        //y[k] = a * (x[k] - y[k-1]) + y[k-1]
        //a = 1/T where T is the time constant

        float output = a *((dp -  prevOutput) + prevOutput);

        mLoggerView.updateForce((int)(output*10));

        //data = data + currentTime + "," + dp + "\n";
        //

        if(output >= 5.00f && !logging){
            //Beginning of the data collection
            startTime = System.nanoTime();
            data = "";
            date = getDateString();
            fileName = getCurrentTimeString();
            //----------------------------------
            logging = true;
            prevAccelMagnitude = 0.0f;
            cumVelocity = 0.0f;
            cumAccelMagnitude = 0.0f;
            samples = 0;
            data = data + "-------------------------\n";
        }

        if(logging && output < 2.00f){
            //End of the data collection
            logging = false;
            rep++;
            float averageVelocity = cumVelocity/samples;
            data = data + "------- " + rep + ":" + averageVelocity + " -------\n";
            //update the view
            mLoggerView.updateRep(Integer.toString(rep));
            mLoggerView.updateVelocity(String.format("%.4f",averageVelocity));
            //write to file here
            mDataRecorder.writeToFile(data,fileName);
            //store the calculated metrics in the database here.
            mRepsDatabase.create(Long.parseLong(fileName),set,date,rep,averageVelocity,weight,"bench press");

            //reset stuff
            data = "";
            fileName = "";

        }
        //compile the string
        //data = data + currentTime + "," + accelData + "," + gravData + "\n";

        if(logging && accelReady) {
            //magnitude of the acceleration vector
            double magnitude = Math.sqrt((accelX * accelX) + (accelY * accelY) + (accelZ * accelZ));

            //rough velocity calculation. this assumes that samples are equally spaced in time, which is not necessarily true
            cumAccelMagnitude += ((float)magnitude - prevAccelMagnitude)/2.0f;
            cumVelocity += cumAccelMagnitude;
            samples += 1;

            data += samples + "," + currentTime + "," + String.format("%.3f", output) + "," + String.format("%.7f",magnitude) + "," + String.format("%.7f",cumVelocity) + "\n";
            prevAccelMagnitude = (float)magnitude;
            accelReady = false;
        }
    }

    public final void onAccuracyChanged(Sensor sensor, int accuracy){
        Log.v("Presenter:", "OnAccuracyChanged");
    }

    public void beginRecording(){
        Log.v("Presenter:","Begin Recording.");
        set++;
        rep = 0;
        mLoggerView.updateSet(Integer.toString(set));
        weight = mLoggerView.getWeight();
    }

    public void endRecording(){
        Log.v("Presenter:","End Recording.");
    }


    private String getCurrentTimeString(){
        Calendar calendar = Calendar.getInstance();
        //Month is plus 1 because the months start at 0 instead of 1
        return "" + calendar.get(YEAR) +
                (calendar.get(MONTH)+1)+
                calendar.get(DATE) +
                calendar.get(HOUR_OF_DAY) +
                calendar.get(MINUTE) +
                calendar.get(SECOND) +
                calendar.get(MILLISECOND);
    }

    private String getDateString(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MMM dd, yyyy");
        return date.format(calendar.getTime());
    }

    public void getLastSet() {
        Cursor cursor = mRepsDatabase.selectReps(getDateString());
        if (cursor.moveToLast()) {
            set = cursor.getInt(cursor.getColumnIndex("setNum"));
            mLoggerView.updateSet(Integer.toString(set));
        }
    }
}
