package com.forzametrix.forzametrix.data;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import java.util.Calendar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import android.database.sqlite.SQLiteOpenHelper;


import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by Bryan on 10/29/2017.
 */

public class DataRecorder implements DataRecorderContract.DataRecorder{

    public void DataRecorder(){
    }

    protected void onCreate(Bundle savedInstanceState) {
        //create or open the database here

    }



    public boolean start(){
      Log.v("Model:","Recording Started");

        return true;
    }

    public boolean stop(){
      Log.v("Model:","Recording Stopped");

        return true;
    }

    public void writeToFile(String data,String fileName){
        Log.v("Model:", "Write to file.");
        File dir = new File(Environment.getExternalStorageDirectory(),"forzametrix");
        boolean dirExists;
        if(!dir.exists()){
            Log.v("Model:","Directory doesn't exist, creating...");
            dirExists = dir.mkdirs();
            if(dirExists)
                Log.v("Model:","Directory created.");
        }

        Long time = Calendar.getInstance().getTimeInMillis();

        try {
            File file = new File(dir,"data_" + time + ".txt");
            boolean fileExists;
            if(!file.exists()) {
                Log.v("Model:","File doesn't exist, creating...");
                fileExists = file.createNewFile();
                if(fileExists)
                    Log.v("Model:","File created.");
            }


            Log.v("Model:","Writing to file...");
            FileOutputStream f = new FileOutputStream(file);
            f.write(data.getBytes());
            f.flush();
            f.close();
            Log.v("Model:","Finished.");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
