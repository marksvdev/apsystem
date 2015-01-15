package com.dtu.s113604.apsystem.mocked_hardware;

/**
 * Created by marksv on 09/01/15.
 */
public class MCGM {
    private static int battery = 30;
    private static int batteryDecay = 0;

    private int egv;
    private boolean uptrend = false;

    private static MCGM instance = null;

    protected MCGM() {

    }
    public static MCGM getInstance() {
        if(instance == null) {
            instance = new MCGM();

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

    private final double PERIOD = 50;
    private final double SCALE = 60;

    private int pos = 0;

    public int getEgv() {
        pos++;
        egv = (int)(Math.sin(pos * 2 * Math.PI / PERIOD) * (SCALE / 2) + (SCALE / 2) + (135 / 2));
        return egv;
    }
}
