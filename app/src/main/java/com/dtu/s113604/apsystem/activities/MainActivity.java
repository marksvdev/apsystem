package com.dtu.s113604.apsystem.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dtu.s113604.apsystem.R;
import com.dtu.s113604.apsystem.ap_system.StepController;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource;
import com.dtu.s113604.apsystem.ap_system.models.APStateModel;
import com.dtu.s113604.apsystem.ap_system.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.ap_system.models.DeviceDataModel;
import com.dtu.s113604.apsystem.ap_system.models.DoseDataModel;
import com.dtu.s113604.apsystem.ap_system.models.UserDataModel;

import utils.MSGCode;
import utils.StateManager;


public class MainActivity extends Activity {

    private static final String ALARMTAG = "ALARM";

    private Button btnSave;
    private TextView textViewEGVValue, textViewBatteryCGM,
            textViewBatteryPumpInsulin, textViewBatteryPumpGlucagon,
            textViewEGVLabel, textViewCGMBatteryLabel, textViewGlucagonBatteryLabel, textViewInsulinBatteryLabel;
    private EditText editTextCGM_SN, editTextCGM_BLE,
            editTextPumpInsulinSN, editTextPumpGlucagonSN,
            editTextUserInsulinSens, editTextUserGlucagonSens,
            editTextthresholdMin, editTextthresholdMax,
            editTextCarbRatio, editTextInsulinReactionTime,
            editTextGlucagonReactionTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave);

        textViewEGVValue = (TextView) findViewById(R.id.LatestEGVValue);
        textViewBatteryCGM = (TextView) findViewById(R.id.CGM_Battery);
        textViewBatteryPumpInsulin = (TextView) findViewById(R.id.PUMP_Battery_Insulin);
        textViewBatteryPumpGlucagon = (TextView) findViewById(R.id.PUMP_Battery_Glucagon);
        textViewEGVLabel = (TextView) findViewById(R.id.LatestEGVLabel);
        textViewCGMBatteryLabel = (TextView) findViewById(R.id.CGMBatteryLabel);
        textViewGlucagonBatteryLabel = (TextView) findViewById(R.id.GlucagonBatteryLabel);
        textViewInsulinBatteryLabel = (TextView) findViewById(R.id.InsulinBatteryLabel);


        editTextCGM_SN = (EditText) findViewById(R.id.CGM_SN);
        editTextCGM_BLE = (EditText) findViewById(R.id.CGM_BLE);
        editTextPumpInsulinSN = (EditText) findViewById(R.id.PUMP_Insulin_SN);
        editTextPumpGlucagonSN = (EditText) findViewById(R.id.PUMP_GLUCAGON_SN);
        editTextUserInsulinSens = (EditText) findViewById(R.id.USER_INSULIN_SENS);
        editTextUserGlucagonSens = (EditText) findViewById(R.id.USER_GLUCAGON_SENS);
        editTextthresholdMax = (EditText) findViewById(R.id.USER_THRESHOLD_MAX);
        editTextthresholdMin = (EditText) findViewById(R.id.USER_THRESHOLD_MIN);
        editTextCarbRatio = (EditText) findViewById(R.id.USER_CARB_RATIO);
        editTextInsulinReactionTime = (EditText) findViewById(R.id.USER_INSULIN_REACTION_TIME);
        editTextGlucagonReactionTime = (EditText) findViewById(R.id.USER_GLUCAGON_REACTION_TIME);


        createNewState();

        editTextCGM_SN.setText(StateManager.getInstance().getState().getDeviceData().getCGMSerialNumber());
        editTextCGM_BLE.setText(StateManager.getInstance().getState().getDeviceData().getCGMBLEAddress());
        editTextPumpInsulinSN.setText(StateManager.getInstance().getState().getDeviceData().getInsulinPumpSerialNumber());
        editTextPumpGlucagonSN.setText(StateManager.getInstance().getState().getDeviceData().getGlucagonPumpSerialNumber());
        editTextUserInsulinSens.setText(StateManager.getInstance().getState().getPatientParameters().getInsulinSensitivity() + "");
        editTextUserGlucagonSens.setText(StateManager.getInstance().getState().getPatientParameters().getGlucagonSensitivity() + "");
        editTextthresholdMax.setText(StateManager.getInstance().getState().getPatientParameters().getGlucoseThresholdMax() + "");
        editTextthresholdMin.setText(StateManager.getInstance().getState().getPatientParameters().getGlucoseThresholdMin() + "");
        editTextCarbRatio.setText(StateManager.getInstance().getState().getPatientParameters().getCarbRatio() + "");
        editTextInsulinReactionTime.setText(StateManager.getInstance().getState().getPatientParameters().getInsulinReactionTime() + "");
        editTextGlucagonReactionTime.setText(StateManager.getInstance().getState().getPatientParameters().getGlucagonReactionTime() + "");

