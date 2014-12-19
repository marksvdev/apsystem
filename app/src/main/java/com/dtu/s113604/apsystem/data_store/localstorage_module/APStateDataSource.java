package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dtu.s113604.apsystem.models.APStateModel;
import com.dtu.s113604.apsystem.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.models.DeviceDataModel;
import com.dtu.s113604.apsystem.models.DoseDataModel;
import com.dtu.s113604.apsystem.models.UserDataModel;

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

        // Saving AlgorithmStateModel
        ContentValues algorithmStateValues = new ContentValues();
        algorithmStateValues.put(APDBOpenHelper.COLUMN_ALGO_DATETIME, apState.getAlgorithmState().getDatetime());
        algorithmStateValues.put(APDBOpenHelper.COLUMN_ALGO_STATE_PROPERTIES, apState.getAlgorithmState().getStateProperties());

        long algo_insertid = database.insert(APDBOpenHelper.TABLE_ALGO_STATE, null, algorithmStateValues);
        apState.getAlgorithmState().setId(algo_insertid);

        // Saving DeviceDataModel
        ContentValues deviceStateValues = new ContentValues();
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_DATETIME, apState.getDeviceData().getDatetime());
        deviceStateValues.put(APDBOpenHelper.COLUMN_DEVICE_DATA_STATE_PROPERTIES, apState.getDeviceData().getStateProperties());

        long device_insertid = database.insert(APDBOpenHelper.TABLE_DEVICE_DATA, null, deviceStateValues);
        apState.getDeviceData().setId(device_insertid);

        // Saving DoseDataModel
        ContentValues doseStateValues = new ContentValues();
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_DATETIME, apState.getDoseData().getDatetime());
        doseStateValues.put(APDBOpenHelper.COLUMN_DOSE_DATA_STATE_PROPERTIES, apState.getDoseData().getStateProperties());

        long dose_insertid = database.insert(APDBOpenHelper.TABLE_DOSE_DATA, null, doseStateValues);
        apState.getDoseData().setId(dose_insertid);

        // Saving UserDataModel
        ContentValues userStateValues = new ContentValues();
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_DATETIME, apState.getPatientParameters().getDatetime());
        userStateValues.put(APDBOpenHelper.COLUMN_USER_DATA_STATE_PROPERTIES, apState.getPatientParameters().getStateProperties());

        long user_insertid = database.insert(APDBOpenHelper.TABLE_USER_DATA, null, userStateValues);
        apState.getPatientParameters().setId(user_insertid);

        // Saving APStateModel
        ContentValues apStateValues = new ContentValues();
        apStateValues.put(APDBOpenHelper.COLUMN_AP_DATETIME, apState.getDatetime());
        apStateValues.put(APDBOpenHelper.COLUMN_AP_STATE_PROPERTIES, apState.getStateProperties());
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

        APStateModel apStateModel = new APStateModel();
        int algoID = -1, deviceID = -1, doseID = -1, userID = -1;

        String selectQueryAP = "SELECT * FROM " + APDBOpenHelper.TABLE_AP_STATE +
                " ORDER BY " + APDBOpenHelper.COLUMN_AP_DATETIME + " DESC";

        Cursor cursor = database.rawQuery(selectQueryAP, null);
        if (cursor.moveToFirst()) {
            apStateModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_ID)));
            apStateModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_DATETIME)));
            apStateModel.setStateProperties(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_AP_STATE_PROPERTIES)));

            algoID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGORITHM_State_FK_ID));
            deviceID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_FK_ID));
            doseID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_FK_ID));
            userID = cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_FK_ID));
        }

        // TODO Check if ids are valid

        // Loading AlgorithmStateModel

        AlgorithmStateModel algorithmStateModel = new AlgorithmStateModel();

        String selectQueryAlgo = "SELECT * FROM " + APDBOpenHelper.TABLE_ALGO_STATE +
                " WHERE " + APDBOpenHelper.COLUMN_ALGO_ID + " = " + algoID +
                " ORDER BY " + APDBOpenHelper.COLUMN_ALGO_DATETIME + " DESC";

        cursor = database.rawQuery(selectQueryAlgo, null);
        if (cursor.moveToFirst()) {
            algorithmStateModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGO_ID)));
            algorithmStateModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGO_DATETIME)));
            algorithmStateModel.setStateProperties(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_ALGO_STATE_PROPERTIES)));
        }

        // Loading DeviceDataModel

        DeviceDataModel deviceDataModel = new DeviceDataModel();

        String selectQueryDevice = "SELECT * FROM " + APDBOpenHelper.TABLE_DEVICE_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_DEVICE_DATA_ID + " = " + deviceID +
                " ORDER BY " + APDBOpenHelper.COLUMN_DEVICE_DATA_DATETIME + " DESC";

        cursor = database.rawQuery(selectQueryDevice, null);
        if (cursor.moveToFirst()) {
            deviceDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_ID)));
            deviceDataModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_DATETIME)));
            deviceDataModel.setStateProperties(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DEVICE_DATA_STATE_PROPERTIES)));
        }

        // Loading DoseDataModel

        DoseDataModel doseDataModel = new DoseDataModel();

        String selectQueryDose = "SELECT * FROM " + APDBOpenHelper.TABLE_DOSE_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_DOSE_DATA_ID + " = " + doseID +
                " ORDER BY " + APDBOpenHelper.COLUMN_DOSE_DATA_DATETIME + " DESC";

        cursor = database.rawQuery(selectQueryDose, null);
        if (cursor.moveToFirst()) {
            doseDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_ID)));
            doseDataModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_DATETIME)));
            doseDataModel.setStateProperties(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_DOSE_DATA_STATE_PROPERTIES)));
        }

        // Loading UserDataModel

        UserDataModel userDataModel = new UserDataModel();

        String selectQueryUser = "SELECT * FROM " + APDBOpenHelper.TABLE_USER_DATA +
                " WHERE " + APDBOpenHelper.COLUMN_USER_DATA_ID + " = " + userID +
                " ORDER BY " + APDBOpenHelper.COLUMN_USER_DATA_DATETIME + " DESC";

        cursor = database.rawQuery(selectQueryUser, null);
        if (cursor.moveToFirst()) {
            userDataModel.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_ID)));
            userDataModel.setDatetime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_DATETIME)));
            userDataModel.setStateProperties(cursor.getString(cursor.getColumnIndex(APDBOpenHelper.COLUMN_USER_DATA_STATE_PROPERTIES)));
        }

        // Loading Rest of APStateModel

        apStateModel.setAlgorithmState(algorithmStateModel);
        apStateModel.setDeviceData(deviceDataModel);
        apStateModel.setDoseData(doseDataModel);
        apStateModel.setPatientParameters(userDataModel);

        close();

        return apStateModel;
    }
}
