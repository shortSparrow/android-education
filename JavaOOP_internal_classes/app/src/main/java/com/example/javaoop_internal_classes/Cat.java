package com.example.javaoop_internal_classes;

import android.util.Log;

public class Cat {
    static int countOfInstance = 0;
    int  age;
    String helloText;
    CatMood catMood;

    // static class
    static class CountResetter {
        boolean isResetCount = false;

        public CountResetter() {
            if (Cat.countOfInstance >= 100) {
                isResetCount = true;
            }

            if (isResetCount) {
                resetCount();
            }
        }

         public void resetCount() {
            Cat.countOfInstance = 0;
        }

    }

    public Cat(int age) {
        countOfInstance++;
        this.age = age;
        catMood = new CatMood();

        switch (catMood.levelOfMood) {
            case 100:
                helloText = "Hello i'm happy cat :)" + " My age is " + age;
                break;
            case 50:
                helloText = "Hello i'm cat" + " My age is " + age;
                break;
            case 20:
                helloText = "Hello i'm sad cat :(" + " My age is " + age;
                break;
            default:
                helloText = "Hello i'm sick cat" + " My age is " + age;
        }
    }

    // Inner class. Если нужно создать класс, который не сузествует без друкого класса.
    // Например класс настроение кошки (CatMood) не существует без класса Cat.
    class CatMood {
        int levelOfMood;

        CatMood() {
            if (Cat.this.age < 2) {
                levelOfMood = 100;
            } else if(Cat.this.age >=2 && Cat.this.age < 7) {
                levelOfMood = 50;
            } else if (Cat.this.age >= 7) {
                levelOfMood = 20;
            }
        }
    }


    // local class
    public void catchMouse(int weight) {
        class Mouse {
            String color;
            int weight;

            public Mouse(int weight, String color) {
                this.weight = weight;
                this.color = color;
            }

            String mouseVoice() {
                return "Pi-pi-pi";
            }
        }

        Mouse mouse = new Mouse(weight, "black");

        if (mouse.weight <= 2) {
            Log.i("CatSay", "I will eat you. " + mouse.mouseVoice());
        }  else {
            Log.i("CatSay", "I afraid you. Mouse weight is " + mouse.weight);
        }
    }

    public void talk() {
        Log.i("talk()", helloText);
    }
}
