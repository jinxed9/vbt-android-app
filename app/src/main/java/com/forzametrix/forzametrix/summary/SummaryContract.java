/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.forzametrix.forzametrix.summary;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.forzametrix.forzametrix.BasePresenter;
import com.forzametrix.forzametrix.BaseView;

//import com.example.android.architecture.blueprints.todoapp.data.Task;


/**
 * This specifies the contract between the view and the presenter.
 */
public interface SummaryContract {

    interface View extends BaseView<Presenter>{
        void notifyDatasetChanged();
    }

    interface Presenter extends BasePresenter {

        //get the number of unique dates
        int getDatesCount();

        String getDate(int index);

        //get the number of reps at a certain date
        int getRepsCount(String date);

        String getRepString(String date, int index);

        void deleteRep(String date, int childPosition);



    }
}
