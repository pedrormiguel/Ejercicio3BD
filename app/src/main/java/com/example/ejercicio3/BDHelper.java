package com.example.ejercicio3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BDHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "StudentDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "STUDENT";
    private static final String IdColumn = "id";
    private static final String NameColum = "name";
    private static final String PicturePathColum = "picturePath";
    private static final String CityBornColum = "city";
    private static final String NumberIdentifierColum = "numberIdentifier";
    private static final String CreativeExpressionColum = "creativeExpression";
    private static final String ImageColum = "image";

    private ByteArrayOutputStream objectByteOut;
    private byte[] imageBytes;

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQuery = "CREATE TABLE " + TABLE_NAME;
        String firstAttribute = "(id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        String secondAttribute = "name        TEXT, ";
        String thirdAttribute = "picturePath TEXT, ";
        String fourthAttribute = "city    TEXT, ";
        String fithAttribute = "numberIdentifier  TEXT, ";
        String sixthAttribute = "image  BLOB, ";
        String seventhAttribute = "creativeExpression TEXT)";

        String finalQuery = sqlQuery + firstAttribute + secondAttribute +
                thirdAttribute + fourthAttribute + fithAttribute  + seventhAttribute;

        db.execSQL(finalQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean create(Estudiante student) {
        ContentValues values = new ContentValues();
//        Bitmap imageToStoreBitMap = student.image;
//
//        objectByteOut = new ByteArrayOutputStream();
//        imageToStoreBitMap.compress(Bitmap.CompressFormat.JPEG,100,objectByteOut);
//        imageBytes = objectByteOut.toByteArray();


        values.put(NameColum, student.nombre);
        values.put(PicturePathColum, student.foto);
        values.put(CityBornColum, student.ciudad);
        values.put(NumberIdentifierColum, student.matricula);
        values.put(CreativeExpressionColum, student.exprecion);
       // values.put(ImageColum,imageBytes);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME;

        int recordCount = db.rawQuery(sql, null).getCount();

        db.close();

        return recordCount;
    }

    public List<Estudiante> read() {

        List<Estudiante> recordList = new ArrayList<Estudiante>();

        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

       // byte[] byteImage = cursor.getBlob(6);

       // Bitmap bitmapImage = BitmapFactory.decodeByteArray(byteImage,0,byteImage.length);

        if (cursor.moveToFirst()) {
            do {
                recordList.add(
                        new Estudiante(
                                Integer.parseInt( cursor.getString(cursor.getColumnIndex("id")) ),
                                cursor.getString(cursor.getColumnIndex(NameColum)),
                                cursor.getString(cursor.getColumnIndex(NumberIdentifierColum)),
                                cursor.getString(cursor.getColumnIndex(PicturePathColum)),
                                cursor.getString(cursor.getColumnIndex(CityBornColum)),
                                cursor.getString(cursor.getColumnIndex(CreativeExpressionColum))
                              //  bitmapImage
                        )
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return recordList;
    }

    public Estudiante readSingleRecord(int studentId) {

        Estudiante objectStudent = null;

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));

            String name = cursor.getString(cursor.getColumnIndex(NameColum));

            String pathImage = cursor.getString(cursor.getColumnIndex(PicturePathColum));

            String city = cursor.getString(cursor.getColumnIndex(CityBornColum));

            String numberId = cursor.getString(cursor.getColumnIndex(NumberIdentifierColum));

            String expression = cursor.getString(cursor.getColumnIndex(CreativeExpressionColum));

           // objectStudent = new Estudiante(id, name, pathImage, city, numberId, expression);

        }

        cursor.close();
        db.close();

        return objectStudent;
    }

    public boolean update(Estudiante objectStudent) {

        ContentValues values = new ContentValues();

        values.put(NameColum, objectStudent.nombre);
        values.put(PicturePathColum, objectStudent.foto);
        values.put(CityBornColum, objectStudent.ciudad);
        values.put(NumberIdentifierColum, objectStudent.matricula);
        values.put(CreativeExpressionColum, objectStudent.exprecion);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(objectStudent.id) };

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update(TABLE_NAME, values, where, whereArgs) > 0;

        db.close();
        return updateSuccessful;
    }

    public boolean delete(int studenId) {

        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();

        deleteSuccessful = db.delete(TABLE_NAME, "id ='" + studenId + "'", null) > 0;

        db.close();
        return deleteSuccessful;
    }

    public void storeImage(){

    }
}
