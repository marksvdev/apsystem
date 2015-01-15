package com.dtu.s113604.apsystem.mocked_hardware;

/**
 * Created by marksv on 09/01/15.
 */
public class MInsulinPump {
    private static int battery = 30;
    private static int batteryDecay = 0;

    private static MInsulinPump instance = null;

    protected MInsulinPump() {
        // Exists only to defeat instantiation.
    }
    public static MInsulinPump getInstance() {
        if(instance == null) {
            instance = new MInsulinPump();
        }
        return instance;
    }


    public static int getBattery() {
        if (battery <= 0) {
            battery = 100;
        }

        if (batteryDecay >= 2) {
            batteryDecay = 0;
            battery -= 5;
            return battery;
        }

        batteryDecay++;
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }
}
