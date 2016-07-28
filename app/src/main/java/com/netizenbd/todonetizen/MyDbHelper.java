package com.netizenbd.todonetizen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n on 7/25/2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {

    long insert;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "todonetizen.db";

    private static final String TABLE_INSTITUTION = "table_institution";
    private static final String TABLE_TASK = "table_task";

    public static final String COLUMN_INSTITUTION_ID = "inst_id",
            COLUMN_INSTITUTION_NAME = "name",
            COLUMN_INSTITUTION_ADDRESS = "address",
            COLUMN_INSTITUTION_AUTHORITY_NAME = "authority_name",
            COLUMN_INSTITUTION_DESIGNATION = "designation",
            COLUMN_INSTITUTION_MOBILE_NO = "mobile_no",
            COLUMN_INSTITUTION_STUDENT_AMOUNT = "student_amount",

    //    COLUMN_TASK_ID = "inst_id", // we don't need this, because we can use COLUMN_INSTITUTION_ID in task table and they should be same
    COLUMN_TASK_VISITED_DATE = "task_v_date",
            COLUMN_TASK_VISITED_TIME = "task_v_time",
            COLUMN_TASK_NOTE = "task_note",
            COLUMN_TASK_FOLLOWUP_DATE = "task_f_date",
            COLUMN_TASK_FOLLOWUP_TIME = "task_f_time";


    // table creation sql
    private static final String SQL_CreateInstitutionTable = "CREATE TABLE " + TABLE_INSTITUTION + "(" +
            COLUMN_INSTITUTION_ID + " TEXT PRIMARY KEY, " +
            COLUMN_INSTITUTION_NAME + " TEXT UNIQUE, " +
            COLUMN_INSTITUTION_ADDRESS + " TEXT, " +
            COLUMN_INSTITUTION_AUTHORITY_NAME + " TEXT, " +
            COLUMN_INSTITUTION_DESIGNATION + " TEXT, " +
            COLUMN_INSTITUTION_MOBILE_NO + " TEXT UNIQUE, " +
            COLUMN_INSTITUTION_STUDENT_AMOUNT + " TEXT)";

//    private static final String SQL_CreateTaskTable = "CREATE TABLE " + TABLE_TASK + "(" +
//            COLUMN_INSTITUTION_ID + " TEXT UNIQUE, " +
//            COLUMN_INSTITUTION_NAME + " TEXT UNIQUE, " +
//            COLUMN_INSTITUTION_ADDRESS + " TEXT, " +
//            COLUMN_INSTITUTION_AUTHORITY_NAME + " TEXT, " +
//            COLUMN_INSTITUTION_DESIGNATION + " TEXT, " +
//            COLUMN_INSTITUTION_MOBILE_NO + " TEXT UNIQUE, " +
//            COLUMN_INSTITUTION_STUDENT_AMOUNT + " TEXT)";

    private static final String SQL_CreateTaskTable = "CREATE TABLE " + TABLE_TASK + "(" +
            COLUMN_INSTITUTION_ID + " TEXT, " + // institution id also in the task table, it is foreign key so it is not unique here
            COLUMN_TASK_VISITED_DATE + " TEXT, " +
            COLUMN_TASK_VISITED_TIME + " TEXT, " +
            COLUMN_TASK_NOTE + " TEXT, " +
            COLUMN_TASK_FOLLOWUP_DATE + " TEXT, " +
            COLUMN_TASK_FOLLOWUP_TIME + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_INSTITUTION_ID + ") REFERENCES " + TABLE_INSTITUTION +
            " (" + COLUMN_INSTITUTION_ID + ") ON UPDATE SET NULL)";


    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables
        db.execSQL(SQL_CreateInstitutionTable);
        db.execSQL(SQL_CreateTaskTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTITUTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
    }


    public boolean saveNewInstitutionData(String instId, String instName, String instAddress, String authoName,
                                          String authoDesignation, String mobileNo, String stuAmount) {

        SQLiteDatabase dbe = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_INSTITUTION_ID, instId);
        values.put(COLUMN_INSTITUTION_NAME, instName);
        values.put(COLUMN_INSTITUTION_ADDRESS, instAddress);
        values.put(COLUMN_INSTITUTION_AUTHORITY_NAME, authoName);
        values.put(COLUMN_INSTITUTION_DESIGNATION, authoDesignation);
        values.put(COLUMN_INSTITUTION_MOBILE_NO, mobileNo);
        values.put(COLUMN_INSTITUTION_STUDENT_AMOUNT, stuAmount);

        // Inserting Row
        insert = dbe.insert(TABLE_INSTITUTION, null, values);

        dbe.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @return List
     */
    public List<String> getAllInstName() { // to spinner
        List<String> instName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INSTITUTION + " ORDER BY " + COLUMN_INSTITUTION_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                instName.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning designation
        return instName;
    }


    public boolean saveTaskData(String instName, String vDate, String vTime, String note, String fDate, String fTime) {

        /**
         * here institution id need to also save, which will not shown on new task page, so need to get id first from the inst name
         */

        // get instId from instName
        List<String> instGetId = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT " + COLUMN_INSTITUTION_ID + " FROM " + TABLE_INSTITUTION + " WHERE " + COLUMN_INSTITUTION_NAME + " = '" + instName + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                instGetId.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        String collectedId = instGetId.get(0);


        // insert data to task table
        SQLiteDatabase dbi = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();

        values.put(COLUMN_INSTITUTION_ID, collectedId); // insert collected id from name
        values.put(COLUMN_TASK_VISITED_DATE, vDate);
        values.put(COLUMN_TASK_VISITED_TIME, vTime);
        values.put(COLUMN_TASK_NOTE, note);
        values.put(COLUMN_TASK_FOLLOWUP_DATE, fDate);
        values.put(COLUMN_TASK_FOLLOWUP_TIME, fTime);

        // Inserting Row
        insert = dbi.insert(TABLE_TASK, null, values);

        dbi.close();

        if (insert == -1) {
            return false;
        } else {
            return true;
        }

    }

    /**
     * @return List
     */
    public Cursor getAllTask(String instName) { // task to generate tasks table (return cursor for easy implementation of data in a table)
        String stringId = ""; // in a method it should initialized first

        // get id first from institute name
        String instIdGet = "SELECT * FROM " + TABLE_INSTITUTION + " WHERE " + COLUMN_INSTITUTION_NAME + "='" + instName + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cid = db.rawQuery(instIdGet, null);

        while (cid.moveToNext()) {
            int i = 0;
            stringId = cid.getString(i);
            break;
        }


        //--------------------------
        // get task using inst id

        String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + COLUMN_INSTITUTION_ID + "='" + stringId + "';";
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor cursor = db2.rawQuery(selectQuery, null);

        // returning task cursor
        return cursor;

    }

}
