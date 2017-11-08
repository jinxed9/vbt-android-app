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

package com.forzametrix.forzametrix.logger;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.support.annotation.NonNull;

import com.forzametrix.forzametrix.BasePresenter;
import com.forzametrix.forzametrix.BaseView;
//import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoggerContract {

    interface View extends BaseView<Presenter>{

        void updateForce(int force);

        void updateForceView(String force);

        void updateRep(String rep);

        void updateVelocity(String velocity);

    }

    interface Presenter extends BasePresenter,SensorEventListener {

        void beginRecording();

        void endRecording();

        void onSensorChanged(SensorEvent sensorEvent);

        void onAccuracyChanged(Sensor sensor, int accuracy);


    }
}
