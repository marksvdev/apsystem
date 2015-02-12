package com.dtu.s113604.apsystem.ap_system.pump_module;

import com.dtu.s113604.apsystem.ap_system.models2.Battery;
import com.dtu.s113604.apsystem.ap_system.models2.Dose;
import com.dtu.s113604.apsystem.mocked_hardware.MGlucagonPump;
import com.dtu.s113604.apsystem.mocked_hardware.MInsulinPump;

import java.util.Random;

import utils.DateTime;

/**
 * Created by marksv on 06/12/14.
 */
public class Pump implements IPump {

    private String info = "some info";
    private Random rand = new Random();

    @Override
    public Dose doseInsulin(String sn, int dose) {
//         "SN: " + sn + ", HORMONE: INSULIN" + ", DOSE: " + dose + ", STATUS: OK";
        return new Dose(DateTime.now(), dose);
    }

    @Override
    public Dose doseGlucagon(String sn, int dose) {
//        return "SN: " + sn + ", HORMONE: GLUCAGON" + ", DOSE: " + dose + ", STATUS: OK";
        return new Dose(DateTime.now(), dose);
    }

    @Override
    public String getInfo(String pumpSerialNumber) {
        return info;
    }

    @Override
    public Battery getBatteryInsulinPump(String insulinPumpSerialNumber) {
        return new Battery(DateTime.now(), MInsulinPump.getInstance().getBattery());
    }

    @Override
    public Battery getBatteryGlucagonPump(String glucagonPumpSerialNumber) {
        return new Battery(DateTime.now(), MGlucagonPump.getInstance().getBattery());
    }
}