//        editTextCGM_SN.setText(editTextCGM_SN.getHint());
//        editTextCGM_BLE.setText(editTextCGM_BLE.getHint());
//        editTextPumpInsulinSN.setText(editTextPumpInsulinSN.getHint());
//        editTextPumpGlucagonSN.setText(editTextPumpGlucagonSN.getHint());
//        editTextUserInsulinSens.setText(editTextUserInsulinSens.getHint());
//        editTextUserGlucagonSens.setText(editTextUserGlucagonSens.getHint());
//        editTextthresholdMax.setText(editTextthresholdMax.getHint());
//        editTextthresholdMin.setText(editTextthresholdMin.getHint());
//        editTextCarbRatio.setText(editTextCarbRatio.getHint());
//        editTextInsulinReactionTime.setText(editTextInsulinReactionTime.getHint());
//        editTextGlucagonReactionTime.setText(editTextGlucagonReactionTime.getHint());


    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mUpdateViewReceiver, makeUpdateViewIntentFilter());
    }

    public void setTextViewEGVValue(String value) {
        textViewEGVValue.setText(value + " mg/dl");
    }

    public void setTextViewBatteryCGM(String value) {
        textViewBatteryCGM.setText(value  + "%");
    }

    public void setTextViewBatteryPumpInsulin(String value) {
        textViewBatteryPumpInsulin.setText(value + "%");
    }

    public void setGetTextViewBatteryPumpGlucagon(String value) {
        textViewBatteryPumpGlucagon.setText(value + "%");
    }

    private final BroadcastReceiver mUpdateViewReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(MSGCode.UPDATE_EGV.toString())) {
                String EGV = intent.getStringExtra(MSGCode.EXTRA_DATA.toString());
                setTextViewEGVValue(EGV);
            }
            else if(action.equals(MSGCode.UPDATE_BATTERY_CGM.toString())) {
                String batteryCGM = intent.getStringExtra(MSGCode.EXTRA_DATA.toString());
                setTextViewBatteryCGM(batteryCGM);
            }
            else if(action.equals(MSGCode.UPDATE_BATTERY_PUMP_INSULIN.toString())) {
                String batteryPumpInsulin = intent.getStringExtra(MSGCode.EXTRA_DATA.toString());
                setTextViewBatteryPumpInsulin(batteryPumpInsulin);
            }
            else if(action.equals(MSGCode.UPDATE_BATTERY_PUMP_GLUCAGON.toString())) {
                String batteryPumpGlucagon = intent.getStringExtra(MSGCode.EXTRA_DATA.toString());
                setGetTextViewBatteryPumpGlucagon(batteryPumpGlucagon);
            }
            else if(action.equals(MSGCode.ALARM_BATTERY_CGM.toString())) {
                // CGM BATTERY LOW
                Log.i(ALARMTAG, "CGM BATTERY LOW");
                alarmCGMBattery();

            }
            else if(action.equals(MSGCode.ALARM_BATTERY_GLUCAGON.toString())) {
                // GLUCAGON PUMP BATTERY LOW
                Log.i(ALARMTAG, "GLUCAGON PUMP BATTERY LOW");
                alarmGlucagonPumpBattery();
            }
            else if(action.equals(MSGCode.ALARM_BATTERY_INSULIN.toString())) {
                // INSULIN PUMP BATTERY LOW
                Log.i(ALARMTAG, "INSULIN PUMP BATTERY LOW");
                alarmInsulinPumpBattery();
            }
            else if(action.equals(MSGCode.ALARM_GLUCOSE_HIGH.toString())) {
                // GLUCOSE HIGH
                Log.i(ALARMTAG, "GLUCOSE LEVEL TOO HIGH");
                alarmGlucoseHigh();
            }
            else if(action.equals(MSGCode.ALARM_GLUCOSE_LOW.toString())) {
                // GLUCOSE LOW
                Log.i(ALARMTAG, "GLUCOSE LEVEL TOO LOW");
                alarmGlucoseLow();
            }
            else if(action.equals(MSGCode.ALARM_GLUCOSE_NORMAL.toString())) {
                // GLUCOSE LEVEL NORMAL
                alarmGlucoseNormal();
            }
            else if(action.equals(MSGCode.ALARM_BATTERY_GLUCAGON_OK.toString())) {
                // GLUCAGON PUMP BATTERY OK
                alarmGlucagonPumpBatteryOk();
            }
            else if(action.equals(MSGCode.ALARM_BATTERY_INSULIN_OK.toString())) {
                // INSULIN PUMP BATTERY OK
                alarmInsulinPumpBatteryOk();
            }
            else if(action.equals(MSGCode.ALARM_BATTERY_CGM_OK.toString())) {
                // CGM BATTERY OK
                alarmCGMBatteryOk();
            }
        }
    };

    private static IntentFilter makeUpdateViewIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(MSGCode.UPDATE_EGV.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_CGM.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_PUMP_INSULIN.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_PUMP_GLUCAGON.toString());

        intentFilter.addAction(MSGCode.ALARM_GLUCOSE_HIGH.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_CGM.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_INSULIN.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_GLUCAGON.toString());
        intentFilter.addAction(MSGCode.ALARM_GLUCOSE_LOW.toString());

        intentFilter.addAction(MSGCode.ALARM_GLUCOSE_NORMAL.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_INSULIN_OK.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_GLUCAGON_OK.toString());
        intentFilter.addAction(MSGCode.ALARM_BATTERY_CGM_OK.toString());

        return intentFilter;
    }

    // STATE INITIALIZATION

    public void onClickSaveSettings(View view) {
        CGMBLEAddress = editTextCGM_BLE.getText().toString();
        CGMSN = editTextCGM_SN.getText().toString();
        InsulinPumpSN = editTextPumpInsulinSN.getText().toString();
        GlucagonPumpSN = editTextPumpGlucagonSN.getText().toString();

        InsulinSensitivity = editTextUserInsulinSens.getText().toString();
        GlucagonSensitivity = editTextUserGlucagonSens.getText().toString();
        GlucoseThresholdMax = editTextthresholdMax.getText().toString();
        GlucoseThresholdMin = editTextthresholdMin.getText().toString();
        CarbRatio = editTextCarbRatio.getText().toString();
        InsulinReactionTime = editTextInsulinReactionTime.getText().toString();
        GlucagonReactionTime = editTextGlucagonReactionTime.getText().toString();

        APStateModel state = StateManager.getInstance().getState();

//        if (state == null) { createNewState(); return;}

//        state = StateManager.getInstance().getState();

        insertUserSettings(state.getPatientParameters());
        insertDeviceSettings(state.getDeviceData());
    }

    // device
    private String CGMBLEAddress;
    private String CGMSN;
    private String InsulinPumpSN;
    private String GlucagonPumpSN;

    // user
    private String InsulinSensitivity;
    private String GlucagonSensitivity;
    private String GlucoseThresholdMax;
    private String GlucoseThresholdMin;
    private String CarbRatio;
    private String InsulinReactionTime;
    private String GlucagonReactionTime;

    public void onClickAPOnOff(View view) {
        if (((ToggleButton) view).isChecked()) {
            // handle toggle on

            if (StepController.isRunning) {
                return;
            }

            if (StateManager.getInstance().getState() == null) {
                createNewState();
            }

            (new Thread(new StepController(this))).start();

        } else {
            // handle toggle off
            StepController.isRunning = false;
        }
    }

    private void createNewState() {
        IAPStateDataSource dataSource = new APStateDataSource(this);
        APStateModel loadedState = dataSource.load();

        if (loadedState != null) {
            StateManager.getInstance().setState(loadedState);
        } else {

            DeviceDataModel deviceData = new DeviceDataModel();
            insertDeviceSettings(deviceData);

            UserDataModel userData = new UserDataModel();
            insertUserSettings(userData);

            APStateModel state = new APStateModel();
            state.setAlgorithmState(new AlgorithmStateModel());
            state.setPatientParameters(userData);
            state.setDoseData(new DoseDataModel());
            state.setDeviceData(deviceData);

            StateManager.getInstance().setState(state);
        }
    }

    private void insertDeviceSettings(DeviceDataModel deviceData) {
        deviceData.setCGMBLEAddress(CGMBLEAddress);
        deviceData.setCGMSerialNumber(CGMSN);
        deviceData.setInsulinPumpSerialNumber(InsulinPumpSN);
        deviceData.setGlucagonPumpSerialNumber(GlucagonPumpSN);
    }

    private void insertUserSettings(UserDataModel userData) {
        userData.setInsulinSensitivity(Integer.valueOf(InsulinSensitivity));
        userData.setGlucagonSensitivity(Integer.valueOf(GlucagonSensitivity));

        userData.setCarbRatio(Integer.valueOf(CarbRatio));
        userData.setInsulinReactionTime(Integer.valueOf(InsulinReactionTime));
        userData.setGlucagonReactionTime(Integer.valueOf(GlucagonReactionTime));

        userData.setGlucoseThresholdMax(Integer.valueOf(GlucoseThresholdMax));
        userData.setGlucoseThresholdMin(Integer.valueOf(GlucoseThresholdMin));
    }

    private void alarmGlucoseLow() {
        // audio (sound)
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        // view (update view)

        textViewEGVLabel.setBackgroundResource(R.color.red);
        textViewEGVValue.setBackgroundResource(R.color.red);


        // feel (vibrate)

        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        //v.vibrate(300);
        v.vibrate(1000);
    }

    private void alarmGlucoseHigh() {
        // audio (sound)
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();

        // view (update view)

        textViewEGVLabel.setBackgroundResource(R.color.yellow);
        textViewEGVValue.setBackgroundResource(R.color.yellow);


        // feel (vibrate)

        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        //v.vibrate(300);
        v.vibrate(1000);
    }

    private void alarmGlucoseNormal() {
        textViewEGVLabel.setBackgroundResource(R.color.white);
        textViewEGVValue.setBackgroundResource(R.color.white);
    }

    private void alarmInsulinPumpBattery() {
        textViewInsulinBatteryLabel.setBackgroundResource(R.color.red);
        textViewBatteryPumpInsulin.setBackgroundResource(R.color.red);
    }

    private void alarmGlucagonPumpBattery() {
        textViewGlucagonBatteryLabel.setBackgroundResource(R.color.red);
        textViewBatteryPumpGlucagon.setBackgroundResource(R.color.red);
    }

    private void alarmCGMBattery() {
        textViewBatteryCGM.setBackgroundResource(R.color.red);
        textViewCGMBatteryLabel.setBackgroundResource(R.color.red);
    }

    private void alarmInsulinPumpBatteryOk() {
        textViewInsulinBatteryLabel.setBackgroundResource(R.color.white);
        textViewBatteryPumpInsulin.setBackgroundResource(R.color.white);
    }

    private void alarmGlucagonPumpBatteryOk() {
        textViewGlucagonBatteryLabel.setBackgroundResource(R.color.white);
        textViewBatteryPumpGlucagon.setBackgroundResource(R.color.white);
    }

    private void alarmCGMBatteryOk() {
        textViewBatteryCGM.setBackgroundResource(R.color.white);
        textViewCGMBatteryLabel.setBackgroundResource(R.color.white);
    }


}