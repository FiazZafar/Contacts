package com.example.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ContactdbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Contacts_Db";
    private static final int DATABASE_VERSION = 1; // Changed Integer to int for simplicity
    private static final String CONTACT_TABLE = "MyContacts";
    private static final String TABLE_ID = "Id";
    private static final String TABLE_NAME = "name";
    private static final String TABLE_CONTACT = "phone_no";
    private static final String TABLE_IMAGE = "ImageRes";

    public ContactdbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +
                CONTACT_TABLE + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABLE_NAME + " TEXT NOT NULL, " +
                TABLE_CONTACT + " TEXT NOT NULL UNIQUE, " +
                TABLE_IMAGE + " BLOB " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        onCreate(db);
    }

    public void insertData(Bitmap image,ContactsList contactsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TABLE_NAME, contactsList.getName());
            cv.put(TABLE_CONTACT, contactsList.getNumber());
            cv.put(TABLE_IMAGE, bitmapToByteArray(image));
            db.insert(CONTACT_TABLE, null, cv);
        } finally {
//            db.close();
        }
    }

    public ArrayList<ContactsList> fetchData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<ContactsList> contactsListsArr = new ArrayList<>();

        try {
            cursor = db.rawQuery("SELECT * FROM " + CONTACT_TABLE, null);
            while (cursor.moveToNext()) {
                ContactsList contactsList = new ContactsList();
                contactsList.id = cursor.getInt(0); // ID
                contactsList.name = cursor.getString(1); // Name
                contactsList.number = cursor.getString(2); // Phone Number

                byte[] imgRes = cursor.getBlob(3);
                if (imgRes != null){
                    Bitmap bitmap = new BitmapFactory().decodeByteArray(imgRes,0,imgRes.length);
                    contactsList.img = bitmap;
                }

                contactsListsArr.add(contactsList);
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return contactsListsArr;
    }

    public void updateDatas(Bitmap image,ContactsList contactsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TABLE_NAME, contactsList.getName());
            cv.put(TABLE_CONTACT, contactsList.getNumber());
            cv.put(TABLE_IMAGE,bitmapToByteArray(image));

            db.update(CONTACT_TABLE, cv, TABLE_ID + " = ?", new String[]{String.valueOf(contactsList.id)});
        } finally {
            db.close();
        }
    }

    public void deleteData(ContactsList contactsList) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(CONTACT_TABLE, TABLE_ID + " = ?", new String[]{String.valueOf(contactsList.id)});
        } finally {
            db.close();
        }
    }
    private byte[] bitmapToByteArray(Bitmap bitmap){
        if (bitmap == null){
            return null;
        }{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
}
