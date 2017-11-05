package com.forzametrix.forzametrix.logger;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.forzametrix.forzametrix.R;
import com.forzametrix.forzametrix.data.DataRecorderContract;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerPresenter implements LoggerContract.Presenter {

    private final DataRecorderContract.DataRecorder mDataRecorder;

    private final LoggerContract.View mLoggerView;

    public LoggerPresenter(@NonNull DataRecorderContract.DataRecorder dataRecorder,@NonNull LoggerContract.View loggerView){
        mDataRecorder = checkNotNull(dataRecorder,"dataRecorder cannot be null");
        mLoggerView = checkNotNull(loggerView,"loggerView cannot be null");

        mLoggerView.setPresenter(this);
    }

    @Override
    public void start(){

    }



    public boolean beginRecording(){
        Log.v("Presenter:","Begin Recording.");
        return mDataRecorder.start();
    }

    public boolean endRecording(){
        Log.v("Presenter:","End Recording.");
        return mDataRecorder.stop();
    }
}
