package com.dtu.s113604.apsystem.ap_system.pump_module;

/**
 * Created by marksv on 06/12/14.
 */
public interface IPump {
    String doseInsulin(String insulinPumpSerialNumber, int dose);
    String doseGlucagon(String glucagonPumpSerialNumber, int dose);
    String getInfo(String pumpSerialNumber);
    int getBatteryInsulinPump(String insulinPumpSerialNumber);
    int getBatteryGlucagonPump(String glucagonPumpSerialNumber);
}
