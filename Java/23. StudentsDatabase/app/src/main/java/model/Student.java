package model;

public class Student {
    public int id;
    public String firstName;
    public String secondName;
    public String faculty;
    public float gpa;

    public Student() {
    }

    public Student(int id, String firstName, String secondName, String faculty, float gpa) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.faculty = faculty;
        this.gpa = gpa;
    }

    public Student(String first_name, String second_name, String faculty, float gpa ) {
        this.firstName = first_name;
        this.secondName = second_name;
        this.faculty = faculty;
        this.gpa = gpa;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
