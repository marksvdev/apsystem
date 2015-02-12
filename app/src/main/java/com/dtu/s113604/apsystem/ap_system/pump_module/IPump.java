package com.dtu.s113604.apsystem.ap_system.pump_module;

import com.dtu.s113604.apsystem.ap_system.models2.Battery;
import com.dtu.s113604.apsystem.ap_system.models2.Dose;

/**
 * Created by marksv on 06/12/14.
 */
public interface IPump {
    Dose doseInsulin(String insulinPumpSerialNumber, int dose);
    Dose doseGlucagon(String glucagonPumpSerialNumber, int dose);
    String getInfo(String pumpSerialNumber);
    Battery getBatteryInsulinPump(String insulinPumpSerialNumber);
    Battery getBatteryGlucagonPump(String glucagonPumpSerialNumber);
}
