package com.dtu.s113604.apsystem.ap_system.cgm_module;

import android.util.Log;

import java.util.Random;

/**
 * Created by marksv on 06/12/14.
 */
public class CGM implements ICGM {

    private static String TAG = "CGM";

    private String info = "Dexcom G4 Platinum";

    @Override
    public int getGlucoseValue(String bleAddress, String SN) {
        Log.i(TAG, "Waiting for next reading...(DEBUG: 10 sec)");
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Random rand = new Random();

        return rand.nextInt((10 - 1) + 1) + 1;
    }

    @Override
    public String getInfo(String bleAddress, String SN) {
        return info;
    }

    @Override
    public int getBattery(String bleAddress, String SN) {
        return 80;
    }
}
