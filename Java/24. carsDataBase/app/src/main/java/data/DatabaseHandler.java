package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Utils.Util;
import model.Car;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL - Structured query language
        String CREATE_CARS_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_PRICE + " TEXT" + ")";

        db.execSQL(CREATE_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // на обновление таблицы мы должны удалить старую и записать новую
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    // CRUD - crate read update delete
    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, car.getName());
        contentValues.put(Util.KEY_PRICE, car.getPrice());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public Car getCar(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PRICE},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        Car car = new Car();
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                car = new Car(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));
            } finally {
                cursor.close();
            }
        }



        return car;
    }

    public List<Car> getCarrList() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Car> carList = new ArrayList<>();

        String selectAllCars = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllCars, null);

        // moveToFirst возвращает true если cursor содеожит какие-то заипси
        if (cursor.moveToFirst()) {
           try {
               do{
                   Car car = new Car();
                   car.setId(Integer.parseInt(cursor.getString(0)));
                   car.setName(cursor.getString(1));
                   car.setPrice(cursor.getString(2));

                   carList.add(car);
               } while (cursor.moveToNext());
           } finally {
               cursor.close();
           }
        }
        
        return carList;
    }

    public int updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_NAME, car.getName());
        contentValues.put(Util.KEY_PRICE, car.getPrice());

        db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?", new String[]{String.valueOf(car.getId())});
        return car.getId();
    }

    public void deleteCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME,Util.KEY_ID + "=?", new String[]{String.valueOf(car.getId())});
        db.close();
    }

    public int getCarsCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        if (cursor != null) {
            try {
                count = cursor.getCount();
            } finally {
                cursor.close();
            }

        }
        return count;

    }

}
