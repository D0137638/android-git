package com.example.user.finalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DbHelper extends SQLiteOpenHelper {
    public static final String TAG = DbHelper.class.getSimpleName();
    public static final String DB_NAME = "myapp.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_USER = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";

    public static final String TABLE_BOOK = "books";
    public static final String COLUMN_BOOK_ID = COLUMN_ID;
    public static final String COLUMN_BOOK_NAME = "name";
    public static final String COLUMN_BOOK_PRICE = "price";
    public static final String COLUMN_BOOK_PICTURE = "book_picture";
    public static final String COLUMN_BOOK_TOTAL = "price_tol";

    public static final String TABLE_FAVORITE = "favorites";
    public static final String TABLE_SHOPCART = "shopcart";


    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT);";

    public static final String CREATE_TABLE_BOOKS = "CREATE TABLE " + TABLE_BOOK + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOOK_NAME + " TEXT,"
            + COLUMN_BOOK_PRICE + " INTEGER,"
            + COLUMN_BOOK_PICTURE + " INTEGER);";

    public static final String CREATE_TABLE_FAVORITE = "CREATE TABLE " + TABLE_FAVORITE + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOOK_NAME + " TEXT,"
            + COLUMN_BOOK_PRICE + " INTEGER,"
            + COLUMN_BOOK_PICTURE + " INTEGER);";

    public static final String CREATE_TABLE_SHOPCART = "CREATE TABLE " + TABLE_SHOPCART + "("
            + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOOK_NAME + " TEXT,"
            + COLUMN_BOOK_PRICE + " INTEGER,"
            + COLUMN_BOOK_PICTURE + " INTEGER,"
            + COLUMN_BOOK_TOTAL + " INTEGER DEFAULT 0);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BOOKS);
        db.execSQL(CREATE_TABLE_FAVORITE);
        db.execSQL(CREATE_TABLE_SHOPCART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPCART);
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(TABLE_USER, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }

    public boolean getUser(String email, String pass){
        //HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "select * from  " + TABLE_USER + " where " +
                COLUMN_EMAIL + " = " + "'"+email+"'" + " and " + COLUMN_PASS + " = " + "'"+pass+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }

    public Cursor searchUser(String email) { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor= db.rawQuery("SELECT * FROM table01 WHERE _id="+rowId,null);
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_USER +" WHERE email='" + email + "'", null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }

    public boolean addBook(String bookname, int price ,int book_picture){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, bookname);
        contentValues.put(COLUMN_BOOK_PRICE, price);
        contentValues.put(COLUMN_BOOK_PICTURE, book_picture);

        long result = db.insert(TABLE_BOOK , null , contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor allBook(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_BOOK, null);
        return c;
    }

    public Cursor getBook(long rowId) throws SQLException { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_BOOK + " WHERE _id=" + rowId, null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }

    public boolean addFavorite(String bookname, int price ,int book_picture){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, bookname);
        contentValues.put(COLUMN_BOOK_PRICE, price);
        contentValues.put(COLUMN_BOOK_PICTURE, book_picture);

        long result = db.insert(TABLE_FAVORITE, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor allFavorite(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_FAVORITE, null);
        return c;
    }

    public Cursor getFavorite(long rowId) throws SQLException { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_FAVORITE + " WHERE _id=" + rowId, null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }

    public void delFavorite(long id) {  //刪除指定的資料
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVORITE, COLUMN_ID + "=" + id, null);
    }

    public boolean addShopcart(String bookname, int price ,int book_picture){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BOOK_NAME, bookname);
        contentValues.put(COLUMN_BOOK_PRICE, price);
        contentValues.put(COLUMN_BOOK_PICTURE, book_picture);

        long result = db.insert(TABLE_SHOPCART, null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor allShopcart(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_SHOPCART, null);
        return c;
    }

    public void delShopcart(long id) {  //刪除指定的資料
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPCART, COLUMN_ID + "=" + id, null);
    }

    public Cursor getShopcart(long rowId) throws SQLException { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_SHOPCART + " WHERE _id=" + rowId, null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }

    public void totalPrice(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_SHOPCART + " set " + COLUMN_BOOK_TOTAL + " = (SELECT SUM(price) FROM " + TABLE_SHOPCART + " ) ");
    }

    public Cursor searchBook(String name) { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor= db.rawQuery("SELECT * FROM table01 WHERE _id="+rowId,null);
        //Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_BOOK +" WHERE name='" + name + "'", null);
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_BOOK + " WHERE name LIKE '%" + name + "%'", null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }

    public Cursor searchFavorite(String name) { // 查詢指定 ID 的資料
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor= db.rawQuery("SELECT * FROM table01 WHERE _id="+rowId,null);
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_FAVORITE +" WHERE name='" + name + "'", null);
        if (cursor.getCount()>0)
            cursor.moveToFirst();

        return cursor;
    }
}