package com.dtu.s113604.apsystem.ap_system.pump_module;

/**
 * Created by marksv on 06/12/14.
 */
public class Pump implements IPump {

    private String info = "some info";

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
}
