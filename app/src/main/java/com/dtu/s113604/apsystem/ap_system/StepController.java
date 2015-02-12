package com.dtu.s113604.apsystem.ap_system;

import android.content.Context;
import android.util.Log;

import com.dtu.s113604.apsystem.activities.MainActivity;
import com.dtu.s113604.apsystem.activities.ViewWrapper;
import com.dtu.s113604.apsystem.ap_system.cgm_module.CGM;
import com.dtu.s113604.apsystem.ap_system.cgm_module.ICGM;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.ControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.control_algorithm.IControlAlgorithm;
import com.dtu.s113604.apsystem.ap_system.models2.APState;
import com.dtu.s113604.apsystem.ap_system.models2.Battery;
import com.dtu.s113604.apsystem.ap_system.models2.Device;
import com.dtu.s113604.apsystem.ap_system.models2.DeviceType;
import com.dtu.s113604.apsystem.ap_system.models2.Dose;
import com.dtu.s113604.apsystem.ap_system.models2.Glucose;
import com.dtu.s113604.apsystem.ap_system.models2.User;
import com.dtu.s113604.apsystem.ap_system.pump_module.IPump;
import com.dtu.s113604.apsystem.ap_system.pump_module.Pump;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource2;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource2;
import com.dtu.s113604.apsystem.system_monitor.IISystemMonitor;
import com.dtu.s113604.apsystem.system_monitor.InitSystemMonitor;

import utils.Broadcast;
import utils.DateTime;
import utils.MSGCode;

/**
 * Created by marksv on 06/12/14.
 */
public class StepController extends Thread {

    public static String TAG = "StepController";
    private static boolean isRunning = false;

    private Context context;

    //private APStateModel state;

    private APState state2;

    private Device cgm;
    private Device insulin;
    private Device glucagon;
    private User user;

    private static ViewWrapper wrapper;
    private boolean newState = false;

    public StepController(Context context) {
        this.context = context;

//        state = createState();
        state2 = createState2();


//        ((MainActivity)context).updateView(state.makeWrapper());
        ((MainActivity) context).updateView(state2.makeWrapper());
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {

            if (newState) {
//                state.makeUnWrap(getWrapper());
                state2.makeUnWrap(getWrapper());
                newState = false;
            }

            executeSteps();
        }
    }

    private void executeSteps() {
        ICGM cgm = new CGM();
        IControlAlgorithm control = new ControlAlgorithm();
        IPump pump = new Pump();
        IISystemMonitor systemMonitor = new InitSystemMonitor();

        /*
        *   CGM
        */

        String CGMBLEAddress = state2.getDevice(DeviceType.CGM).getBleAddress();
        String CGMSN = state2.getDevice(DeviceType.CGM).getSerialNumber();
        Log.i(TAG, "CGMBLEAddress = " + CGMBLEAddress);
        Log.i(TAG, "CGMSN = " + CGMSN);

        Glucose newGlucoseValue = cgm.getGlucoseValue(CGMBLEAddress, CGMSN);
        Log.i(TAG, "newGlucoseValue = " + newGlucoseValue);

        Battery batteryCGM = cgm.getBattery(CGMBLEAddress, CGMSN);

        state2.getUser().addGlucose(newGlucoseValue);
        state2.getDevice(DeviceType.CGM).addBattery(batteryCGM);

        // Update View
        setLatestEGV(newGlucoseValue);
        setCGMBattery(batteryCGM);

        /*
        *   Control Algorithm
        */

        int insulin = control.calculateInsulinDose(state2);
        int glucagon = control.calculateGlucagonDose(state2);

        Log.i(TAG, "insulinDose = " + insulin);
        Log.i(TAG, "glucagonDose = " + glucagon);

        // HOLISTIC CHECKS GO HERE

//        state.getDoseData().setCurrentInsulinDose(insulinDose);
//        state.getDoseData().setCurrentGlugaconDose(glucagonDose);

        /*
        *   Pump(s)
        */

        String insulinPumpSN = state2.getDevice(DeviceType.INSULIN_PUMP).getSerialNumber();
        String glucagonPumpSN = state2.getDevice(DeviceType.GLUCAGON_PUMP).getSerialNumber();

        Log.i(TAG, "insulinPumpSN = " + insulinPumpSN);
        Log.i(TAG, "glucagonPumpSN = " + glucagonPumpSN);

        Dose insulinDose = pump.doseInsulin(insulinPumpSN, insulin);
        Dose glucagonDose = pump.doseGlucagon(glucagonPumpSN, glucagon);
        state2.getDevice(DeviceType.INSULIN_PUMP).addDose(insulinDose);
        state2.getDevice(DeviceType.GLUCAGON_PUMP).addDose(glucagonDose);

        Log.i(TAG, "insulinPumpResponse = " + insulinDose.getLevel());
        Log.i(TAG, "glucagonPumpResponse = " + glucagonDose.getLevel());

        Battery batteryPumpInsulin = pump.getBatteryInsulinPump(insulinPumpSN);
        Battery batteryPumpGlucagon = pump.getBatteryGlucagonPump(glucagonPumpSN);

        // Save battery to state
        state2.getDevice(DeviceType.INSULIN_PUMP).addBattery(batteryPumpInsulin);
        state2.getDevice(DeviceType.GLUCAGON_PUMP).addBattery(batteryPumpGlucagon);

        // Update battery to UI
        setBatteryPumpInsulin(batteryPumpInsulin);
        setBatteryPumpGlucagon(batteryPumpGlucagon);

        /**
         *  Check with System Monitor
         */

        systemMonitor.startSystemMonitor(context, state2);

        /*
        *   Save to Datastore
        */

        IAPStateDataSource2 dataSource = new APStateDataSource2(context);
        dataSource.save(state2, DateTime.now());
    }

//    private APStateModel createState() {
//        IAPStateDataSource dataSource = new APStateDataSource(context);
//        APStateModel loadedState = dataSource.load();
//
//        if (loadedState != null) {
//            return loadedState;
//        } else {
//            throw new RuntimeException("Resource not found");
//        }
//    }

    private APState createState2() {
        IAPStateDataSource2 dataSource = new APStateDataSource2(context);
        APState loadedState = dataSource.load(10);

        if (loadedState != null) {
            return loadedState;
        } else {
            // TODO Create new state
            throw new RuntimeException("Resource not found");
        }
    }

    private static final Object lock = new Object();

    public void updateState(ViewWrapper newViewWrapper) {
        setWrapper(newViewWrapper);
        newState = true;
    }

    private ViewWrapper getWrapper() {
        synchronized (lock) {
            return wrapper;
        }
    }

    private void setWrapper(ViewWrapper wrapper) {
        synchronized (lock) {
            this.wrapper = wrapper;
        }
    }

    private void setLatestEGV(Glucose value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_EGV.toString(), String.valueOf(value.getLevel()));
    }

    private void setCGMBattery(Battery value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_CGM.toString(), String.valueOf(value.getLevel()));
    }

    private void setBatteryPumpInsulin(Battery value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_PUMP_INSULIN.toString(), String.valueOf(value.getLevel()));
    }

    private void setBatteryPumpGlucagon(Battery value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_BATTERY_PUMP_GLUCAGON.toString(), String.valueOf(value.getLevel()));
    }

    public void stopLoop() {
        isRunning = false;
    }
}
