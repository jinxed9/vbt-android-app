package com.forzametrix.forzametrix.summary;

import android.support.annotation.NonNull;

import com.forzametrix.forzametrix.data.RepsDatabase;
import com.forzametrix.forzametrix.data.RepsDatabaseContract;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class SummaryPresenter implements SummaryContract.Presenter {




    private final RepsDatabase mSummaryDatabase;
    //private final SummaryContract.View mSummaryView;
    private final SummaryFragment mSummaryFragment;

    public SummaryPresenter(@NonNull RepsDatabase database, @NonNull SummaryFragment summaryFragment){
        mSummaryDatabase = checkNotNull(database,"database cannot be null");
        mSummaryFragment = checkNotNull(summaryFragment,"summaryFragment cannot be null");
        //mSummaryView = checkNotNull(summaryView,"summaryView cannot be null");
    }



}
