package com.forzametrix.forzametrix.logger;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.forzametrix.forzametrix.data.DataRecorder;
import com.forzametrix.forzametrix.data.DataRecorderContract;



import com.forzametrix.forzametrix.R;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class LoggerActivity extends AppCompatActivity implements LoggerContract.View {

    private LoggerContract.Presenter mPresenter;

    private NumberPicker ones;
    private NumberPicker tens;
    private NumberPicker hundreds;
    private ProgressBar forceBar;
    private Switch record;
    private TextView forceNum;
    private DataRecorder mDataRecorder;

    private int plusOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logger_activity);

        SensorManager mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        mDataRecorder = new DataRecorder();
        mPresenter = new LoggerPresenter(mDataRecorder,this);





        tens = (NumberPicker) findViewById(R.id.numberPicker);
        ones = (NumberPicker) findViewById(R.id.numberPicker2);
        hundreds = (NumberPicker) findViewById(R.id.numberPicker3);
        record = (Switch)findViewById(R.id.switch1);
        forceBar = (ProgressBar)findViewById(R.id.progressBar2);

        forceBar.setMax(100);
        forceBar.setProgress(0);

        forceNum = (TextView) findViewById(R.id.textView6);

        ones.setMinValue(0);
        tens.setMinValue(0);
        hundreds.setMinValue(0);
        ones.setMaxValue(9);
        tens.setMaxValue(9);
        hundreds.setMaxValue(9);
        ones.setWrapSelectorWheel(false);
        tens.setWrapSelectorWheel(false);
        hundreds.setWrapSelectorWheel(false);

        plusOne = 0;

        record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.v("Switch State = ","" + b);

                plusOne++;

                //This is for testing purposes
               // forceNum.setText("" + plusOne);

                forceBar.setProgress(plusOne);
                if(b == true) {
                    mPresenter.beginRecording();
                }
                if(b == false){
                    mPresenter.endRecording();
                }
            }
        });

    }



    public void setPresenter(@NonNull LoggerContract.Presenter presenter){
        mPresenter = checkNotNull(presenter);
    }


    public void updateForce(int force){
        forceNum.setText("" + force);

    }

}
