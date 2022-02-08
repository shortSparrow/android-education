package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Utils.Util;
import model.Student;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_DB = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_FIRST_NAME + " TEXT,"
                + Util.KEY_SECOND_NAME + " TEXT,"
                + Util.KEY_FACULTY + " TEXT,"
                + Util.KEY_GPA + " TEXT" + ")";

        db.execSQL(CREATE_STUDENT_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void setStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_FIRST_NAME, student.getFirstName());
        values.put(Util.KEY_SECOND_NAME, student.getSecondName());
        values.put(Util.KEY_FACULTY, student.getFaculty());
        values.put(Util.KEY_GPA, student.getGpa());

        db.insert(Util.TABLE_NAME, null, values);
        db.close();
    }

    public List<Student> getStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Student> studentList = new ArrayList<>();
        String selectAllUsers = "SELECT * FROM " + Util.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectAllUsers, null);

        if (cursor.moveToFirst()) {
            try {
                do {

//                Student student = new Student();
//                student.setId(Integer.parseInt(cursor.getString(0)));
//                student.setFirstName(cursor.getString(1));
//                student.setSecondName(cursor.getString(2));
//                student.setFaculty(cursor.getString(3));
//                student.setGpa(cursor.getString(4));


                    Student student = new Student(
                            (Integer.parseInt(cursor.getString(0))), // id
                            cursor.getString(1), // first_name
                            cursor.getString(2), // first_name
                            cursor.getString(3), // faculty
                            Float.parseFloat(cursor.getString(4)) // GPA
                    );

                    studentList.add(student);
                } while (cursor.moveToNext());
            } finally {
                cursor.close();
            }
        }

        return studentList;
    }

    public void clearStudentDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }


}
