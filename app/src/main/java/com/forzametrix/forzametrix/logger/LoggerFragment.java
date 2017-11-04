package com.forzametrix.forzametrix.logger;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
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

public class LoggerFragment extends Fragment {

    private LoggerContract.Presenter mPresenter;
    private NumberPicker ones;
    private NumberPicker tens;
    private NumberPicker hundreds;
    private ProgressBar forceBar;
    private Switch record;
    private TextView forceNum;
    private DataRecorder mDataRecorder;
    private int plusOne;

    public LoggerFragment(){

    }

    public static LoggerFragment newInstance(){
        return new LoggerFragment();
    }

    public void onCreate(){





     //   tens = (NumberPicker) findViewById(R.id.numberPicker);
      //  ones = (NumberPicker) findViewById(R.id.numberPicker2);
      //  hundreds = (NumberPicker) findViewById(R.id.numberPicker3);
       // record = (Switch)findViewById(R.id.switch1);
       // forceBar = (ProgressBar)findViewById(R.id.progressBar2);

        forceBar.setMax(100);
        forceBar.setProgress(0);

       // forceNum = (TextView) findViewById(R.id.textView6);

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
    }


    public void setPresenter(){

    }



}
