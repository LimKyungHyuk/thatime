package net.khlim.lib;


import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbManager {


    DbHelper DbHelper;
    SQLiteDatabase DB;
    Context context;

    String time = "";
    int mode, sound;

    public DbManager(Context context) {
        this.context = context;
    }

    public void startDB() {

        DbHelper = new DbHelper(context);
        DB = DbHelper.getWritableDatabase();
    }

    public void endDB() {
        DB.close();
    }


    public void setQuery(String sql){


    }


    public void alarmOn() {

        DBThread_alarmOn DBT = new DBThread_alarmOn();
        DBT.start();
    }

    public void alarmOff() {
        DBThread_alarmOff DBT = new DBThread_alarmOff();
        DBT.start();
    }

    public boolean isAlarm() {

        boolean _operation;
        Cursor cursor = DB.rawQuery("select _operation from alarm where _id=1", null);

        cursor.moveToFirst();
        String oper = cursor.getString(0);

        _operation = new Boolean(oper);

        return _operation;

    }


    public void inputSettingHour(String time) {

        this.time = time;
        DBThread_SettingHour DBT = new DBThread_SettingHour();
        DBT.start();
    }

    public void inputSettingMode(int mode) {
        this.mode = mode;
        DBThread_SettingMode DBT = new DBThread_SettingMode();
        DBT.start();
    }

    public void inputSettingSound(int sound) {
        this.sound = sound;
        DBThread_SettingSound DBT = new DBThread_SettingSound();
        DBT.start();
    }

    public String isSettingHour() {

        Cursor cursor = DB.rawQuery("select _setting_time from alarm where _id=1", null);
        cursor.moveToFirst();

        return cursor.getString(0);
    }

    public int isSettingMode() {
        Cursor cursor = DB.rawQuery("select _setting_mode from alarm where _id=1", null);
        cursor.moveToFirst();

        return Integer.parseInt(cursor.getString(0));
    }

    public int isSettingSound() {
        Cursor cursor = DB.rawQuery("select _setting_sound from alarm where _id=1", null);
        cursor.moveToFirst();

        return Integer.parseInt(cursor.getString(0));
    }

    public ArrayList<String> isSetting() {

        ArrayList<String> ret = new ArrayList<String>();
        // _setting_time, _setting_mode, _setting_sound
        Cursor cursor = DB.rawQuery("select _setting_time, _setting_mode, _setting_sound from alarm where _id=1", null);

        cursor.moveToFirst();
        ret.add(cursor.getString(0));
        ret.add(cursor.getString(1));
        ret.add(cursor.getString(2));

        return ret;
    }


    // DB helper ( create db )
    private class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context) {
            super(context, "THERE.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            try {

                db.execSQL("CREATE TABLE there("
                            + "_id interger, "
                            + "_operation interger, "
                            + "_setting_time text, "
                            + "_setting_mode interger, "
                            + "_setting_sound interger" +
                            ");");

                db.execSQL("Insert into alarm values(1, 'false', '111111111111111111111111', 0, 1)");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub

        }
    }

    private class DBThread_SettingHour extends Thread {

        public void run() {
            try {
                DB.execSQL("update alarm set _setting_time='" + time + "' where _id=1");
            } catch (Exception e) {
                Log.e("AlarmOff", e.toString());
            }
        }
    }

    private class DBThread_SettingMode extends Thread {
        public void run() {
            try {
                DB.execSQL("update alarm set _setting_mode=" + mode + " where _id=1");
            } catch (Exception e) {
                Log.e("AlarmOff", e.toString());
            }
        }
    }

    private class DBThread_SettingSound extends Thread {
        public void run() {
            try {
                DB.execSQL("update alarm set _setting_sound=" + sound + " where _id=1");
            } catch (Exception e) {
                Log.e("AlarmOff", e.toString());
            }
        }
    }

    private class DBThread_alarmOn extends Thread {
        public void run() {
            try {
                DB.execSQL("update alarm set _operation='true' where _id=1");
            } catch (Exception e) {
                Log.e("AlarmOff", e.toString());
            }
        }
    }

    private class DBThread_alarmOff extends Thread {
        public void run() {
            try {
                DB.execSQL("update alarm set _operation='false' where _id=1");
            } catch (Exception e) {
                Log.e("AlarmOff", e.toString());
            }
        }
    }

}
