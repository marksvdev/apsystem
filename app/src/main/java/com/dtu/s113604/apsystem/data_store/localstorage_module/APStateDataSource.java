package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;
import com.dtu.s113604.apsystem.ap_system.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.ap_system.models.DeviceDataModel;
import com.dtu.s113604.apsystem.ap_system.models.DoseDataModel;
import com.dtu.s113604.apsystem.ap_system.models.UserDataModel;

import utils.DateTime;

/**
 * Created by marksv on 07/12/14.
 */
public class APStateDataSource implements IAPStateDataSource {

    public static final String TAG = "APStateDataSource";

    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase database;

    public APStateDataSource(Context context) {
        dbhelper = new APDBOpenHelper(context);
    }

    // Opens a connection to the database
    private void openWriteable() {
        Log.i(TAG, "Writeable database opened");
        database = dbhelper.getWritableDatabase();
    }

    private void openReadable() {
        Log.i(TAG, "Readable database opened");
        database = dbhelper.getReadableDatabase();
    }

    // Closes the connection to the database
    public void close() {
        Log.i(TAG, "Database closed");
        dbhelper.close();
    }

    // Creates a new AP State entry in the database
    @Override
    public APStateModel save(APStateModel apState) {
        openWriteable();

        String now = DateTime.now();

        // Saving AlgorithmStateModel
        ContentValues algorithmStateValues = new ContentValues();
        algorithmStateValues.put(APDBOpenHelper.COLUMN_ALGO_DUMMY, apState.getAlgorithmState().getDummy());

        long algo_insertid = database.insert(APDBOpenHelper.TABLE_ALGO_STATE, null, algorithmStateValues);
        apState.getAlgorithmState().setId(algo_insertid);

        // Saving DeviceDataModel
        ContentValues deviceStateValues = new ContentValues();
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_BLE_ADDRESS, apState.getDeviceData().getCGMBLEAddress());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_SERIAL_NUMBER, apState.getDeviceData().getCGMSerialNumber());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_INSULIN_PUMP_SERIAL_NUMBER, apState.getDeviceData().getInsulinPumpSerialNumber());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_GLUCAGON_PUMP_SERIAL_NUMBER, apState.getDeviceData().getGlucagonPumpSerialNumber());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_BATTERY, apState.getDeviceData().getCGMBattery());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_INSULIN_PUMP_BATTERY, apState.getDeviceData().getInsulinPumpBattery());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_GLUCAGON_PUMP_Battery, apState.getDeviceData().getGlucagonPumpBattery());

        long device_insertid = database.insert(APDBOpenHelper.TABLE_DEVICE_DATA, null, deviceStateValues);
        apState.getDeviceData().setId(device_insertid);

        // Saving DoseDataModel
        ContentValues doseStateValues = new ContentValues();
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_CURRENT_INSULIN_DOSE, apState.getDoseData().getCurrentInsulinDose());
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_LAST_INSULIN_DOSE, apState.getDoseData().getLastInsulinDose());
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_CURRENT_GLUCAGON_DOSE, apState.getDoseData().getCurrentGlugaconDose());
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_LAST_GLUCAGON_DOSE, apState.getDoseData().getLastGlucagonDose());

        long dose_insertid = database.insert(APDBOpenHelper.TABLE_DOSE_DATA, null, doseStateValues);
        apState.getDoseData().setId(dose_insertid);

        // Saving UserDataModel
        ContentValues userStateValues = new ContentValues();
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_CARBRATIO, apState.getPatientParameters().getCarbRatio());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_INSULIN_SENS, apState.getPatientParameters().getInsulinSensitivity());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_INSULIN_REACTIONTIME, apState.getPatientParameters().getInsulinReactionTime());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_GLUCAGON_SENS, apState.getPatientParameters().getGlucagonSensitivity());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_GLUCAGON_REACTIONTIME, apState.getPatientParameters().getGlucagonReactionTime());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MAX, apState.getPatientParameters().getGlucoseThresholdMax());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MIN, apState.getPatientParameters().getGlucoseThresholdMin());

        long user_insertid = database.insert(APDBOpenHelper.TABLE_USER_DATA, null, userStateValues);
        apState.getPatientParameters().setId(user_insertid);

        // Saving APStateModel
        ContentValues apStateValues = new ContentValues();
        apStateValues.put(APDBOpenHelper.COLUMN_AP_DATETIME, now);
        apStateValues.put(APDBOpenHelper.COLUMN_AP_CURRENT_GLUCOSE, apState.getCurrentGlucose());
        apStateValues.put(APDBOpenHelper.COLUMN_AP_LAST_GLUCOSE, apState.getLastGlucose());

        apStateValues.put(APDBOpenHelper.COLUMN_ALGORITHM_State_FK_ID, apState.getAlgorithmState().getId());
        apStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_FK_ID, apState.getDeviceData().getId());
        apStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_FK_ID, apState.getDoseData().getId());
        apStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_FK_ID, apState.getPatientParameters().getId());

        long ap_insertid = database.insert(APDBOpenHelper.TABLE_AP_STATE, null, apStateValues);
        apState.setId(ap_insertid);

        close();

        return apState;
    }

    // Loads the latest stored AP State from the database
    @Override
    public APStateModel load() {
        openReadable();

        // Loading APStateModel

        if (isDBEmpty(APDBOpenHelper.TABLE_AP_STATE)) {
            return null;
        }

        APStateModel apStateModel = new APStateModel();
        int algoID = -1, deviceID = -1, doseID = -1, userID = -1;

        String selectQueryAP = "SELECT * FROM " + APDBOpenHelper.TABLE_AP_STATE +
                " ORDER BY " + APDBOpenHelper.COLUMN_AP_DATETIME + " DESC";

        Cursor cursor = database.rawQuery(selectQueryAP, null);
        if (cursor.moveToFirst()) {
            apStateModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_ID)));
            apStateModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_DATETIME)));
            apStateModel.setCurrentGlucose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_CURRENT_GLUCOSE)));
            apStateModel.setCurrentGlucoseDateTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_CURRENT_GLUCOSE_DATETIME)));
            apStateModel.setLastGlucose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_LAST_GLUCOSE)));
            apStateModel.setLastGlucoseDateTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_LAST_GLUCOSE_DATETIME)));

            algoID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGORITHM_State_FK_ID));
            deviceID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_FK_ID));
            doseID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_FK_ID));
            userID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_FK_ID));
        }

        // TODO Check if ids are valid

        // Loading AlgorithmStateModel

        AlgorithmStateModel algorithmStateModel = new AlgorithmStateModel();

        String selectQueryAlgo = "SELECT * FROM " + APDBOpenHelper.TABLE_ALGO_STATE +
                " WHERE " + APDBOpenHelper.COLUMN_ALGO_ID + " = " + algoID;

        cursor = database.rawQuery(selectQueryAlgo, null);
        if (cursor.moveToFirst()) {
            algorithmStateModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGO_ID)));
            algorithmStateModel.setDummy(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGO_DUMMY)));
        }

        // Loading DeviceDataModel

        DeviceDataModel deviceDataModel = new DeviceDataModel();

        String selectQueryDevice = "SELECT * FROM " + APDBOpenHelper.TABLE_DEVICE_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_DEVICE_DATA_ID + " = " + deviceID;

        cursor = database.rawQuery(selectQueryDevice, null);
        if (cursor.moveToFirst()) {
            deviceDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_ID)));
            deviceDataModel.setCGMBLEAddress(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_BLE_ADDRESS)));
            deviceDataModel.setCGMSerialNumber(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_SERIAL_NUMBER)));
            deviceDataModel.setInsulinPumpSerialNumber(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_INSULIN_PUMP_SERIAL_NUMBER)));
            deviceDataModel.setGlucagonPumpSerialNumber(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_GLUCAGON_PUMP_SERIAL_NUMBER)));
            deviceDataModel.setCGMBattery(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_CGM_BATTERY)));
            deviceDataModel.setInsulinPumpBattery(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_INSULIN_PUMP_BATTERY)));
            deviceDataModel.setGlucagonPumpBattery(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_GLUCAGON_PUMP_Battery)));
        }

        // Loading DoseDataModel

        DoseDataModel doseDataModel = new DoseDataModel();

        String selectQueryDose = "SELECT * FROM " + APDBOpenHelper.TABLE_DOSE_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_DOSE_DATA_ID + " = " + doseID;

        cursor = database.rawQuery(selectQueryDose, null);
        if (cursor.moveToFirst()) {
            doseDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_ID)));
            doseDataModel.setCurrentInsulinDose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_CURRENT_INSULIN_DOSE)));
            doseDataModel.setLastInsulinDose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_LAST_INSULIN_DOSE)));
            doseDataModel.setCurrentGlugaconDose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_CURRENT_GLUCAGON_DOSE)));
            doseDataModel.setLastGlucagonDose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_LAST_GLUCAGON_DOSE)));
        }

        // Loading UserDataModel

        UserDataModel userDataModel = new UserDataModel();

        String selectQueryUser = "SELECT * FROM " + APDBOpenHelper.TABLE_USER_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_USER_DATA_ID + " = " + userID;

        cursor = database.rawQuery(selectQueryUser, null);
        if (cursor.moveToFirst()) {
            userDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_ID)));
            userDataModel.setCarbRatio(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_CARBRATIO)));
            userDataModel.setInsulinSensitivity(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_INSULIN_SENS)));
            userDataModel.setInsulinReactionTime(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_INSULIN_REACTIONTIME)));
            userDataModel.setGlucagonSensitivity(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_GLUCAGON_SENS)));
            userDataModel.setGlucagonReactionTime(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_GLUCAGON_REACTIONTIME)));
            userDataModel.setGlucoseThresholdMax(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MAX)));
            userDataModel.setGlucoseThresholdMin(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MIN)));

        }

        // Loading Rest of APStateModel

        apStateModel.setAlgorithmState(algorithmStateModel);
        apStateModel.setDeviceData(deviceDataModel);
        apStateModel.setDoseData(doseDataModel);
        apStateModel.setPatientParameters(userDataModel);

        close();

        return apStateModel;
    }

    private boolean isDBEmpty(String table) {
        String count = "SELECT count(*) FROM " + table;
        Cursor mcursor = database.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount<=0) {
            //database is empty
            Log.i(TAG, "Database is empty");
            return true;
        } else {
            return false;
        }
    }
}
