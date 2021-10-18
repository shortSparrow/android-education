package com.example.javaoop_inheritance;

public class Person {
    int age;
    String firstName;
    String secondName;
    boolean isDeaf;
    boolean isChild;

    public Person(int age, String firstName, String secondName) {
        this.age = age;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Person(boolean isDeaf) {
        this.isDeaf = isDeaf;
    }

    public Person(int age, boolean isChild) {
        this.age = age;
        this.isChild = isChild;
    }


    public String  introduceMySelf() {
        return "Hello, I'm " + firstName + " " + secondName + ". And I'm " + age + " years old";
    }

    public String introduceMySelf(boolean isDeaf) {
        if (isDeaf) {
            return "Hello, I'm " + firstName + " " + secondName + ". And I'm deaf";
        }
        return "Hello, I'm " + firstName + " " + secondName + ". And I'm not deaf";
    }

    public String introduceMySelf(int age) {
        if (age < 16) {
            return "Hello, I'm " + firstName + " " + secondName + ". And I'm child";
        }
        return "Hello, I'm " + firstName + " " + secondName + ". And I'm not child";
    }
}
