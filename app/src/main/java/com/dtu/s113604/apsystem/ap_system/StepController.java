package com.dtu.s113604.apsystem.ap_system;

import android.content.Context;
import android.util.Log;

import com.dtu.s113604.apsystem.activities.MainActivity;
import com.dtu.s113604.apsystem.activities.ViewWrapper;
import com.dtu.s113604.apsystem.ap_system.cgm_module.CGM;
import com.dtu.s113604.apsystem.ap_system.cgm_module.ICGM;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.ControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.IControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.models.APStateModel;
import com.dtu.s113604.apsystem.ap_system.pump_module.IPump;
import com.dtu.s113604.apsystem.ap_system.pump_module.Pump;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource;
import com.dtu.s113604.apsystem.system_monitor.SystemMonitor;

import utils.Broadcast;
import utils.DateTime;
import utils.MSGCode;

/**
 * Created by marksv on 06/12/14.
 */
public class StepController extends Thread {

    public static String TAG = "StepController";
    public static boolean isRunning = false;

    private Context context;

    private APStateModel state;

    public StepController(Context context) {
        this.context = context;

        state = createNewState();

        ((MainActivity)context).updateView(state.makeWrapper());
    }

    @Override
    public void run() {
        isRunning = true;

        while(isRunning) {

            if (newState) {
                state.makeUnWrap(getWrapper());
                newState = false;
            }

            executeSteps();
        }
    }

    private APStateModel createNewState() {
        IAPStateDataSource dataSource = new APStateDataSource(context);
        APStateModel loadedState = dataSource.load();

        if (loadedState != null) {
            return loadedState;
        } else {

            throw new RuntimeException("Resource not found");
        }
    }

    private void executeSteps() {
        ICGM cgm = new CGM();
        IControlAlgorithm control = new ControlAlgorithm();
        IPump pump = new Pump();

        /*
        *   CGM
        */

        String CGMBLEAddress = state.getDeviceData().getCGMBLEAddress();
        String CGMSN = state.getDeviceData().getCGMSerialNumber();
        Log.i(TAG, "CGMBLEAddress = " + CGMBLEAddress);
        Log.i(TAG, "CGMSN = " + CGMSN);

        int newGlucoseValue = cgm.getGlucoseValue(CGMBLEAddress, CGMSN);
        Log.i(TAG, "newGlucoseValue = " + newGlucoseValue);

        int batteryCGM = cgm.getBattery(CGMBLEAddress, CGMSN);

        state.setCurrentGlucose(newGlucoseValue);
        state.setCurrentGlucoseDateTime(DateTime.now());
        state.getDeviceData().setCGMBattery(batteryCGM);

        // Update View
        setLatestEGV(newGlucoseValue);
        setCGMBattery(batteryCGM);

        /*
        *   Control Algorithm
        */

        int insulinDose = control.calculateInsulinDose(state);
        int glucagonDose = control.calculateGlucagonDose(state);

        Log.i(TAG, "insulinDose = " + insulinDose);
        Log.i(TAG, "glucagonDose = " + glucagonDose);

        // HOLISTIC CHECKS GO HERE

        state.getDoseData().setCurrentInsulinDose(insulinDose);
        state.getDoseData().setCurrentGlugaconDose(glucagonDose);

        /*
        *   Pump(s)
        */

        String insulinPumpSN = state.getDeviceData().getInsulinPumpSerialNumber();
        String glucagonPumpSN = state.getDeviceData().getGlucagonPumpSerialNumber();

        Log.i(TAG, "insulinPumpSN = " + insulinPumpSN);
        Log.i(TAG, "glucagonPumpSN = " + glucagonPumpSN);

        String insulinPumpResponse = pump.doseInsulin(insulinPumpSN, insulinDose);
        String glucagonPumpResponse = pump.doseGlucagon(glucagonPumpSN, glucagonDose);

        Log.i(TAG, "insulinPumpResponse = " + insulinPumpResponse);
        Log.i(TAG, "glucagonPumpResponse = " + glucagonPumpResponse);

        int batteryPumpInsulin = pump.getBatteryInsulinPump(insulinPumpSN);
        int batteryPumpGlucagon = pump.getBatteryGlucagonPump(glucagonPumpSN);

        // Save battery to state
        state.getDeviceData().setInsulinPumpBattery(batteryPumpInsulin);
        state.getDeviceData().setGlucagonPumpBattery(batteryPumpGlucagon);

        // Update battery to UI
        setBatteryPumpInsulin(batteryPumpInsulin);
        setBatteryPumpGlucagon(batteryPumpGlucagon);

        /**
         *  Check with System Monitor
         */

        (new Thread(new SystemMonitor(context, state))).start();

        /*
        *   Save to Datastore
        */

        IAPStateDataSource dataSource = new APStateDataSource(context);
        dataSource.save(state);
    }

    private static ViewWrapper wrapper;
    private boolean newState = false;

    public void updateState(ViewWrapper newViewWrapper) {
        setWrapper(newViewWrapper);
        newState = true;
    }


    private synchronized ViewWrapper getWrapper() {
        return wrapper;
    }

    private synchronized void setWrapper(ViewWrapper wrapper) {
        this.wrapper = wrapper;
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

    public void stopLoop() {
        isRunning = false;
    }


}
