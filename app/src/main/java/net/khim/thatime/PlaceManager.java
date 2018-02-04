package net.khim.thatime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import net.khim.thatime.values.ValuePlace;

import java.util.ArrayList;


public class PlaceManager extends SQLiteOpenHelper {

    private static PlaceManager placeManager;

    public PlaceManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static PlaceManager getInstance(Context context){

        if(placeManager == null){
            placeManager = new PlaceManager(context, "MyPlace.db", null, 1);
        }

        return placeManager;
    }



//
//    public ValuePlace getPlace(){
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM MYPLACE", null);
//        cursor.moveToFirst();
//
//        ValuePlace vp = new ValuePlace();
//
//        vp.setKey(cursor.getLong(0));
//        vp.setTitle(cursor.getString(1));
//        vp.setMemo(cursor.getString(2));
//
//        cursor.close();
//        db.close();
//
//        return vp;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MYPLACE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "date TEXT, " +
                "type TEXT, " +
                "gallery TEXT, " +
                "address TEXT, " +
                "mapx TEXT, " +
                "mapy TEXT, " +
                "memo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ValuePlace getPlace(long valuePlaceKey) {

        ValuePlace vp = new ValuePlace();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MYPLACE WHERE _id = '" + valuePlaceKey + "'", null);
        cursor.moveToFirst();

        vp.setKey(cursor.getLong(0));
        vp.setTitle(cursor.getString(1));
        vp.setDate(cursor.getString(2));
        vp.setType(cursor.getString(3));
        vp.setGallery(cursor.getString(4));
        vp.setAddress(cursor.getString(5));
        vp.setMapx(cursor.getString(6));
        vp.setMapy(cursor.getString(7));
        vp.setMemo(cursor.getString(8));

        cursor.close();
        db.close();

        return vp;

    }

    public ArrayList<ValuePlace> getPlaceAll(){

        ArrayList<ValuePlace> vpLsit = new ArrayList<ValuePlace>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MYPLACE", null);


        while (cursor.moveToNext()) {

            ValuePlace vp = new ValuePlace();
            vp.setKey(cursor.getLong(0));
            vp.setTitle(cursor.getString(1));
            vp.setDate(cursor.getString(2));
            vp.setType(cursor.getString(3));
            vp.setGallery(cursor.getString(4));
            vp.setAddress(cursor.getString(5));
            vp.setMapx(cursor.getString(6));
            vp.setMapy(cursor.getString(7));
            vp.setMemo(cursor.getString(8));

            vpLsit.add(vp);
        }

        cursor.close();
        db.close();

        return vpLsit;
    }


    public void insertPlace(ValuePlace v){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MYPLACE VALUES(null, '"
                + v.getTitle() + "', '"
                + v.getDate() + "', '"
                + v.getType() + "', '"
                + v.getGallery() + "', '"
                + v.getAddress() + "', '"
                + v.getMapx() + "', '"
                + v.getMapy() + "', '"
                + v.getMemo() + "');");
        db.close();
    }

    public void updatePlace(ValuePlace v){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MYPLACE SET " +
                "title = '" + v.getTitle() + "', " +
                "date = '" + v.getDate() + "', " +
                "type = '" + v.getType() + "', " +
                "gallery = '" + v.getGallery() + "', " +
                "address = '" + v.getAddress() + "', " +
                "mapx = '" + v.getMapx() + "', " +
                "mapy = '" + v.getMapy() + "', " +
                "memo = '" + v.getMemo() + "' " +
                "WHERE _id = '" + v.getKey() + "'");

        db.close();

    }

    public void deletePlace(long valuePlaceKey ){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MYPLACE WHERE _id = '" + valuePlaceKey + "'");
        db.close();
    }


}
