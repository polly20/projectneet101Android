package com.neet101.project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class mySQLite {

    private static String TAG = "mySQLite";
    public static String DB_NAME ="kpadb_neet101";
    public static String tblStudent ="student";
    public static String tblQuestions ="questions";

    public static boolean isClearTable;

    private SQLiteDatabase SQL_DB;

    public mySQLite(SQLiteDatabase database)
    {
        SQL_DB = database;

        if(isClearTable) {
            execute("DROP TABLE IF EXISTS " + mySQLite.tblStudent + ";");
            execute("DROP TABLE IF EXISTS " + mySQLite.tblQuestions + ";");
        }

        String tbl_field = "student_id INT, reference_id VARCHAR, batch_id INT";
        created_table("student", tbl_field);

        tbl_field = "subject_id INT, batch_id INT, question_id INT, question VARCHAR, choice_1 VARCHAR, choice_2 VARCHAR, choice_3 VARCHAR, choice_4 VARCHAR, correct_answer INT, answer_explanation VARCHAR";
        created_table("questions", tbl_field);
    }


    public boolean created_table(String name, String fields) {
        try {
            SQL_DB.execSQL("CREATE TABLE IF NOT EXISTS "+ name +" ("+ fields +");");
            Log.d(TAG, "table was created.");
            return true;
        }
        catch (SQLiteException ex) {
            Log.d(TAG, ex.toString());
        }
        return false;
    }

    public boolean insert(String table, String values) {
        try {
            SQL_DB.execSQL("INSERT INTO "+ table +" VALUES("+ values +");");
            Log.d(TAG, "Save to " + table);
            return true;
        }
        catch (SQLiteException ex) {
            Log.d(TAG, ex.toString());
        }
        return false;
    }

    public boolean execute(String query) {
        try {
            SQL_DB.execSQL(query);
            Log.d(TAG, "DONE");
            return true;
        }
        catch (SQLiteException ex) {
            Log.d(TAG, ex.toString());
        }
        return false;
    }


    //                    String strQuery = StudentID + ", " + batch_id;
//                    strQuery += qid + ", '" + question + "', " + "'" + choice_1 + "', " + "'" + choice_2 + "', " + "'" + choice_3 + "', " + "'" + choice_4 + "', ";
//                    strQuery += correct_answer + ", '" + correct_answer_explanation + "'";
//                    Log.d("COUNT " + i , strQuery + "");

//                    mysqlite.insert(mySQLite.tblQuestions, strQuery);


}
