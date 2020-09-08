package com.example.jenny.datingfuwa;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Jenny on 08.01.2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "datingDB12.db";
    public static final int DB_VERSION = 13;
    public static final String TABLE_NAME = "dating_table12";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORT = "PW";
    public static final String COLUMN_ALTERE = "ALTERE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_GESCHLECHT = "GESCHLECHT";
    public static final String COLUMN_SUCHE = "SUCHE";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_BESCHREIBUNG = "BESCHREIBUNG";
    public static final String COLUMN_IMAGE = "IMAGES";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_PASSWORT + " TEXT," +
                    COLUMN_BESCHREIBUNG + " TEXT," +
                    COLUMN_GESCHLECHT + " TEXT," +
                    COLUMN_SUCHE + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_IMAGE + " BLOB," +
                    COLUMN_ALTERE + " INTEGER," +
                    COLUMN_NAME + " TEXT" +

                    ");";
                    //TODO: Column Image

    public static final String SQL_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.e(TAG, "Updating table from " + oldVersion + " to " + newVersion);
        // You will not need to modify this unless you need to do some android specific things.
        // When upgrading the database, all you need to do is add a file to the assets folder and name it:
        // from_1_to_2.sql with the version that you are upgrading to as the last version.

            for (int i = oldVersion; i < newVersion; ++i) {
                String migrationName = String.format("from_%d_to_%d.sql", i, (i + 1));
        }
    }

    public boolean insertData(String username, String passwort, String altere, String name, String geschlecht, String email, String suche,  byte[] image, String beschreibung){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORT, passwort);
        contentValues.put(COLUMN_ALTERE, altere);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_GESCHLECHT, geschlecht);
        contentValues.put(COLUMN_SUCHE, suche);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_BESCHREIBUNG, beschreibung);

        long result = db.insert(TABLE_NAME, null, contentValues);

        db.close();

        if(result == -1){
            return false;
        }else {
            return true;
        }

    }

}
