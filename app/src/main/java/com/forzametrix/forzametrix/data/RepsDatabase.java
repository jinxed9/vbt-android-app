package com.forzametrix.forzametrix.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.forzametrix.forzametrix.data.RepsDatabaseContract.DatabaseEventListener;



/**
 * Created by Bryan on 11/13/2017.
 */

public class RepsDatabase implements RepsDatabaseContract.Database {

    /*
        Database Info
        id (int), date (string), set (int), rep (int) , velocity (real), weight (int), exercise type(string)

        csv will be written that correspond with each id
     */

    DatabaseEventListener mDatabaseListener;
    RepsDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context con;

    public final static String TABLE = "repsTable"; //name of table
    public final static String ID = "_id"; //id value for the rep
    public final static String SET= "setNum"; //name of the rep
    public final static String DATE = "date"; //date of the rep
    public final static String REP = "repNum"; //the rep.
    public final static String VELOCITY = "velocity"; //the average velocity of the rep
    public final static String WEIGHT = "weight"; //the total weight being lifted
    public final static String TYPE = "type";

    public RepsDatabase(Context context){
        con = context;
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
        long result = db.insert(TABLE,null,values);
        if(mDatabaseListener != null){
            mDatabaseListener.onCreate();
        }
        return result;
    }

    public Cursor selectDates(){
        return db.rawQuery("SELECT DISTINCT " + DATE + " from " + TABLE,null);
    }

    public Cursor selectReps(String date){
        return db.rawQuery("SELECT * from " + TABLE + " where " + DATE + "=?",new String[]{date + ""});
    }

    //Delete
    public boolean delete(long id){
        boolean result = db.delete(TABLE,ID + "=" + id,null) > 0;
        if(mDatabaseListener != null){
            mDatabaseListener.onDelete();
        }
        return result;
    }

    public void setDatabaseEventListener(DatabaseEventListener listener){
        mDatabaseListener = listener;
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
