package com.forzametrix.forzametrix.logger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    NumberPicker ones;
    NumberPicker tens;
    NumberPicker hundreds;
    Switch record;
    ProgressBar forceBar;

    public LoggerFragment(){

    }

    public static LoggerFragment newInstance(){
        return new LoggerFragment();
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.logger_fragment, container, false);


        tens = (NumberPicker) root.findViewById(R.id.numberPicker_tens);
        ones = (NumberPicker) root.findViewById(R.id.numberPicker_ones);
        hundreds = (NumberPicker) root.findViewById(R.id.numberPicker_hundreds);
        record = (Switch)root.findViewById(R.id.switch_record);
        forceBar = (ProgressBar)root.findViewById(R.id.progressBar_force);

        forceBar.setMax(100);
        forceBar.setProgress(0);

        ones.setMinValue(0);
        tens.setMinValue(0);
        hundreds.setMinValue(0);
        ones.setMaxValue(9);
        tens.setMaxValue(9);
        hundreds.setMaxValue(9);
        ones.setWrapSelectorWheel(true);
        tens.setWrapSelectorWheel(true);
        hundreds.setWrapSelectorWheel(true);

        return root;
    }


    public void setPresenter(){

    }



}
