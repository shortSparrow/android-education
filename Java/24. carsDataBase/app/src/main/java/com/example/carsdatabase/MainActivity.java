package com.example.carsdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import data.DatabaseHandler;
import model.Car;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
//        databaseHandler.addCar(new Car("Toyota", "20 000$"));
//        databaseHandler.addCar(new Car("BMW", "30 000$"));
//        databaseHandler.addCar(new Car("Tesla", "40 000$"));
//        databaseHandler.addCar(new Car("Nisan", "10 000$"));
//
//        databaseHandler.addCar(new Car("Mazda", "12 000$"));
//        databaseHandler.addCar(new Car("KIA", "9 000$"));
//        databaseHandler.addCar(new Car("Shkoda", "15 000$"));
//
        List<Car> carList = databaseHandler.getCarrList();

        Log.d("carsCount", String.valueOf(databaseHandler.getCarsCount()));
        for (Car car : carList) {
            Log.d("CarInfo ",
                    "id: " + car.getId() + ", "
                    + "name: " + car.getName() + ", "
                    + "price: " + car.getPrice());
            //  id: 1, name: Toyota, price: 20 000$ ....
        }

//        databaseHandler.deleteCar(carList.get(0)); // delete first db item


//        Car car = databaseHandler.getCar(7);
//        Log.d("CarInfo ",
//                "id: " + car.getId() + ", "
//                        + "name: " + car.getName() + ", "
//                        + "price: " + car.getPrice());
//
//        car.setPrice("42 000$");
//        int updateCarId = databaseHandler.updateCar(car);
//        Car updatedCar = databaseHandler.getCar(updateCarId);
//        Log.d("CarInfo ",
//                "id: " + updatedCar.getId() + ", "
//                        + "name: " + updatedCar.getName() + ", "
//                        + "price: " + updatedCar.getPrice());
    }
}