package com.dtu.s113604.apsystem.ap_system.pump_module;

import java.util.Random;

/**
 * Created by marksv on 06/12/14.
 */
public class Pump implements IPump {

    private String info = "some info";
    private Random rand = new Random();

    @Override
    public String doseInsulin(String sn, int dose) {
        return "SN: " + sn + ", HORMONE: INSULIN" + ", DOSE: " + dose + ", STATUS: OK";
    }

    @Override
    public String doseGlucagon(String sn, int dose) {
        return "SN: " + sn + ", HORMONE: GLUCAGON" + ", DOSE: " + dose + ", STATUS: OK";
    }

    @Override
    public String getInfo(String pumpSerialNumber) {
        return info;
    }

    @Override
    public int getBatteryInsulinPump(String insulinPumpSerialNumber) {
        return rand.nextInt((95 - 40) + 1) + 40;
    }

    @Override
    public int getBatteryGlucagonPump(String glucagonPumpSerialNumber) {
        return rand.nextInt((100 - 67) + 1) + 67;
    }
}
