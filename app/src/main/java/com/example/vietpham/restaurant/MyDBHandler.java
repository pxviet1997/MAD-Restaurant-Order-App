package com.example.vietpham.restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mealDB.db";
    public static final String TABLE_NAME = "Meal";
    public static final String COLUMN_ID = "MealId";
    public static final String COLUMN_ORDEREDID = "OrderedId";
    public static final String COLUMN_NAME = "MealName";
    public static final String COLUMN_DESCRIPTION = "MealDescription";
    public static final String COLUMN_QUANTITY = "MealQuantity";
    public static final String COLUMN_CATEGORY = "MealCategory";
    public static final String COLUMN_IMAGEURL = "MealImageUrl";
    public static final String COLUMN_PRICE = "MealPrice";
    public static final String COLUMN_STATUS = "MealStatus";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super((android.content.Context) context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
        + COLUMN_ID + " TEXT PRIMARY KEY,"
        + COLUMN_ORDEREDID + " INTEGER,"
        + COLUMN_NAME + " TEXT,"
        + COLUMN_DESCRIPTION + " TEXT,"
        + COLUMN_QUANTITY + " INTEGER,"
        + COLUMN_CATEGORY + " TEXT,"
        + COLUMN_IMAGEURL + " TEXT,"
        + COLUMN_PRICE + " REAL,"
        + COLUMN_STATUS + " TEXT )";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public int loadHandler(ArrayList<String> id, ArrayList<String> name, ArrayList<String> description, ArrayList<String> category, ArrayList<String> imgUrl, ArrayList<Double> price) {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            id.add(cursor.getString(0));
            name.add(cursor.getString(2));
            description.add(cursor.getString(3));
            category.add(cursor.getString(5));
            imgUrl.add(cursor.getString(6));
            price.add(cursor.getDouble(7));
        }
        cursor.close();
        db.close();
        return id.size();
    }

    public boolean check() {
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        boolean check = cursor.moveToFirst();
        cursor.close();
        db.close();
        return check;
    }

    public void addHandler(Meal meal) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, meal.getId());
        values.put(COLUMN_ORDEREDID, meal.getOrderedId());
        values.put(COLUMN_NAME, meal.getName());
        values.put(COLUMN_DESCRIPTION, meal.getDescription());
        values.put(COLUMN_QUANTITY, meal.getQuantity());
        values.put(COLUMN_CATEGORY, meal.getCategory());
        values.put(COLUMN_IMAGEURL, meal.getImageUrl());
        values.put(COLUMN_PRICE, meal.getPrice());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
