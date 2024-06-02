package com.example.my_finance;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Datab  {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyFIn.db";

    public static final String COLUMN_ID = "_id";
    public static final String TABLE_NAME = "MyFinance";
    public static final String COLUMN_WASTE_DATE ="date";
    public static final String COLUMN_WASTE_NAME ="name";
    public static final String COLUMN_WASTE_SUM="waste";
    private static final int NUM_Id =0;
    private static final int NUM_WASTE_DATE=1;
    private static final int NUM_WASTE_NAME=2;
    private static final int NUM_WASTE_SUM=3;
    public SQLiteDatabase mDataBase;
    public Datab(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();

    }

    private class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);}
        @Override
        public void onUpgrade(SQLiteDatabase db, int old_version, int new_Version) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
        @Override
        public  void onCreate(SQLiteDatabase db ) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    COLUMN_WASTE_DATE + " INTEGER,"+COLUMN_WASTE_NAME+" TEXT ,"+COLUMN_WASTE_SUM+" INTEGER );";
            db.execSQL(query);
        }



    }
    public List<Waste> diapasone(long start, long finish){
        Cursor mCursor = mDataBase.query(TABLE_NAME ,null,COLUMN_WASTE_DATE + " > ? and "+COLUMN_WASTE_DATE + " <? ", new String[]{String.valueOf(start),String.valueOf(finish)},null,null,COLUMN_WASTE_DATE+" desc");
        mCursor.moveToFirst();
        List<Waste> arr = new ArrayList<Waste>();
        if (!mCursor.isAfterLast()) {
            do{
                long id = mCursor.getLong(NUM_Id);
                long date= mCursor.getLong(NUM_WASTE_DATE);
                String name = mCursor.getString(NUM_WASTE_NAME);
                int sum=mCursor.getInt(NUM_WASTE_SUM);
                arr.add(new Waste(id,date,name,sum));


            }while (mCursor.moveToNext());
        }

        return arr ;
    }
    public long add(long date,String name,int waste){
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_WASTE_DATE,date);
        cv.put(COLUMN_WASTE_NAME,name);
        cv.put(COLUMN_WASTE_SUM,waste);
        return (mDataBase.insert(TABLE_NAME, null,cv));
    }
    public void delete(long id){
        mDataBase.delete(TABLE_NAME,COLUMN_ID+"= ?", new String[] { String.valueOf(id) });
    }
}



