package com.omer.constems_ai_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DB_NAME="recordDb";
    private static final int DB_Version=1;
    private static final String record_table="record_table";
    private static final String ic_col="id";
    private static final String video ="video";
    private static final String step="step";
    private static final String startcor="startcor";
    private static final String endcoe="endcor";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String query = "CREATE TABLE " + record_table + " ("
                + ic_col + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + video + " TEXT,"
                + step + " TEXT,"
                + startcor + " TEXT,"
                + endcoe + " TEXT)";
         db.execSQL(query);
    }
    public void addnewRecord(String vide,String ste,String start,String end){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(video,vide);
        contentValues.put(step,ste);
        contentValues.put(startcor,start);
        contentValues.put(endcoe,end);
        db.insert(record_table,null,contentValues);
        db.close();

    }
    public ArrayList<RecordModel>readRecord(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursorRecord=db.rawQuery("Select * from "+record_table,null);
        ArrayList<RecordModel>arrayList=new ArrayList<>();
        if(cursorRecord.moveToFirst()){
            do{
                arrayList.add(new RecordModel(cursorRecord.getString(1),
                        cursorRecord.getString(2),
                        cursorRecord.getString(3),
                        cursorRecord.getString(4)));

            }
            while (cursorRecord.moveToNext());
        }
        cursorRecord.close();
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + record_table);
        onCreate(db);
    }
}
