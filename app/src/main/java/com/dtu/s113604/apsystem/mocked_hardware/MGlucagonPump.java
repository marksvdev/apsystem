package com.dtu.s113604.apsystem.mocked_hardware;

/**
 * Created by marksv on 09/01/15.
 */
public class MGlucagonPump {
    private static int battery = 75;
    private static int batteryDecay = 0;

    private static MGlucagonPump instance = null;

    protected MGlucagonPump() {
        // Exists only to defeat instantiation.
    }
    public static MGlucagonPump getInstance() {
        if(instance == null) {
            instance = new MGlucagonPump();
        }
        return instance;
    }


    public static int getBattery() {
        if (battery <= 0) {
            battery = 100;
        }

        if (batteryDecay >= 2) {
            batteryDecay = 0;
            battery -= 3;
            return battery;
        }

        batteryDecay++;
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
