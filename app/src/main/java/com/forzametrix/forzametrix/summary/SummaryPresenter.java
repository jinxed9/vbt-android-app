package com.forzametrix.forzametrix.summary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.database.DataSetObserver;

import com.forzametrix.forzametrix.data.RepsDatabase;
import com.forzametrix.forzametrix.data.RepsDatabaseContract;
import com.forzametrix.forzametrix.data.RepsDatabaseContract.DatabaseEventListener;


import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class SummaryPresenter implements SummaryContract.Presenter {



    private final RepsDatabaseContract.Database mSummaryDatabase;
    private SummaryContract.View mSummaryView;
    //private final SummaryFragment mSummaryFragment;


    public SummaryPresenter(@NonNull RepsDatabaseContract.Database database, @NonNull SummaryFragment summaryView){
        mSummaryDatabase = checkNotNull(database,"database cannot be null");
       // mSummaryFragment = checkNotNull(summaryFragment,"summaryFragment cannot be null");
        mSummaryView = checkNotNull(summaryView,"summaryView cannot be null");
        mSummaryView.setPresenter(this);

        mSummaryDatabase.setDatabaseEventListener(new DatabaseEventListener() {
            @Override
            public void onEvent() {
                mSummaryView.notifyDatasetChanged();
            }
        });

    }



    //get all rows with unique dates.
    public int getDatesCount(){
        Cursor dates = mSummaryDatabase.selectDates();
        return dates.getCount();
    }

    public int getRepsCount(String date){
        Cursor reps = mSummaryDatabase.selectReps(date);
        return reps.getCount();
    }


    //make assumption that the cursor will return things in a consistent way
    public String getDate(int index){
        Cursor dates = mSummaryDatabase.selectDates();
        String date = "";
        if(dates.moveToFirst()) {
            dates.move(index);
            date = dates.getString(0);
        }
        return date;
    }

    public String getRepString(String date,int index){
        String repString = "";
        int rep = 0;
        int set = 0;
        float velocity = 0.00f;
        int weight = 0;
        String type = "";
        Cursor reps = mSummaryDatabase.selectReps(date);
        if(reps.moveToFirst()){
            reps.move(index);
            int index3 = reps.getColumnIndex("setNum");
            rep = reps.getInt(reps.getColumnIndex("repNum"));
            set = reps.getInt(reps.getColumnIndex("setNum"));
            velocity = reps.getFloat(reps.getColumnIndex("velocity"));
            weight = reps.getInt(reps.getColumnIndex("weight"));
            type = reps.getString(reps.getColumnIndex("type"));
        }



        return "Set: "+set + " Rep: " + rep + " Vel: " + velocity + " @ " + weight + " lbs "+ type;
    }


    public void deleteRep(String date, int index){
        Cursor cursor = mSummaryDatabase.selectReps(date);
        if(cursor.moveToFirst()){
            cursor.move(index);
            //get the _id of the row
            long rowId = cursor.getLong(cursor.getColumnIndex("_id"));
            //then delete the row
            boolean success = mSummaryDatabase.delete(rowId);
        }
    }




}
