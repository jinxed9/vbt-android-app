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

package com.forzametrix.forzametrix.data;

import android.database.Cursor;

public interface RepsDatabaseContract {

    public interface Database {

        //Create
        long create(long id, int set,String date, int rep, double vel,int weight, String type);

        //Update
        void update(long id);

        //Delete
        boolean delete(long id);

        //Read
        int readWeight(long id);
        int readReps(long id);
        int readSet(long id);
        String readDate(long id);
        String readType(long id);
        float readVelocity(long id);

        Cursor selectDates();
        Cursor selectReps(String date);

    }


}
