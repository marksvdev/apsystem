package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marksv on 07/12/14.
 */
public class APDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "APDBOpenHelper";
    private static final String DATABASE_NAME = "ap.db";
    private static final int DATABASE_VERSION = 1;

    /******
     *  UserDataModel : user_data
     */

    public static final String TABLE_USER_DATA =                "user_data";
    public static final String COLUMN_USER_DATA_ID =            "id";
    public static final String COLUMN_USER_DATA_CARBRATIO =     "carb_ratio";
    public static final String COLUMN_USER_DATA_INSULIN_SENS =     "insulin_sens";
    public static final String COLUMN_USER_DATA_INSULIN_REACTIONTIME =     "insulin_reactiontime";
    public static final String COLUMN_USER_DATA_GLUCAGON_SENS =     "glucagon_sens";
    public static final String COLUMN_USER_DATA_GLUCAGON_REACTIONTIME =     "glucagon_reactiontime";
    public static final String COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MAX =     "glucose_threshold_max";
    public static final String COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MIN =     "glucose_threshold_min";


    private static final String TABLE_CREATE_USER_DATA =
            "CREATE TABLE " + TABLE_USER_DATA + "( " +
                    COLUMN_USER_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_DATA_CARBRATIO + " INTEGER, " +
                    COLUMN_USER_DATA_INSULIN_SENS + " INTEGER, " +
                    COLUMN_USER_DATA_INSULIN_REACTIONTIME + " INTEGER, " +
                    COLUMN_USER_DATA_GLUCAGON_SENS + " INTEGER, " +
                    COLUMN_USER_DATA_GLUCAGON_REACTIONTIME + " INTEGER, " +
                    COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MAX + " INTEGER, " +
                    COLUMN_USER_DATA_GLUCOSE_THRESHOLD_MIN + " INTEGER " +
                    ");";

    /******
     *  UserDataModel : dose_data
     */

    public static final String TABLE_DOSE_DATA =                "dose_data";
    public static final String COLUMN_DOSE_DATA_ID =            "id";
    public static final String COLUMN_DOSE_DATA_CURRENT_INSULIN_DOSE =            "current_insulin_dose";
    public static final String COLUMN_DOSE_DATA_LAST_INSULIN_DOSE =            "last_insulin_dose";
    public static final String COLUMN_DOSE_DATA_CURRENT_GLUCAGON_DOSE =            "current_glucagon_dose";
    public static final String COLUMN_DOSE_DATA_LAST_GLUCAGON_DOSE =            "last_glucagon_dose";

    private static final String TABLE_CREATE_DOSE_DATA =
            "CREATE TABLE " + TABLE_DOSE_DATA + "( " +
                    COLUMN_DOSE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOSE_DATA_CURRENT_INSULIN_DOSE + " INTEGER, " +
                    COLUMN_DOSE_DATA_LAST_INSULIN_DOSE + " INTEGER, " +
                    COLUMN_DOSE_DATA_CURRENT_GLUCAGON_DOSE + " INTEGER, " +
                    COLUMN_DOSE_DATA_LAST_GLUCAGON_DOSE + " INTEGER " +
                    ");";

    /******
     *  DeviceDataModel : device_data
     */

    public static final String TABLE_DEVICE_DATA =              "device_data";
    public static final String COLUMN_DEVICE_DATA_ID =          "id";
    public static final String COLUMN_DEVICE_DATA_CGM_BLE_ADDRESS =          "cgm_ble_address";
    public static final String COLUMN_DEVICE_DATA_CGM_SERIAL_NUMBER =          "cgm_serial_number";
    public static final String COLUMN_DEVICE_DATA_INSULIN_PUMP_SERIAL_NUMBER =          "insulin_pump_serial_number";
    public static final String COLUMN_DEVICE_DATA_GLUCAGON_PUMP_SERIAL_NUMBER =          "glucagon_pump_serial_number";
    public static final String COLUMN_DEVICE_DATA_CGM_BATTERY =          "cgm_battery";
    public static final String COLUMN_DEVICE_DATA_INSULIN_PUMP_BATTERY =          "insulin_pump_battery";
    public static final String COLUMN_DEVICE_DATA_GLUCAGON_PUMP_Battery =          "glucagon_pump_battery";


    private static final String TABLE_CREATE_DEVICE_DATA =
            "CREATE TABLE " + TABLE_DEVICE_DATA + "( " +
                    COLUMN_DEVICE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DEVICE_DATA_CGM_BLE_ADDRESS + " TEXT, " +
                    COLUMN_DEVICE_DATA_CGM_SERIAL_NUMBER + " TEXT, " +
                    COLUMN_DEVICE_DATA_INSULIN_PUMP_SERIAL_NUMBER + " TEXT, " +
                    COLUMN_DEVICE_DATA_GLUCAGON_PUMP_SERIAL_NUMBER + " TEXT, " +
                    COLUMN_DEVICE_DATA_CGM_BATTERY + " INTEGER, " +
                    COLUMN_DEVICE_DATA_INSULIN_PUMP_BATTERY + " INTEGER, " +
                    COLUMN_DEVICE_DATA_GLUCAGON_PUMP_Battery + " INTEGER " +
                    ");";

    /******
     *  AlgorithmStateModel : algorithm_state
     */

    public static final String TABLE_ALGO_STATE =                 "algorithm_state";
    public static final String COLUMN_ALGO_ID =                   "id";
    public static final String COLUMN_ALGO_DUMMY =                  "dummy";

    private static final String TABLE_CREATE_ALGO_STATE =
            "CREATE TABLE " + TABLE_ALGO_STATE + "( " +
                    COLUMN_ALGO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ALGO_DUMMY + " TEXT " +
                    ");";

    /******
     *  APStateModel : ap_state
     */

    public static final String TABLE_AP_STATE =                 "ap_state";
    public static final String COLUMN_AP_ID =                   "id";
    public static final String COLUMN_AP_DATETIME =             "datetime";
    public static final String COLUMN_AP_CURRENT_GLUCOSE =      "current_glucose";
    public static final String COLUMN_AP_CURRENT_GLUCOSE_DATETIME =      "current_glucose_datetime";
    public static final String COLUMN_AP_LAST_GLUCOSE =         "last_glucose";
    public static final String COLUMN_AP_LAST_GLUCOSE_DATETIME =      "last_glucose_datetime";
    public static final String COLUMN_DEVICE_DATA_FK_ID =       "device_data_id";
    public static final String COLUMN_USER_DATA_FK_ID =         "user_data_id";
    public static final String COLUMN_ALGORITHM_State_FK_ID =   "algorithm_state_id";
    public static final String COLUMN_DOSE_DATA_FK_ID =         "algorithm_state_id";


    private static final String TABLE_CREATE_AP_STATE =
            "CREATE TABLE " + TABLE_AP_STATE + "( " +
                    COLUMN_AP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AP_DATETIME + " TEXT, " +
                    COLUMN_AP_CURRENT_GLUCOSE + " INTEGER, " +
                    COLUMN_AP_CURRENT_GLUCOSE_DATETIME + " TEXT, " +
                    COLUMN_AP_LAST_GLUCOSE + " INTEGER, " +
                    COLUMN_AP_LAST_GLUCOSE_DATETIME + " TEXT, " +
                    COLUMN_DEVICE_DATA_FK_ID + " INTEGER, " +
                    COLUMN_USER_DATA_FK_ID + " INTEGER, " +
                    COLUMN_ALGORITHM_State_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_DEVICE_DATA_FK_ID + ") REFERENCES " + TABLE_DEVICE_DATA + "(" + COLUMN_DEVICE_DATA_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_USER_DATA_FK_ID + ") REFERENCES " + TABLE_USER_DATA + "(" + COLUMN_USER_DATA_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_DOSE_DATA_FK_ID + ") REFERENCES " + TABLE_DOSE_DATA + "(" + COLUMN_DOSE_DATA_ID + ") " +
                    ");";

    public APDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USER_DATA);
        db.execSQL(TABLE_CREATE_DOSE_DATA);
        db.execSQL(TABLE_CREATE_DEVICE_DATA);
        db.execSQL(TABLE_CREATE_ALGO_STATE);
        db.execSQL(TABLE_CREATE_AP_STATE);
        Log.i(TAG, "Tables has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }
}
