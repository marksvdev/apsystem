package com.dtu.s113604.apsystem.ap_system.cgm_module;

import android.util.Log;

import com.dtu.s113604.apsystem.mocked_hardware.MCGM;

import java.util.Random;

/**
 * Created by marksv on 06/12/14.
 */
public class CGM implements ICGM {

    private static String TAG = "CGM";

    private String info = "Dexcom G4 Platinum";

    private Random rand = new Random();

    @Override
    public int getGlucoseValue(String bleAddress, String SN) {
        Log.i(TAG, "Waiting for next reading...(DEBUG: 2 sec)");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return MCGM.getInstance().getEgv();
    }

    @Override
    public String getInfo(String bleAddress, String SN) {
        return info;
    }

    @Override
    public int getBattery(String bleAddress, String SN) {
        return MCGM.getInstance().getBattery();
    }
}
