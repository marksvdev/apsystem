package com.dtu.s113604.apsystem.ap_system;

import com.dtu.s113604.apsystem.ap_system.cgm_module.CGM;
import com.dtu.s113604.apsystem.ap_system.cgm_module.ICGM;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.ControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.IControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.pump_module.IPump;
import com.dtu.s113604.apsystem.ap_system.pump_module.Pump;
import com.dtu.s113604.apsystem.models.APStateModel;

/**
 * Created by marksv on 06/12/14.
 */
public class StepController implements Runnable {

    APStateModel state;

    public StepController(APStateModel state) {
        this.state = state;
    }

    @Override
    public void run() {
        ICGM cgm = new CGM();
        IControlAlgorithm control = new ControlAlgorithm();
        IPump pump = new Pump();

        /*
        *   CGM
        */

        String CGMBLEAddress = state.getDeviceData().getCGMReceiverBLEAddress();

        int newGlucoseValue = cgm.getGlucoseValue(CGMBLEAddress);
        state.setCurrentGlucoseValue(newGlucoseValue);

        /*
        *   Control Algorithm
        */

        int insulinDose = control.calculateInsulinDose(state);
        int glucagonDose = control.calculateGlucagonDose(state);
        state.getDoseData().setCurrentInsulinDose(insulinDose);
        state.getDoseData().setCurrentGlucagonDose(glucagonDose);

        /*
        *   Pump(s)
        */

        String insulinPumpSN = state.getDeviceData().getInsulinPumpSerialNumber();
        String glucagonPumpSN = state.getDeviceData().getGlucagonPumpSerialNumber();

        String insulinPumpResponse = pump.doseInsulin(insulinPumpSN, insulinDose);
        String glucagonPumpResponse = pump.doseGlucagon(glucagonPumpSN, glucagonDose);
    }
}
