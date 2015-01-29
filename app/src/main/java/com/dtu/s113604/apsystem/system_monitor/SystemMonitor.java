package com.dtu.s113604.apsystem.system_monitor;

import android.content.Context;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;

import utils.Broadcast;
import utils.MSGCode;

/**
 * Created by marksv on 15/01/15.
 */
public class SystemMonitor implements Runnable {

    private static String TAG = "SystemMonitor";
    private APStateModel state;

    private Context context;

    private static final int BATTERY_WARN3 = 20;
    private static final int BATTERY_WARN2 = 10;
    private static final int BATTERY_WARN1 = 5;
    private static final int BATTERY_WARN0 = 0;

    public SystemMonitor(Context context, APStateModel state) {
        this.context = context;
        this.state = state;
    }

    public SystemMonitor(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        int batteryCGM = state.getDeviceData().getCGMBattery();
        int batteryPumpGlucagon = state.getDeviceData().getGlucagonPumpBattery();
        int batteryPumpInsulin = state.getDeviceData().getInsulinPumpBattery();

        int glucose = state.getCurrentGlucose();
        int glucoseMax = state.getPatientParameters().getGlucoseThresholdMax();
        int glucoseMin = state.getPatientParameters().getGlucoseThresholdMin();

        // Check Battery levels

        // PUMP
        int pumpInsulinWarn = CheckBattery(batteryPumpInsulin);

        switch (pumpInsulinWarn) {
            case BATTERY_WARN0:
                // Battery ded
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_INSULIN.toString(), String.valueOf(BATTERY_WARN0));
                break;
            case BATTERY_WARN1:
                // 5%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_INSULIN.toString(), String.valueOf(BATTERY_WARN1));
                break;
            case BATTERY_WARN2:
                // 10%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_INSULIN.toString(), String.valueOf(BATTERY_WARN2));
                break;
            case BATTERY_WARN3:
                // 20%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_INSULIN.toString(), String.valueOf(BATTERY_WARN3));
                break;
            default:
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_INSULIN_OK.toString(), "OK");
                break;
        }

        int pumpGlucagonWarn = CheckBattery(batteryPumpGlucagon);

        switch (pumpGlucagonWarn) {
            case BATTERY_WARN0:
                // Battery ded
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_GLUCAGON.toString(), String.valueOf(BATTERY_WARN0));
                break;
            case BATTERY_WARN1:
                // 5%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_GLUCAGON.toString(), String.valueOf(BATTERY_WARN1));
                break;
            case BATTERY_WARN2:
                // 10%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_GLUCAGON.toString(), String.valueOf(BATTERY_WARN2));
                break;
            case BATTERY_WARN3:
                // 20%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_GLUCAGON.toString(), String.valueOf(BATTERY_WARN3));
                break;
            default:
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_GLUCAGON_OK.toString(), "OK");
                break;
        }

        // CGM
        int CGMWarn = CheckBattery(batteryCGM);

        switch (CGMWarn) {
            case BATTERY_WARN0:
                // Battery ded
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_CGM.toString(), String.valueOf(BATTERY_WARN0));
                break;
            case BATTERY_WARN1:
                // 5%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_CGM.toString(), String.valueOf(BATTERY_WARN1));
                break;
            case BATTERY_WARN2:
                // 10%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_CGM.toString(), String.valueOf(BATTERY_WARN2));
                break;
            case BATTERY_WARN3:
                // 20%
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_CGM.toString(), String.valueOf(BATTERY_WARN3));
                break;
            default:
                Broadcast.broadcastUpdate(context, MSGCode.ALARM_BATTERY_CGM_OK.toString(), "OK");
                break;
        }


        // Check Thresholds

        if (glucose >= glucoseMax) {
            // glucose High
            Broadcast.broadcastUpdate(context, MSGCode.ALARM_GLUCOSE_HIGH.toString(), "HIGH");
        }
        // Insulin Low
        else if (glucose <= glucoseMin) {
            // glucose High
            Broadcast.broadcastUpdate(context, MSGCode.ALARM_GLUCOSE_LOW.toString(), "LOW");
        }
        else {
          Broadcast.broadcastUpdate(context, MSGCode.ALARM_GLUCOSE_NORMAL.toString(), "NORMAL");
        }

    }

    private int CheckBattery(int level) {
        // Battery 20%
        if (level <= BATTERY_WARN3 && level > BATTERY_WARN2) {
            return BATTERY_WARN3;
        }
        // Battery 10%
        if (level <= BATTERY_WARN2 && level > BATTERY_WARN1) {
            return BATTERY_WARN2;
        }
        // Battery 5%
        if (level <= BATTERY_WARN1 && level > BATTERY_WARN0) {
            return BATTERY_WARN1;
        }
        // Battery 0%
        if (level < BATTERY_WARN0) {
            return BATTERY_WARN0;
        }

        return 100;
    }
}
