package com.example.registeration;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MySQLDb.db";

    // Register Table
    public static final String Register_TABLE_NAME = "registration";
    public static final String Register_COLUMN_ID = "UR_id";
    public static final String Register_COLUMN_Name = "UR_Name";
    public static final String Register_COLUMN_Email = "UR_Email";
    public static final String Register_COLUMN_Phone_Number = "UR_PhNum";
    public static final String Register_COLUMN_Password = "UR_Password";
    public static final String Register_COLUMN_Address = "UR_Address";


    // Project Table
    public static final String Project_TABLE_NAME = "projects";
    public static final String Project_COLUMN_ID = "P_id";
    public static final String Project_COLUMN_Name = "P_Name";
    public static final String Project_COLUMN_Type = "P_Type";
    public static final String Project_COLUMN_Status = "P_Status";
    public static final String Project_COLUMN_Description = "P_Desc";
    public static final String Project_COLUMN_Sync = "STATUS";

    // Resource Table
    public static final String Resource_TABLE_NAME = "resource";
    public static final String Resource_COLUMN_ID = "R_id";
    public static final String Resource_COLUMN_Name = "R_Name";
    public static final String Resource_COLUMN_Type = "R_Type";
    public static final String Resource_COLUMN_Status = "R_Status";
    public static final String Resource_COLUMN_Description = "R_Desc";
    public static final String Resource_COLUMN_Sync = "STATUS";

    // Change Project Title Table
    public static final String CPT_TABLE_NAME = "CPTA_Table";
    public static final String CPT_COLUMN_ID = "CPT_id";
    public static final String CPT_COLUMN_NAME = "CPT_name";
    public static final String CPT_COLUMN_REQUEST = "CPT_request";
    public static final String CPT_COLUMN_DATE = "CPT_date";
    public static final String CPT_COLUMN_reqNAME = "CPT_reqName";
    public static final String CPT_COLUMN_chDESC = "CPT_chDesc";
    public static final String CPT_COLUMN_chREASON = "CPT_chReason";
    public static final String CPT_COLUMN_Impact = "CPT_Impact";
    public static final String CPT_COLUMN_Sync = "STATUS";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table registration " +
                        "(UR_id integer primary key, UR_Name text,UR_Email text,UR_PhNum text,UR_Password text,UR_Address text)"
        );
        db.execSQL(
                "create table projects " +
                        "(P_id integer primary key , P_Name text,P_Type text,P_Status text,P_Desc text,STATUS text)"
        );
        db.execSQL(
                "create table resource " +
                        "(R_id integer primary key , R_Name text,R_Type text,R_Status text,R_Desc text,STATUS text)"
        );

        db.execSQL(
                "create table CPTA_Table " +
                        "(CPT_id integer primary key , CPT_name text,CPT_request text,CPT_date text,CPT_reqName text,CPT_chDesc text,CPT_chReason text,CPT_Impact text,STATUS text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS registration");
        db.execSQL("DROP TABLE IF EXISTS projects");
        db.execSQL("DROP TABLE IF EXISTS resource");
        db.execSQL("DROP TABLE IF EXISTS CPTA_Table");
        onCreate(db);
    }

    //Register table classes------------------------------------------------------------------------

    public boolean insertInRegister (String name, String Email, String PhNum, String Password , String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Register_COLUMN_Name, name);
        contentValues.put(Register_COLUMN_Email, Email);
        contentValues.put(Register_COLUMN_Phone_Number, PhNum);
        contentValues.put(Register_COLUMN_Password, Password);
        contentValues.put(Register_COLUMN_Address, address);
        db.insert("registration", null, contentValues);
        return true;
    }

    public Cursor readallRegisterData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String res =  "select * from registration order by UR_id desc";
        Cursor cursor = db.rawQuery(res,null);
        return cursor;
    }


    public boolean getSpecificRegisterData(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean result = false;
        try {
            String[] params = new String[]{email, pass};
            Cursor res = db.rawQuery("SELECT UR_Email,UR_Password FROM registration WHERE UR_Email = ? and UR_Password = ?",
                    params);
            result = res.getCount() > 0;
            res.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
        return  result;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Register_TABLE_NAME);
        return numRows;
    }

    public boolean updateRegisterData (String temp,String name, String Email, String PhNum, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UR_Name", name);
        contentValues.put("UR_Email", Email);
        contentValues.put("UR_PhNum", PhNum);
        contentValues.put("UR_Address", address);
        db.update("registration", contentValues, "UR_Email = ? ", new String[] { temp } );
        return true;
    }

    public Integer deleteSpecificRegister (String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("registration",
                "UR_Email = ? ",
                new String[] { email });
    }

    //Project table classes-------------------------------------------------------------------------

    public boolean insertInProject (String name, String type, String status, String desc,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Project_COLUMN_Name, name);
        contentValues.put(Project_COLUMN_Type, type);
        contentValues.put(Project_COLUMN_Status, status);
        contentValues.put(Project_COLUMN_Description, desc);
        contentValues.put(Project_COLUMN_Sync, st);

        db.insert("projects", null, contentValues);
        return true;
    }

    public Cursor readAllProjectData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String res =  "select * from projects order by P_id desc";
        Cursor cursor = db.rawQuery(res,null);
        return cursor;
    }

    public Cursor getSpecificProjectData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from projects where P_id="+id+"", null );
        return res;
    }

    public int numberOfProjectTRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Project_TABLE_NAME);
        return numRows;
    }

    public boolean updateProjectData (Integer id, String name, String type, String status, String desc,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("P_Name", name);
        contentValues.put("P_Type", type);
        contentValues.put("P_Status", status);
        contentValues.put("P_Desc", desc);
        contentValues.put("STATUS", st);
        db.update("projects", contentValues, "P_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteSpecificProject (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("projects",
                "P_id = ? ",
                new String[] { Integer.toString(id) });
    }

    //Resource table classes-------------------------------------------------------------------------

    public boolean insertInResource (String name, String type, String status, String desc,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource_COLUMN_Name, name);
        contentValues.put(Resource_COLUMN_Type, type);
        contentValues.put(Resource_COLUMN_Status, status);
        contentValues.put(Resource_COLUMN_Description, desc);
        contentValues.put(Resource_COLUMN_Sync, st);

        db.insert("resource", null, contentValues);
        return true;
    }

    public Cursor readAllResourceData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String res =  "select * from resource order by R_id desc";
        Cursor cursor = db.rawQuery(res,null);
        return cursor;
    }

    public Cursor getSpecificResourceData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from resource where R_id="+id+"", null );
        return res;
    }

    public int numberOfResourceTRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Resource_TABLE_NAME);
        return numRows;
    }

    public boolean updateResourceData (Integer id, String name, String type, String status, String desc,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("R_Name", name);
        contentValues.put("R_Type", type);
        contentValues.put("R_Status", status);
        contentValues.put("R_Desc", desc);
        contentValues.put("STATUS", st);
        db.update("resource", contentValues, "R_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteSpecificResource (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("resource",
                "R_id = ? ",
                new String[] { Integer.toString(id) });
    }


    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " + TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

    //Change project title table classes-------------------------------------------------------------------------

    public boolean insertInCPTA (String name, String req,String date, String reqName, String desc , String reason, String impact,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CPT_COLUMN_NAME, name);
        contentValues.put(CPT_COLUMN_REQUEST, req);
        contentValues.put(CPT_COLUMN_DATE, date);
        contentValues.put(CPT_COLUMN_reqNAME, reqName);
        contentValues.put(CPT_COLUMN_chDESC, desc);
        contentValues.put(CPT_COLUMN_chREASON, reason);
        contentValues.put(CPT_COLUMN_Impact, impact);
        contentValues.put(CPT_COLUMN_Sync, st);
        db.insert("CPTA_Table", null, contentValues);
        return true;
    }

    public Cursor readAllCPTAData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String res =  "select * from CPTA_Table order by CPT_id desc";
        Cursor cursor = db.rawQuery(res,null);
        return cursor;
    }

    public boolean updateCPTAData (Integer id, String name, String req,String date, String reqName, String desc , String reason, String impact,String st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CPT_name", name);
        contentValues.put("CPT_request", req);
        contentValues.put("CPT_date", date);
        contentValues.put("CPT_reqName", reqName);
        contentValues.put("CPT_chDesc", desc);
        contentValues.put("CPT_chReason", reason);
        contentValues.put("CPT_Impact", impact);
        contentValues.put("STATUS", st);
        db.update("CPTA_Table", contentValues, "CPT_id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteSpecificCPTA (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("CPTA_Table",
                "CPT_id = ? ",
                new String[] { Integer.toString(id) });
    }

}