package com.dtu.s113604.apsystem.ap_system;

import android.content.Context;
import android.util.Log;

import com.dtu.s113604.apsystem.ap_system.cgm_module.CGM;
import com.dtu.s113604.apsystem.ap_system.cgm_module.ICGM;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.ControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.IControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.pump_module.IPump;
import com.dtu.s113604.apsystem.ap_system.pump_module.Pump;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource;
import com.dtu.s113604.apsystem.ap_system.models.*;
import com.dtu.s113604.apsystem.system_monitor.SystemMonitor;


import org.w3c.dom.Document;

import utils.Broadcast;
import utils.DateTime;
import utils.MSGCode;
import utils.StateManager;
import utils.StateProps;
import utils.XMLManager;

/**
 * Created by marksv on 06/12/14.
 */
public class StepController implements Runnable {

    public static String TAG = "StepController";
    public static boolean isRunning = false;

    private Context context;

    public StepController(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        isRunning = true;

        while(isRunning) {
            executeSteps();
        }
    }

    private void executeSteps() {
        ICGM cgm = new CGM();
        IControlAlgorithm control = new ControlAlgorithm();
        IPump pump = new Pump();

        /*
        *   CGM
        */

        String CGMBLEAddress = getState().getDeviceData().getCGMBLEAddress();
        String CGMSN = getState().getDeviceData().getCGMSerialNumber();
        Log.i(TAG, "CGMBLEAddress = " + CGMBLEAddress);
        Log.i(TAG, "CGMSN = " + CGMSN);

        int newGlucoseValue = cgm.getGlucoseValue(CGMBLEAddress, CGMSN);
        Log.i(TAG, "newGlucoseValue = " + newGlucoseValue);

        int batteryCGM = cgm.getBattery(CGMBLEAddress, CGMSN);

        getState().setCurrentGlucose(newGlucoseValue);
        getState().setCurrentGlucoseDateTime(DateTime.now());
        getState().getDeviceData().setCGMBattery(batteryCGM);

        // Update View
        setLatestEGV(newGlucoseValue);
        setCGMBattery(batteryCGM);

        /*
        *   Control Algorithm
        */

        int insulinDose = control.calculateInsulinDose(getState());
        int glucagonDose = control.calculateGlucagonDose(getState());

        Log.i(TAG, "insulinDose = " + insulinDose);
        Log.i(TAG, "glucagonDose = " + glucagonDose);

        // HOLISTIC CHECKS GO HERE

        getState().getDoseData().setCurrentInsulinDose(insulinDose);
        getState().getDoseData().setCurrentGlugaconDose(glucagonDose);

        /*
        *   Pump(s)
        */

        String insulinPumpSN = getState().getDeviceData().getInsulinPumpSerialNumber();
        String glucagonPumpSN = getState().getDeviceData().getGlucagonPumpSerialNumber();

        Log.i(TAG, "insulinPumpSN = " + insulinPumpSN);
        Log.i(TAG, "glucagonPumpSN = " + glucagonPumpSN);

        String insulinPumpResponse = pump.doseInsulin(insulinPumpSN, insulinDose);
        String glucagonPumpResponse = pump.doseGlucagon(glucagonPumpSN, glucagonDose);

        Log.i(TAG, "insulinPumpResponse = " + insulinPumpResponse);
        Log.i(TAG, "glucagonPumpResponse = " + glucagonPumpResponse);

        int batteryPumpInsulin = pump.getBatteryInsulinPump(insulinPumpSN);
        int batteryPumpGlucagon = pump.getBatteryGlucagonPump(glucagonPumpSN);

        // Save battery to state
        getState().getDeviceData().setInsulinPumpBattery(batteryPumpInsulin);
        getState().getDeviceData().setGlucagonPumpBattery(batteryPumpGlucagon);

        // Update battery to UI
        setBatteryPumpInsulin(batteryPumpInsulin);
        setBatteryPumpGlucagon(batteryPumpGlucagon);

        /**
         *  Check with System Monitor
         */

        (new Thread(new SystemMonitor(context, getState()))).start();

        /*
        *   Save to Datastore
        */

        IAPStateDataSource dataSource = new APStateDataSource(context);
        dataSource.save(getState());
    }

    private void setLatestEGV(int value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_EGV.toString(), String.valueOf(value));
    }

    private void setCGMBattery(int value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_CGM.toString(), String.valueOf(value));
    }

    private void setBatteryPumpInsulin(int value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_PUMP_INSULIN.toString(), String.valueOf(value));
    }
    private void setBatteryPumpGlucagon(int value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_PUMP_GLUCAGON.toString(), String.valueOf(value));
    }

    private APStateModel getState() {
        return StateManager.getInstance().getState();
    }

}
