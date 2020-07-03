package com.example.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mainDB"; //Имя бд
    private static final String TABLE_NAME = "Students"; //Имя таблицы
    private static final int DB_VERSION = 2;// Версия бд

    private static final String KEY_ID = "ID";//Поля таблицы Students
    private static final String KEY_SURNAME = "Фамилия";
    private static final String KEY_NAME = "Имя";//
    private static final String KEY_MIDDLE_NAME = "Отчество";
    private static final String KEY_DATE = "DATE";//

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME +" ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_DATE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        if(newVersion==2){
            db.execSQL("DROP TABLE " + TABLE_NAME);
            db.execSQL("CREATE TABLE " + TABLE_NAME +" ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_SURNAME + " TEXT, "
                    + KEY_NAME + " TEXT, "
                    + KEY_MIDDLE_NAME + " TEXT, "
                    + KEY_DATE + " TEXT);");
        }
    }

    public static String getTableName() {return TABLE_NAME;}

    public static String getKeyID(){return KEY_ID;}

    public static String getKeySurname() { return KEY_SURNAME; }

    public static String getKeyName() {return KEY_NAME;}

    public static String getKeyMiddleName(){ return KEY_MIDDLE_NAME; }

    public static String getKeyDate() {return KEY_DATE;}
};