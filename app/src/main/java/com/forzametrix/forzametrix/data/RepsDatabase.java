package com.forzametrix.forzametrix.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bryan on 11/13/2017.
 */

public class RepsDatabase implements RepsDatabaseContract.Database {

    /*
        Database Info
        id (int), date (string), set (int), rep (int) , velocity (real), weight (int), exercise type(string)

        csv will be written that correspond with each id
     */

    RepsDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public final static String TABLE = "repsTable"; //name of table
    public final static String ID = "_id"; //id value for the rep
    public final static String SET= "setNum"; //name of the rep
    public final static String DATE = "date"; //date of the rep
    public final static String REP = "repNum"; //the rep.
    public final static String VELOCITY = "velocity"; //the average velocity of the rep
    public final static String WEIGHT = "weight"; //the total weight being lifted
    public final static String TYPE = "type";

    public RepsDatabase(Context context){
        dbHelper = new RepsDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long create(long id, int set,String date, int rep, double vel,int weight, String type){
        ContentValues values = new ContentValues();
        values.put(ID,id);
        values.put(SET,set);
        values.put(DATE,date);
        values.put(REP,rep);
        values.put(VELOCITY,vel);
        values.put(WEIGHT,weight);
        values.put(TYPE,type);
        return db.insert(TABLE,null,values);
    }

    public Cursor selectFirstRow(){
        String[] cols = new String[] {ID,SET,DATE,REP,VELOCITY,WEIGHT,TYPE};
        Cursor mCursor = db.query(true,TABLE,cols,null,null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor; //iterate to get each value
    }

    Cursor selectRowById(long id, String item){
        return db.rawQuery("SELECT " + item + " from " + TABLE + " where " + ID + "=?",new String[]{id + ""});
    }

    public Cursor selectDates(){
        return db.rawQuery("SELECT DISTINCT " + DATE + " from " + TABLE,null);
    }

    public Cursor selectReps(String date){
        return db.rawQuery("SELECT * from " + TABLE + " where " + DATE + "=?",new String[]{date + ""});
    }

    //Update
    public void update(long id){

    }

    //Delete
    public boolean delete(long id){
        return db.delete(TABLE,ID + "=" + id,null) > 0;
    }

    //Read
    int readRowItemInt(long id,String item){

        Cursor mCursor = null;
       try {
           mCursor = selectRowById(id,item);
           int value = -1;

           if (mCursor.getCount() > 0) {
               if(mCursor.moveToFirst())
                   value = mCursor.getInt(0);
           }
           return value;
       } finally {
           mCursor.close();
       }

    }

    String readRowItemString(long id,String item){
        Cursor mCursor = null;
        try {
            mCursor = selectRowById(id,item);
            String value = "";
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                value = mCursor.getString(0);
            }
            return value;
        } finally {
            mCursor.close();
        }
    }

    float readRowItemFloat(long id, String item){
        Cursor mCursor = null;
        try {
            mCursor = selectRowById(id,item);
            float value = -1.0f;
            if (mCursor.getCount() > 0) {
                mCursor.moveToFirst();
                value = mCursor.getFloat(0);
            }
            return value;
        }finally {
            mCursor.close();
        }
    }


    public int readWeight(long id){
        return readRowItemInt(id,WEIGHT);
    }
    public int readReps(long id){
        return readRowItemInt(id,REP);
    }
    public int readSet(long id){
        return readRowItemInt(id,SET);
    }
    public String readDate(long id){
        return readRowItemString(id,DATE);
    }
    public String readType(long id){
        return readRowItemString(id,TYPE);
    }
    public float readVelocity(long id){
        return readRowItemFloat(id,VELOCITY);
    }
}




class RepsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "repsTable";
    private static final int DATABASE_VERSION = 1;

    //TODO: this needs to be modified for the apps needs
    //Ideally we would use a long for the primary key, which would be the time of the beginning of the rep in milliseconds
    private static final String DATABASE_CREATE = "create table repsTable( _id integer primary key, " +
            "date text not null, " +
            "setNum integer," +
            "repNum integer," +
            "velocity real," +
            "weight integer," +
            "type text not null);";

    public RepsDatabaseHelper(Context context){

        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Method called during the creation of the database
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }

    //Method is called during an upgrade of the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RepsDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        db.execSQL("DROP TABLE IF EXISTS repsTable");
        onCreate(db);
    }
}
