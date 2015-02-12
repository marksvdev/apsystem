package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marksv on 29/01/15.
 */
public class APDBOpenHelper2 extends SQLiteOpenHelper {
    private static final String TAG = "APDBOpenHelper";
    private static final String DATABASE_NAME = "ap.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * ***
     *  : ap_run_log
     */

    public static final String TABLE_LOG = "ap_run_log";
    public static final String COLUMN_LOG_ID = "id";
    public static final String COLUMN_LOG_DATETIME = "datetime";

    private static final String TABLE_CREATE_LOG =
            "CREATE TABLE " + TABLE_LOG + "( " +
                    COLUMN_LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LOG_DATETIME + " TEXT " +
                    ");";

    /**
     * ***
     * UserModel : user
     */

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_CARBRATIO = "carb_ratio";
    public static final String COLUMN_USER_INSULIN_SENS = "insulin_sens";
    public static final String COLUMN_USER_INSULIN_REACTIONTIME = "insulin_reactiontime";
    public static final String COLUMN_USER_GLUCAGON_SENS = "glucagon_sens";
    public static final String COLUMN_USER_GLUCAGON_REACTIONTIME = "glucagon_reactiontime";
    public static final String COLUMN_USER_GLUCOSE_THRESHOLD_MAX = "glucose_threshold_max";
    public static final String COLUMN_USER_GLUCOSE_THRESHOLD_MIN = "glucose_threshold_min";
    public static final String COLUMN_USER_CREATEDTIME = "created_time";
    public static final String COLUMN_USER_MODIFIEDTIME = "modified_time";

    private static final String TABLE_CREATE_USER =
            "CREATE TABLE " + TABLE_USER + "( " +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_CARBRATIO + " INTEGER, " +
                    COLUMN_USER_INSULIN_SENS + " INTEGER, " +
                    COLUMN_USER_INSULIN_REACTIONTIME + " INTEGER, " +
                    COLUMN_USER_GLUCAGON_SENS + " INTEGER, " +
                    COLUMN_USER_GLUCAGON_REACTIONTIME + " INTEGER, " +
                    COLUMN_USER_GLUCOSE_THRESHOLD_MAX + " INTEGER, " +
                    COLUMN_USER_GLUCOSE_THRESHOLD_MIN + " INTEGER, " +
                    COLUMN_USER_CREATEDTIME + " TEXT, " +
                    COLUMN_USER_MODIFIEDTIME + " TEXT " +
                    ");";

    /**
     * ***
     * GlucoseModel : glucose
     */

    public static final String TABLE_GLUCOSE = "glucose";
    public static final String COLUMN_GLUCOSE_ID = "id";
    public static final String COLUMN_GLUCOSE_MEASUREDTIME = "measured_time";
    public static final String COLUMN_GLUCOSE_GLUCOSE = "glucose";
    public static final String COLUMN_GLUCOSE_USER_FK_ID = "user_id";
    public static final String COLUMN_GLUCOSE_LOG_FK_ID = "log_id";

    private static final String TABLE_CREATE_GLUCOSE =
            "CREATE TABLE " + TABLE_GLUCOSE + "( " +
                    COLUMN_GLUCOSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_GLUCOSE_MEASUREDTIME + " TEXT, " +
                    COLUMN_GLUCOSE_GLUCOSE + " INTEGER, " +
                    COLUMN_GLUCOSE_USER_FK_ID + " INTEGER, " +
                    COLUMN_GLUCOSE_LOG_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_GLUCOSE_USER_FK_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_GLUCOSE_LOG_FK_ID + ") REFERENCES " + TABLE_LOG + "(" + COLUMN_LOG_ID + ") " +
                    ");";


    /**
     * ***
     * DeviceModel : device
     */

    public static final String TABLE_DEVICE = "device";
    public static final String COLUMN_DEVICE_ID = "id";
    public static final String COLUMN_DEVICE_TYPE = "type";
    public static final String COLUMN_DEVICE_SERIAL_NUMBER = "sn";
    public static final String COLUMN_DEVICE_BLE_ADDRESS = "ble_address";

    private static final String TABLE_CREATE_DEVICE =
            "CREATE TABLE " + TABLE_DEVICE + "( " +
                    COLUMN_DEVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DEVICE_TYPE + " TEXT, " +
                    COLUMN_DEVICE_SERIAL_NUMBER + " TEXT, " +
                    COLUMN_DEVICE_BLE_ADDRESS + " TEXT " +
                    ");";

    /**
     * ***
     * DoseModel : dose
     */

    public static final String TABLE_DOSE = "dose";
    public static final String COLUMN_DOSE_ID = "id";
    public static final String COLUMN_DOSE_DOSE = "dose";
    public static final String COLUMN_DOSE_INJECTTIME = "inject_time";
    public static final String COLUMN_DOSE_DEVICE_FK_ID = "device_id";
    public static final String COLUMN_DOSE_LOG_FK_ID = "log_id";

