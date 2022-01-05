package util;

public class Constants {
    static public final String PASSENGER_ROLE = "passenger";
    static public final String PASSENGER_DB_NAME = "passengers";
    static public final String PASSENGER_TAG = "PassengerMapsActivity";

    static public final String DRIVER_ROLE = "driver";
    static public final String DRIVER_DB_NAME = "driver";
    static public final String DRIVER_TAG = "DriverMapsActivity";

    public Constants() {
    }

    static public String getBDNameByRole(String role) {
        if (role == PASSENGER_ROLE) {
            return PASSENGER_DB_NAME;
        }

        if (role == DRIVER_ROLE) {
            return DRIVER_DB_NAME;
        }

        return null;
    }

    static public String getTAGByRole(String role) {
        if (role == PASSENGER_ROLE) {
            return PASSENGER_TAG;
        }

        if (role == DRIVER_ROLE) {
            return DRIVER_TAG;
        }

        return null;
    }


}
