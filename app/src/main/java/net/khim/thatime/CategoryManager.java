package net.khim.thatime;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.khim.thatime.values.ValueCategory;

import java.util.ArrayList;


public class CategoryManager extends SQLiteOpenHelper {

    private static CategoryManager placeManager;

    private static final String DB_NAME = "MyCategory.db";
    private final String TABLE_NAME = "CATEGORY";


    public CategoryManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static CategoryManager getInstance(Context context){

        if(placeManager == null){
            placeManager = new CategoryManager(context, DB_NAME, null, 1);
        }

        return placeManager;
    }


    public void insertCategory(ValueCategory v){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null, '" + v.getName() + "');");
        db.close();
    }

    public ValueCategory getCategory(long key) {

        ValueCategory vp = new ValueCategory();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE _id = '" + key + "'", null);
        cursor.moveToFirst();

        vp.setKey(cursor.getLong(0));
        vp.setName(cursor.getString(1));

        cursor.close();
        db.close();

        return vp;

    }

    public ArrayList<ValueCategory> getCategoryList(){

        ArrayList<ValueCategory> vpLsit = new ArrayList<ValueCategory>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);


        while (cursor.moveToNext()) {

            ValueCategory vc = new ValueCategory();
            vc.setKey(cursor.getLong(0));
            vc.setName(cursor.getString(1));


            vpLsit.add(vc);
        }

        cursor.close();
        db.close();

        return vpLsit;
    }

    public String[] getCategoryArray(){

        ArrayList<String> vpLsit = new ArrayList<String>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);


        while (cursor.moveToNext()) {
            vpLsit.add(cursor.getString(1));
        }

        cursor.close();
        db.close();

        return vpLsit.toArray(new String[vpLsit.size()]);
    }

    public void updateCategory(ValueCategory v){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET name = '" + v.getName() + "' WHERE _id = '" + v.getKey() + "'");
        db.close();

    }

    public void deleteCategory(long key ){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id = '" + key + "'");
        db.close();
    }

    public void deleteAllCategory(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
