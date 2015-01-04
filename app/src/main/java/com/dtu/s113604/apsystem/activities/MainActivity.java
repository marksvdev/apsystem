package com.dtu.s113604.apsystem.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dtu.s113604.apsystem.R;
import com.dtu.s113604.apsystem.ap_system.StepController;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APDBOpenHelper;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource;
import com.dtu.s113604.apsystem.models.APStateModel;
import com.dtu.s113604.apsystem.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.models.DeviceDataModel;
import com.dtu.s113604.apsystem.models.DoseDataModel;
import com.dtu.s113604.apsystem.models.UserDataModel;

import org.w3c.dom.Document;

import utils.DateTime;
import utils.MSGCode;
import utils.StateManager;
import utils.StateProps;
import utils.XMLManager;


public class MainActivity extends Activity {

    private Button btnSave;
    private TextView textViewEGV, textViewBatteryCGM,
            textViewBatteryPumpInsulin, textViewBatteryPumpGlucagon;
    private EditText editTextCGM_SN, editTextCGM_BLE,
            editTextPumpInsulinSN, editTextPumpGlucagonSN,
            editTextUserInsulinSens, editTextUserGlucagonSens;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = (Button) findViewById(R.id.btnSave);

        textViewEGV = (TextView) findViewById(R.id.LatestEGVValue);
        textViewBatteryCGM = (TextView) findViewById(R.id.CGM_Battery);
        textViewBatteryPumpInsulin = (TextView) findViewById(R.id.PUMP_Battery_Insulin);
        textViewBatteryPumpGlucagon = (TextView) findViewById(R.id.PUMP_Battery_Glucagon);

        editTextCGM_SN = (EditText) findViewById(R.id.CGM_SN);
        editTextCGM_BLE = (EditText) findViewById(R.id.CGM_BLE);
        editTextPumpInsulinSN = (EditText) findViewById(R.id.PUMP_Insulin_SN);
        editTextPumpGlucagonSN = (EditText) findViewById(R.id.PUMP_GLUCAGON_SN);
        editTextUserInsulinSens = (EditText) findViewById(R.id.USER_INSULIN_SENS);
        editTextUserGlucagonSens = (EditText) findViewById(R.id.USER_GLUCAGON_SENS);

        editTextCGM_SN.setText(editTextCGM_SN.getHint());
        editTextCGM_BLE.setText(editTextCGM_BLE.getHint());
        editTextPumpInsulinSN.setText(editTextPumpInsulinSN.getHint());
        editTextPumpGlucagonSN.setText(editTextPumpGlucagonSN.getHint());
        editTextUserInsulinSens.setText(editTextUserInsulinSens.getHint());
        editTextUserGlucagonSens.setText(editTextUserGlucagonSens.getHint());
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mUpdateViewReceiver, makeUpdateViewIntentFilter());
    }

    public void setTextViewEGV(String value) {
        textViewEGV.setText(value + " mg/dl");
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
                setTextViewEGV(EGV);
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
        }
    };

    private static IntentFilter makeUpdateViewIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MSGCode.UPDATE_EGV.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_CGM.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_PUMP_INSULIN.toString());
        intentFilter.addAction(MSGCode.UPDATE_BATTERY_PUMP_GLUCAGON.toString());
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

        editState();
    }

    // device
    private String CGMBLEAddress;
    private String CGMSN;
    private String InsulinPumpSN;
    private String GlucagonPumpSN;

    // user
    private String InsulinSensitivity;
    private String GlucagonSensitivity;

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

    private void editState() {
        APStateModel state = StateManager.getInstance().getState();

        if (state == null) { createNewState(); }

        state = StateManager.getInstance().getState();

        state.getPatientParameters().setXML(insertUserSettings(state.getPatientParameters().getXML()));
        state.getDeviceData().setXML(insertDeviceSettings(state.getDeviceData().getXML()));
    }

    private void createNewState() {
        IAPStateDataSource dataSource = new APStateDataSource(this);
        APStateModel loadedState = dataSource.load();

        if (loadedState != null) {
            StateManager.getInstance().setState(loadedState);
        } else {

            Document deviceDoc = XMLManager.generateNewDocument();
            deviceDoc = insertDeviceSettings(deviceDoc);

            Document userDoc = XMLManager.generateNewDocument();
            userDoc = insertUserSettings(userDoc);

            String data = XMLManager.DocumentToString(userDoc);

            APStateModel state = new APStateModel();
            state.setAlgorithmState(new AlgorithmStateModel());
            state.setPatientParameters(new UserDataModel(userDoc));
            state.setDoseData(new DoseDataModel());
            state.setDeviceData(new DeviceDataModel(deviceDoc));

            StateManager.getInstance().setState(state);
        }
    }

    private Document insertDeviceSettings(Document deviceDoc) {
        XMLManager.insert(deviceDoc, StateProps.CGMBLEAddress.toString(), CGMBLEAddress);
        XMLManager.insert(deviceDoc, StateProps.CGMSerialNumber.toString(), CGMSN);
        XMLManager.insert(deviceDoc, StateProps.InsulinPumpSerialNumber.toString(), InsulinPumpSN);
        XMLManager.insert(deviceDoc, StateProps.GlucagonPumpSerialNumber.toString(), GlucagonPumpSN);
        return deviceDoc;
    }

    private Document insertUserSettings(Document userDoc) {
        XMLManager.insert(userDoc, StateProps.InsulinSensitivity.toString(), InsulinSensitivity);
        XMLManager.insert(userDoc, StateProps.GlucagonSensitivity.toString(), GlucagonSensitivity);
        return userDoc;
    }
}