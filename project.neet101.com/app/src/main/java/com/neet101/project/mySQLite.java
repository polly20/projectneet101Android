package com.neet101.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class mySQLite {

    private static String TAG = "mySQLite";
    public static String DB_NAME ="kpadb_neet101";
    private SQLiteDatabase SQL_DB;

    public mySQLite(SQLiteDatabase database)
    {
        SQL_DB = database;

        String tbl_field = "student_id INT, reference_id VARCHAR, batch_id INT";
        created_table("student", tbl_field);

        tbl_field = "subject_id INT, batch_id INT, question_id INT, question VARCHAR, choice_1 VARCHAR, choice_2 VARCHAR, choice_3 VARCHAR, choice_4 VARCHAR";
        created_table("questions", tbl_field);
    }

    private boolean isDbPresent() {
        Log.v(TAG, "is DB present Entry!!!");
        boolean checkFlag = true;
        SQLiteDatabase testDb;
        String testPath = DB_NAME;
        try{
            testDb = SQLiteDatabase.openDatabase(testPath, null, SQLiteDatabase.OPEN_READWRITE);
        }
        catch(SQLiteException sqlException){
            Log.v(TAG, "DB absent");
            checkFlag=false;
        }
        Log.v(TAG, "is DB present Exit!!!");
        return checkFlag;
    }

    public void created_table(String name, String fields) {
        SQL_DB.execSQL("CREATE TABLE IF NOT EXISTS "+ name +" ("+ fields +");");
        Log.d(TAG, "table was created.");
    }

    public boolean insert(String table, String values) {
        try {
            SQL_DB.execSQL("INSERT INTO "+ table +" VALUES("+ values +");");
            Log.d(TAG, "DONE");
            return true;
        }
        catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
        return false;
    }


}
