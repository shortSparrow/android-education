package com.example.studentsdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import data.DatabaseHandler;
import model.Student;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

//        Student student1 = new Student("Senya", "Volkov", "computer science", 82f);
//        databaseHandler.setStudent(student1);
//
//        Student student2 = new Student("Dmytro", "Korysh", "Polygraphy", 92f);
//        databaseHandler.setStudent(student2);
//
//        Student student3 = new Student("Yana", "Shever", "Polygraphy", 73.2f);
//        databaseHandler.setStudent(student3);
//
//        Student student4 = new Student("Anna", "Moryshko", "Multymedia", 74.7f);
//        databaseHandler.setStudent(student4);
//
//        Student student5 = new Student("Mykyta", "Pol", "Multymedia", 64.7f);
//        databaseHandler.setStudent(student5);

//        databaseHandler.clearStudentDatabase();

        List<Student> studentList = databaseHandler.getStudents();
        for (Student student : studentList) {

            Log.d("studentInfo",
                    "id: " + String.valueOf(student.getId()) + ", " +
                            "first name: " + student.getFirstName() + ", " +
                            "second name: " + student.getSecondName() + ", " +
                            "faculty: " + student.getFaculty() + ", " +
                            "GPA: " + String.valueOf(student.getGpa())
            );
        }
    }
}