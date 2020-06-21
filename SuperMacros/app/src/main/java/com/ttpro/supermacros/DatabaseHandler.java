//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHandler.java";
    private static final int DATABASE_VERSION = 4;
    protected static final String DATABASE_NAME = "SuperMacrosDatabase";


    // table
    public String catalogTable = "macros";
    public String logTable = "log";
    public String targetTable = "target";
    public String timestamp = "timestamp";
    public String objectId = "id";
    public String objectName = "name";
    public String objectCalories = "calories";
    public String objectFats = "fats";
    public String objectCarbs = "carbs";
    public String objectProtein = "protein";
    public String objectServingSize = "servingSize";
    public String objectServings = "servings";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 = "";

        sql1 += "CREATE TABLE " + this.logTable;
        sql1 += " ( ";
        sql1 += this.timestamp + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')), ";
        sql1 += this.objectName + " TEXT, ";
        sql1 += this.objectCalories + " INT, ";
        sql1 += this.objectFats + " INT, ";
        sql1 += this.objectCarbs + " INT, ";
        sql1 += this.objectProtein + " INT, ";
        sql1 += this.objectServings + " INT ";
        sql1 += " ) ";
        db.execSQL(sql1);

        String sql2 = "";

        sql2 += "CREATE TABLE " + this.catalogTable;
        sql2 += " ( ";
        sql2 += this.objectId + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql2 += this.objectName + " TEXT, ";
        sql2 += this.objectCalories + " INT, ";
        sql2 += this.objectFats + " INT, ";
        sql2 += this.objectCarbs + " INT, ";
        sql2 += this.objectProtein + " INT, ";
        sql2 += this.objectServingSize + " INT ";
        sql2 += " ) ";

        db.execSQL(sql2);

        String sql3 = "";

        sql3 += "CREATE TABLE " + this.targetTable;
        sql3 += " ( ";
        sql3 += this.timestamp + " DATETIME DEFAULT (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')), ";
        sql3 += this.objectCalories + " INT, ";
        sql3 += this.objectFats + " INT, ";
        sql3 += this.objectCarbs + " INT, ";
        sql3 += this.objectProtein + " INT ";
        sql3 += " ) ";

        db.execSQL(sql3);

    }

    public void dropTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql1 = "DROP TABLE IF EXISTS " + this.catalogTable;
        db.execSQL(sql1);
        String sql2 = "DROP TABLE IF EXISTS " + this.logTable;
        db.execSQL(sql2);
        String sql3 = "DROP TABLE IF EXISTS " + this.targetTable;
        db.execSQL(sql3);
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //String sqll = "DROP TABLE IF EXISTS " + this.catalogTable;
        //db.execSQL(sql);
        //sqll = "DROP TABLE IF EXISTS " + this.logTable;
        //db.execSQL(sql);
        onCreate(db);
    }

    public boolean create(MyObject myObject) {
        boolean createSuccessful = false;

        if(!checkIfExists(myObject.name)){

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(this.objectName, myObject.name);
            values.put(this.objectCalories, myObject.calories);
            values.put(this.objectFats, myObject.fats);
            values.put(this.objectCarbs, myObject.carbs);
            values.put(this.objectProtein, myObject.protein);
            values.put(this.objectServingSize, myObject.servingSize);
            createSuccessful = db.insert(catalogTable, null, values) > 0;
            if(createSuccessful){
                Log.e(TAG, myObject.name + " created.");
            }
        }

        return createSuccessful;
    }

    public boolean logEntry(MyEntry myEntry, MainActivity mainActivity) {
        boolean logItemSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.objectName, myEntry.name);
        values.put(this.objectCalories, myEntry.calories);
        values.put(this.objectFats, myEntry.fats);
        values.put(this.objectCarbs, myEntry.carbs);
        values.put(this.objectProtein, myEntry.protein);
        values.put(this.objectServings, myEntry.servings);
        logItemSuccessful = db.insert(this.logTable, null, values) > 0;

        Log.e(TAG, myEntry.name + " logged in database.");
        String msg = "Item Successfully Logged.";
        Toast.makeText(mainActivity, msg, Toast.LENGTH_LONG).show();

        return logItemSuccessful;
    }

    public boolean setTargets(MyTarget myTarget, String date) {
        boolean setSuccessful = false;
        deleteTarget(date);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.objectCalories, myTarget.calories);
        values.put(this.objectFats, myTarget.fats);
        values.put(this.objectCarbs, myTarget.carbs);
        values.put(this.objectProtein, myTarget.protein);
        setSuccessful = db.insert(this.targetTable, null, values) > 0;
        return setSuccessful;
    }

    public boolean deleteTarget(String date) {
        boolean deleteSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete(this.targetTable, this.timestamp + " LIKE '%" + date + "%'", null) > 0;
        return deleteSuccessful;
    }

    public boolean addUpdateObject(MyObject myObject, MainActivity mainActivity) {
        boolean addUpdateSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(this.objectName, myObject.name);
        values.put(this.objectCalories, myObject.calories);
        values.put(this.objectFats, myObject.fats);
        values.put(this.objectCarbs, myObject.carbs);
        values.put(this.objectProtein, myObject.protein);
        values.put(this.objectServingSize, myObject.servingSize);

        if (mainActivity.addItem == false) {
            Boolean catalogTableUpdate = db.update(this.catalogTable, values, this.objectName + "='" + mainActivity.itemName + "'", null) > 0;
            values.remove(this.objectServingSize);
            Boolean logTableUpdate = db.update(this.logTable, values, this.objectName + "='" + mainActivity.itemName + "'", null) > 0;
            addUpdateSuccessful = (catalogTableUpdate == true && logTableUpdate == true);
            if (addUpdateSuccessful) {
                Log.e(TAG, myObject.name + " updated in database.");
                Toast.makeText(mainActivity, "Item Updated Successfully", Toast.LENGTH_LONG).show();
            }
        } else {
            addUpdateSuccessful = db.insert(this.catalogTable, null, values) > 0;
            if (addUpdateSuccessful) {
                Log.e(TAG, myObject.name + " added to database.");
                Toast.makeText(mainActivity, "Item Added Successfully", Toast.LENGTH_LONG).show();
            }
        }
        return addUpdateSuccessful;
    }

    public boolean deleteObject(String objectName) {
        boolean deleteOjbectSuccessful = false;
        boolean deleteLogSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
            deleteOjbectSuccessful = db.delete(this.catalogTable, this.objectName + "='" + objectName + "'", null) > 0;
            deleteLogSuccessful = db.delete(this.logTable, this.objectName + "='" + objectName + "'", null) > 0;
        return deleteOjbectSuccessful && deleteLogSuccessful ;
    }

    public boolean deleteEntry(String objectName, String time){
        boolean recordExists = true;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM " + this.logTable + " WHERE " + this.timestamp + " LIKE '%" + time + "'" + " AND " + this.objectName + " = '" + objectName + "'", null);

        if(cursor != null) {
            if(cursor.getCount()>0) {
                recordExists = false;
            }
        }
        cursor.close();
        return recordExists;
    }

    public boolean checkIfExists(String objectName){
        boolean recordExists = false;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + this.objectId + " FROM " + this.catalogTable + " WHERE " + this.objectName + " = '" + objectName + "'", null);

        if(cursor != null) {
            if(cursor.getCount()>0) {
                recordExists = true;
            }
        }
        cursor.close();
        return recordExists;
    }

    public String [] readAutoComplete(String searchTerm) {
        List<String> recordsList = new ArrayList<String>();

        String sql = "";
        sql += "SELECT * FROM " + this.catalogTable;
        sql += " WHERE " + this.objectName + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + this.objectName + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String objectName = cursor.getString(cursor.getColumnIndex(this.objectName));
                recordsList.add(objectName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String [] items = new String[recordsList.size()];
        int x =0;

        for (String item : recordsList) {
            items[x] = item;
            x++;
        }
        return items;
    }

    public MyObject getObject(String objectName) {
        String name = "";
        Integer calories = 0;
        Integer fats = 0;
        Integer carbs = 0;
        Integer protein = 0;
        Integer servingSize = 0;

        String sql = "";
        sql += "SELECT * FROM " + this.catalogTable;
        sql += " WHERE " + this.objectName + " LIKE '%" + objectName + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(this.objectName));
                calories = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCalories)));
                fats = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectFats)));
                carbs = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCarbs)));
                protein = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectProtein)));
                servingSize = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectServingSize)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        MyObject myObject = new MyObject(name, calories, fats, carbs, protein, servingSize);
        return myObject;
    }

    public MyEntry getEntry(String objectName, String time) {
        String name = "";
        Integer calories = 0;
        Integer fats = 0;
        Integer carbs = 0;
        Integer protein = 0;
        Integer servings = 0;

        String sql = "";
        sql += "SELECT * FROM " + this.logTable;
        sql += " WHERE " + this.objectName + " LIKE '%" + objectName + "%' AND " + this.timestamp + " LIKE '%" + time + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(this.objectName));
                calories = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCalories)));
                fats = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectFats)));
                carbs = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCarbs)));
                protein = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectProtein)));
                servings = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectServings)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        MyEntry myEntry = new MyEntry(name, calories, fats, carbs, protein, servings);
        myEntry.timesServings();
        return myEntry;
    }

    public MyTarget getTarget(String date) {
        Integer calories = 2500;
        Integer fats = 50;
        Integer carbs = 50;
        Integer protein = 150;

        String [] dateSplit = date.split("-");
        String month = dateSplit[1];
        if (Integer.parseInt(month) < 10 && month.length() < 2) {
            month = "0" + month;
        }
        String day = dateSplit[2];
        if (Integer.parseInt(day) < 10 && day.length() < 2) {
            day = "0" + day;
        }

        date = dateSplit[0] + "-"  + month + "-" + day;

        String sql = "";
        sql += "SELECT * FROM " + this.targetTable;
        sql += " WHERE date(" + this.timestamp + ") <= '" + date + "' LIMIT 1";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                calories = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCalories)));
                fats = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectFats)));
                carbs = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectCarbs)));
                protein = Integer.parseInt(cursor.getString(cursor.getColumnIndex(this.objectProtein)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        MyTarget myTarget = new MyTarget(calories, fats, carbs, protein);
        return myTarget;
    }

    public String [] readAll() {
        List<String> recordsList = new ArrayList<String>();

        String sql = "";
        sql += "SELECT * FROM " + this.catalogTable;
        sql += " WHERE " + this.objectName + " LIKE '%" + "%'";
        sql += " ORDER BY LOWER(" + this.objectName + ") ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String objectName = cursor.getString(cursor.getColumnIndex(this.objectName));
                recordsList.add(objectName);
            } while (cursor.moveToNext());
        }
        cursor.close();

        String [] items = new String[recordsList.size()];
        int x =0;

        for (String item : recordsList) {
            items[x] = item;
            x++;
        }
        return items;
    }

    public String [] readAllEntries(String date) {
        List<String> recordsList = new ArrayList<String>();

        String sql = "";
        sql += "SELECT " + this.objectName + "," + this.timestamp + " FROM " + this.logTable;
        sql += " WHERE " + this.timestamp + " LIKE '"  + date + "%'";
        sql += " ORDER BY (" + this.timestamp + ") DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                String objectName = cursor.getString(cursor.getColumnIndex(this.objectName));
                String timestamp = cursor.getString(cursor.getColumnIndex(this.timestamp));
                timestamp = timestamp.split(" ")[1];
                String entry = String.format(objectName + "_" + timestamp);
                recordsList.add(entry);
            } while (cursor.moveToNext());
        }
        cursor.close();

        String [] items = new String[recordsList.size()];
        int x =0;

        for (String item : recordsList) {
            items[x] = item;
            x++;
        }
        return items;
    }

    public String getTotal(String date, String macro) {
        String total = "";
        switch (macro) {
            case "Calories":
                macro = this.objectCalories;
                break;
            case "Carbs":
                macro = this.objectCarbs;
                break;
            case "Protein":
                macro = this.objectProtein;
                break;
            case "Fats":
                macro = this.objectFats;
                break;
        }
        String sql = "";
        sql += "SELECT SUM(" + macro + " * " + this.objectServings + " ) AS TOTAL FROM " + this.logTable;
        sql += " WHERE " + this.timestamp +  " LIKE '" + date + "%'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                total = cursor.getString(cursor.getColumnIndex("TOTAL"));
            } while (cursor.moveToNext());
        }
        if (total == null) {
            total = "0";
        }
        cursor.close();
        return total;
    }

    public void closeDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();
    }
}