    private static final String TABLE_CREATE_DOSE =
            "CREATE TABLE " + TABLE_DOSE + "( " +
                    COLUMN_DOSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DOSE_INJECTTIME + " TEXT, " +
                    COLUMN_DOSE_DOSE + " INTEGER, " +
                    COLUMN_DOSE_DEVICE_FK_ID + " INTEGER, " +
                    COLUMN_DOSE_LOG_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_DOSE_DEVICE_FK_ID + ") REFERENCES " + TABLE_DEVICE + "(" + COLUMN_DEVICE_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_DOSE_LOG_FK_ID + ") REFERENCES " + TABLE_LOG + "(" + COLUMN_LOG_ID + ") " +
                    ");";

    /**
     * ***
     * BatteryModel : battery
     */

    public static final String TABLE_BATTERY = "battery";
    public static final String COLUMN_BATTERY_ID = "id";
    public static final String COLUMN_BATTERY_MEASUREDTIME = "measured_time";
    public static final String COLUMN_BATTERY_LEVEL = "level";
    public static final String COLUMN_BATTERY_DEVICE_FK_ID = "device_id";
    public static final String COLUMN_BATTERY_LOG_FK_ID = "log_id";

    private static final String TABLE_CREATE_BATTERY =
            "CREATE TABLE " + TABLE_BATTERY + "( " +
                    COLUMN_BATTERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BATTERY_MEASUREDTIME + " TEXT, " +
                    COLUMN_BATTERY_LEVEL + " INTEGER, " +
                    COLUMN_BATTERY_DEVICE_FK_ID + " INTEGER, " +
                    COLUMN_BATTERY_LOG_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_BATTERY_DEVICE_FK_ID + ") REFERENCES " + TABLE_DEVICE + "(" + COLUMN_DEVICE_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_BATTERY_LOG_FK_ID + ") REFERENCES " + TABLE_LOG + "(" + COLUMN_LOG_ID + ") " +
                    ");";

    /**
     * ***
     * algorithmStateModel : algorithm_state
     */

    public static final String TABLE_ALGORITHM = "algorithm_state";
    public static final String COLUMN_ALGORITHM_ID = "id";
    public static final String COLUMN_ALGORITHM_DUMMY = "dummy";
    public static final String COLUMN_ALGORITHM_LOG_FK_ID = "log_id";

    private static final String TABLE_CREATE_ALGORITHM =
            "CREATE TABLE " + TABLE_ALGORITHM + "( " +
                    COLUMN_ALGORITHM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ALGORITHM_DUMMY + " TEXT, " +
                    COLUMN_ALGORITHM_LOG_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_ALGORITHM_LOG_FK_ID + ") REFERENCES " + TABLE_LOG + "(" + COLUMN_LOG_ID + ") " +
                    ");";

    /**
     * ***
     * reservoirModel : reservoir
     */

    public static final String TABLE_RESERVOIR = "reservoir";
    public static final String COLUMN_RESERVOIR_ID = "id";
    public static final String COLUMN_RESERVOIR_MEASURED_TIME = "measured_time";
    public static final String COLUMN_RESERVOIR_LEVEL = "level";
    public static final String COLUMN_RESERVOIR_DEVICE_FK_ID = "device_id";
    public static final String COLUMN_RESERVOIR_LOG_FK_ID = "log_id";

    private static final String TABLE_CREATE_RESERVOIR =
            "CREATE TABLE " + TABLE_RESERVOIR + "( " +
                    COLUMN_RESERVOIR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RESERVOIR_MEASURED_TIME + " TEXT, " +
                    COLUMN_RESERVOIR_LEVEL + " INTEGER, " +
                    COLUMN_RESERVOIR_DEVICE_FK_ID + " INTEGER, " +
                    COLUMN_RESERVOIR_LOG_FK_ID + " INTEGER, " +
                    "FOREIGN KEY(" + COLUMN_RESERVOIR_DEVICE_FK_ID + ") REFERENCES " + TABLE_DEVICE + "(" + COLUMN_DEVICE_ID + "), " +
                    "FOREIGN KEY(" + COLUMN_RESERVOIR_LOG_FK_ID + ") REFERENCES " + TABLE_LOG + "(" + COLUMN_LOG_ID + ") " +
                    ");";

    public APDBOpenHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DEVICE);
        db.execSQL(TABLE_CREATE_USER);
        db.execSQL(TABLE_CREATE_LOG);
        db.execSQL(TABLE_CREATE_GLUCOSE);
        db.execSQL(TABLE_CREATE_ALGORITHM);
        db.execSQL(TABLE_CREATE_BATTERY);
        db.execSQL(TABLE_CREATE_RESERVOIR);
        db.execSQL(TABLE_CREATE_DOSE);
        Log.i(TAG, "Tables has been created");

        //db.execSQL("CREATE INDEX " + TABLE_GLUCOSE + "_"+ COLUMN_GLUCOSE_MEASUREDTIME +"_idx ON " + TABLE_GLUCOSE+"("+COLUMN_GLUCOSE_MEASUREDTIME+");");
        Log.i(TAG, "Indexes has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {

    }
}
