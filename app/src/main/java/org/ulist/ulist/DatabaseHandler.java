package org.ulist.ulist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 5.1.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "storeManager",
        TABLE_STORE = "stores",
        KEY_ID = "id",
        KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_STORE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);

        onCreate(db);
    }

    //TODO CRUD

    public void createStore(Store store) {
        SQLiteDatabase db = getWritableDatabase(); //we can write trough database

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, store.getName());

        //load to table
        db.insert(TABLE_STORE, null, values);
        db.close();
    }

    public Store getStore(int id) {
        SQLiteDatabase db = getReadableDatabase();

        //goto each db row and select certain values
        Cursor cursor = db.query(TABLE_STORE, new String[] { KEY_ID, KEY_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Store store = new Store(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        db.close();
        cursor.close();

        return store;
    }

    public void deleteStore(Store store) {
        SQLiteDatabase db = getWritableDatabase();

        // =? = specify where statement
        db.delete(TABLE_STORE, KEY_ID + "=?", new String[]{ String.valueOf(store.getId()) });
        db.close();
    }

    public int getStoreCount() {
        //SELECT * FROM STORE
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STORE, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }

    public int updateStore(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //load to table
        db.insert(TABLE_STORE, null, values);
        int rowsAffected = db.update(TABLE_STORE, values, KEY_ID + "=?",
                new String[] { String.valueOf(store.getId()) });

        db.close();

        return rowsAffected;
    }

    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<Store>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STORE, null);

        if(cursor.moveToFirst()) {
            do {
                stores.add(new Store(Integer.parseInt(cursor.getString(0)), cursor.getString(1)));
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return stores;
    }
}
