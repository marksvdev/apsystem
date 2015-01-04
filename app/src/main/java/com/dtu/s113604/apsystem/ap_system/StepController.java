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
import com.dtu.s113604.apsystem.models.APStateModel;

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

        Document APXML = StateManager.getInstance().getState().getXML();
        Document deviceXML = getState().getDeviceData().getXML();
        Document doseXML = getState().getDoseData().getXML();

        /*
        *   CGM
        */

        String CGMBLEAddress = XMLManager.getValue(deviceXML, StateProps.CGMBLEAddress.toString());
        String CGMSN = XMLManager.getValue(deviceXML, StateProps.CGMSerialNumber.toString());
        Log.i(TAG, "CGMBLEAddress = " + CGMBLEAddress);
        Log.i(TAG, "CGMSN = " + CGMSN);

        int newGlucoseValue = cgm.getGlucoseValue(CGMBLEAddress, CGMSN);
        setLatestEGV(newGlucoseValue);
        Log.i(TAG, "newGlucoseValue = " + newGlucoseValue);

        XMLManager.insert(APXML, StateProps.Glucose.toString(), String.valueOf(newGlucoseValue));
        XMLManager.insert(APXML, StateProps.GlucoseTime.toString(), DateTime.now());
        getState().setXML(APXML);

        /*
        *   Control Algorithm
        */

        int insulinDose = control.calculateInsulinDose(getState());
        int glucagonDose = control.calculateGlucagonDose(getState());

        Log.i(TAG, "insulinDose = " + insulinDose);
        Log.i(TAG, "glucagonDose = " + glucagonDose);

        XMLManager.insert(doseXML, StateProps.Insulin.toString(), String.valueOf(insulinDose));
        XMLManager.insert(doseXML, StateProps.Glucagon.toString(), String.valueOf(glucagonDose));
        getState().getDoseData().setXML(doseXML);

        /*
        *   Pump(s)
        */

        String insulinPumpSN = XMLManager.getValue(deviceXML, StateProps.InsulinPumpSerialNumber.toString());
        String glucagonPumpSN = XMLManager.getValue(deviceXML, StateProps.GlucagonPumpSerialNumber.toString());

        Log.i(TAG, "insulinPumpSN = " + insulinPumpSN);
        Log.i(TAG, "glucagonPumpSN = " + glucagonPumpSN);

        String insulinPumpResponse = pump.doseInsulin(insulinPumpSN, insulinDose);
        String glucagonPumpResponse = pump.doseGlucagon(glucagonPumpSN, glucagonDose);

        Log.i(TAG, "insulinPumpResponse = " + insulinPumpResponse);
        Log.i(TAG, "glucagonPumpResponse = " + glucagonPumpResponse);

        // SAVE

        IAPStateDataSource dataSource = new APStateDataSource(context);
        dataSource.save(getState());
    }

    private void setLatestEGV(int value) {
        Broadcast.broadcastUpdate(context, MSGCode.UPDATE_EGV.toString(), String.valueOf(value));
    }

    private APStateModel getState() {
        return StateManager.getInstance().getState();
    }

}
