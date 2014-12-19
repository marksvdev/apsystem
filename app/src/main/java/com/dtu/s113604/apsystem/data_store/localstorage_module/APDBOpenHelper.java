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
     *  UserDataModel : dose_data
     */

    public static final String TABLE_USER_DATA =                "user_data";
    public static final String COLUMN_USER_DATA_ID =            "id";
    public static final String COLUMN_USER_DATA_DATATIME =      "datetime";
    public static final String COLUMN_CARB_RATIO =              "carb_ratio";
    public static final String COLUMN_INSULIN_SENS_RATIO =      "insulin_sens_ratio";
    public static final String COLUMN_INSULIN_REACTION_TIME =   "insulin_reaction_time";
    public static final String COLUMN_GLUCAGON_SENS_RATIO =     "glucagon_sens_ratio";
    public static final String COLUMN_GLUCAGON_REACTION_TIME =  "glucagon_reaction_time";

    private static final String TABLE_CREATE_USER_DATA =
            "CREATE TABLE " + TABLE_USER_DATA + "( " +
                    COLUMN_USER_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_DATA_DATATIME + " TEXT, " +
                    COLUMN_CARB_RATIO + " INTEGER, " +
                    COLUMN_INSULIN_SENS_RATIO + " INTEGER, " +
                    COLUMN_INSULIN_REACTION_TIME + " INTEGER, " +
                    COLUMN_GLUCAGON_SENS_RATIO + " INTEGER, " +
                    COLUMN_GLUCAGON_REACTION_TIME + " INTEGER " +
                    ");";

    /******
     *  UserDataModel : dose_data
     */

    public static final String TABLE_DOSE_DATA =                "dose_data";
    public static final String COLUMN_DOSE_DATA_ID =            "id";
    public static final String COLUMN_DOSE_DATA_DATETIME =      "datetime";
    public static final String COLUMN_LAST_INSULIN_DOSE =       "last_insulin_dose";
    public static final String COLUMN_CURRENT_INSULIN_DOSE =    "current_insulin_dose";
    public static final String COLUMN_LAST_GLUCAGON_DOSE =      "last_glucagon_dose";
    public static final String COLUMN_CURRENT_GLUCAGON_DOSE =   "current_glucagon_dose";

    private static final String TABLE_CREATE_DOSE_DATA =
            "CREATE TABLE " + TABLE_DOSE_DATA + "( " +
                    COLUMN_DOSE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOSE_DATA_DATETIME + " TEXT, " +
                    COLUMN_LAST_INSULIN_DOSE + " INTEGER, " +
                    COLUMN_CURRENT_INSULIN_DOSE + " INTEGER, " +
                    COLUMN_LAST_GLUCAGON_DOSE + " INTEGER, " +
                    COLUMN_CURRENT_GLUCAGON_DOSE + " INTEGER " +
                    ");";

    /******
     *  DeviceDataModel : device_data
     */

    public static final String TABLE_DEVICE_DATA =              "device_data";
    public static final String COLUMN_DEVICE_DATA_ID =          "id";
    public static final String COLUMN_DEVICE_DATA_DATETIME =    "datetime";
    public static final String COLUMN_INSULIN_PUMP_SN =         "insulin_pump_sn";
    public static final String COLUMN_GLUCAGON_PUMP_SN =        "glucagon_pump_sn";
    public static final String COLUMN_CGM_BLE_ADDRESS =         "cgm_ble_address";

    private static final String TABLE_CREATE_DEVICE_DATA =
            "CREATE TABLE " + TABLE_DEVICE_DATA + "( " +
                    COLUMN_DEVICE_DATA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DEVICE_DATA_DATETIME + " TEXT, " +
                    COLUMN_INSULIN_PUMP_SN + " TEXT, " +
                    COLUMN_GLUCAGON_PUMP_SN + " TEXT, " +
                    COLUMN_CGM_BLE_ADDRESS + " TEXT " +
                    ");";

    /******
     *  APStateModel : ap_state
     */

    public static final String TABLE_AP_STATE =                 "ap_state";
    public static final String COLUMN_AP_ID =                   "id";
    public static final String COLUMN_AP_DATETIME =             "datetime";
    public static final String COLUMN_CURRENT_GLUCOSE =         "current_glucose";
    public static final String COLUMN_OLD_GLUCOSE =             "old_glucose";
    public static final String COLUMN_DEVICE_DATA_FK_ID =       "device_data_id";
    public static final String COLUMN_USER_DATA_FK_ID =         "user_data_id";
    public static final String COLUMN_ALGORITHM_State_FK_ID =   "algorithm_state_id";
    public static final String COLUMN_DOSE_DATA_FK_ID =   "algorithm_state_id";

    private static final String TABLE_CREATE_AP_STATE =
            "CREATE TABLE " + TABLE_AP_STATE + "( " +
                    COLUMN_AP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_AP_DATETIME + " TEXT, " +
                    COLUMN_CURRENT_GLUCOSE + " INTEGER, " +
                    COLUMN_OLD_GLUCOSE + " INTEGER, " +
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
        db.execSQL(TABLE_CREATE_AP_STATE);
        Log.i(TAG, "Tables has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }
}
