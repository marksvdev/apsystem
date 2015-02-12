package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dtu.s113604.apsystem.ap_system.models2.APState;
import com.dtu.s113604.apsystem.ap_system.models2.AlgorithmData;
import com.dtu.s113604.apsystem.ap_system.models2.Battery;
import com.dtu.s113604.apsystem.ap_system.models2.Device;
import com.dtu.s113604.apsystem.ap_system.models2.DeviceType;
import com.dtu.s113604.apsystem.ap_system.models2.Dose;
import com.dtu.s113604.apsystem.ap_system.models2.Glucose;
import com.dtu.s113604.apsystem.ap_system.models2.Reservoir;
import com.dtu.s113604.apsystem.ap_system.models2.User;

import java.util.ArrayList;
import java.util.List;

import utils.DateTime;

/**
 * Created by marksv on 29/01/15.
 */
public class APStateDataSource2 implements IAPStateDataSource2 {
    public static final String TAG = "APStateDataSource";

    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase database;

    public APStateDataSource2(Context context) {
        dbhelper = new APDBOpenHelper2(context);
        database = dbhelper.getWritableDatabase();
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
    
    private long saveLog(String logtime) {
        openWriteable();



        // Save Log
        ContentValues logCV = new ContentValues();
        logCV.put(APDBOpenHelper2.COLUMN_LOG_DATETIME, logtime);

        long logId = database.insert(APDBOpenHelper2.TABLE_LOG, null, logCV);
        
        return logId;
    }
    
    private User saveUpdateUser(User user, long logid) {
        String now = DateTime.now();

        // Save User
        ContentValues userCV = new ContentValues();
        userCV.put(APDBOpenHelper2.COLUMN_USER_CARBRATIO, user.getCarbRatio());
        userCV.put(APDBOpenHelper2.COLUMN_USER_INSULIN_SENS, user.getInsulinSens());
        userCV.put(APDBOpenHelper2.COLUMN_USER_INSULIN_REACTIONTIME, user.getInsulinReaction());
        userCV.put(APDBOpenHelper2.COLUMN_USER_GLUCAGON_SENS, user.getGlucagonSens());
        userCV.put(APDBOpenHelper2.COLUMN_USER_GLUCAGON_REACTIONTIME, user.getGlucagonReaction());
        userCV.put(APDBOpenHelper2.COLUMN_USER_GLUCOSE_THRESHOLD_MIN, user.getMinGlucose());
        userCV.put(APDBOpenHelper2. COLUMN_USER_GLUCOSE_THRESHOLD_MAX, user.getMaxGlucose());

        if(entryExists(APDBOpenHelper2.TABLE_USER, APDBOpenHelper2.COLUMN_USER_ID + "=" + user.getId())) {
            userCV.put(APDBOpenHelper2.COLUMN_USER_MODIFIEDTIME, now);

            database.update(APDBOpenHelper2.TABLE_USER, userCV, APDBOpenHelper2.COLUMN_USER_ID + "=" + user.getId(), null);
        } else {
            userCV.put(APDBOpenHelper2.COLUMN_USER_CREATEDTIME, now);

            long userId = database.insert(APDBOpenHelper2.TABLE_USER, null, userCV);
            user.setId(userId);
        }
        return user;
    }

    private List<Glucose> saveGlucose(List<Glucose> glucoseList, long logid, long userid) {
        for (Glucose glucose : glucoseList) {
            if (glucose.getId() == null) {
                ContentValues glucoseCV = new ContentValues();
                glucoseCV.put(APDBOpenHelper2.COLUMN_GLUCOSE_MEASUREDTIME, glucose.getMeasuredTime());
                glucoseCV.put(APDBOpenHelper2.COLUMN_GLUCOSE_GLUCOSE, glucose.getLevel());
                glucoseCV.put(APDBOpenHelper2.COLUMN_GLUCOSE_USER_FK_ID, userid);
                glucoseCV.put(APDBOpenHelper2.COLUMN_GLUCOSE_LOG_FK_ID, logid);
                long glucoseId = database.insert(APDBOpenHelper2.TABLE_GLUCOSE, null, glucoseCV);
                glucose.setId(glucoseId);
            }
        }
        return glucoseList;
    }

    private List<Battery> saveBattery(List<Battery> batteryList, long logid, long deviceid) {
        for (Battery battery : batteryList) {
            if (battery.getId() == null) {
                // Save Battery
                ContentValues batteryCV = new ContentValues();
                batteryCV.put(APDBOpenHelper2.COLUMN_BATTERY_LEVEL, battery.getLevel());
                batteryCV.put(APDBOpenHelper2.COLUMN_BATTERY_MEASUREDTIME, battery.getMeasuredTime());
                batteryCV.put(APDBOpenHelper2.COLUMN_BATTERY_DEVICE_FK_ID, deviceid);
                batteryCV.put(APDBOpenHelper2.COLUMN_BATTERY_LOG_FK_ID, logid);
                long batteryId = database.insert(APDBOpenHelper2.TABLE_BATTERY, null, batteryCV);
                battery.setId(batteryId);
            }
        }
        return batteryList;
    }

    private List<Reservoir> saveReservoir(List<Reservoir> reservoirList, long logid, long deviceid) {
        for (Reservoir reservoir : reservoirList) {
            if (reservoir.getId() == null) {
                // Save Reservoir
                ContentValues reservoirCV = new ContentValues();
                reservoirCV.put(APDBOpenHelper2.COLUMN_RESERVOIR_LEVEL, reservoir.getLevel());
                reservoirCV.put(APDBOpenHelper2.COLUMN_RESERVOIR_MEASURED_TIME, reservoir.getMeasuredTime());
                reservoirCV.put(APDBOpenHelper2.COLUMN_RESERVOIR_DEVICE_FK_ID, deviceid);
                reservoirCV.put(APDBOpenHelper2.COLUMN_RESERVOIR_LOG_FK_ID, logid);
                long reservoirId = database.insert(APDBOpenHelper2.TABLE_RESERVOIR, null, reservoirCV);
                reservoir.setId(reservoirId);
            }
        }
        return reservoirList;
    }

    private List<Dose> saveDose(List<Dose> doseList, long logid, long deviceid) {
        for (Dose dose : doseList) {
            if (dose.getId() == null) {
                // Save Dose
                ContentValues doseCV = new ContentValues();
                doseCV.put(APDBOpenHelper2.COLUMN_DOSE_DOSE, dose.getLevel());
                doseCV.put(APDBOpenHelper2.COLUMN_DOSE_INJECTTIME, dose.getInjectTime());
                doseCV.put(APDBOpenHelper2.COLUMN_DOSE_DEVICE_FK_ID, deviceid);
                doseCV.put(APDBOpenHelper2.COLUMN_DOSE_LOG_FK_ID, logid);
                long doseId = database.insert(APDBOpenHelper2.TABLE_DOSE, null, doseCV);
                dose.setId(doseId);
            }
        }
        return doseList;
    }

    private AlgorithmData saveAlgorithmData(AlgorithmData data, long logid) {
        // Save Algorithm
        ContentValues algorithmCV = new ContentValues();
        algorithmCV.put(APDBOpenHelper2.COLUMN_ALGORITHM_DUMMY, data.getFoo());
        algorithmCV.put(APDBOpenHelper2.COLUMN_ALGORITHM_LOG_FK_ID, logid);
        long algorithmId = database.insert(APDBOpenHelper2.TABLE_ALGORITHM, null, algorithmCV);
        data.setId(algorithmId);

        return data;
    }
    
    private Device saveUpdateDevice(Device device, long logid) {
        ContentValues deviceCV = new ContentValues();
        deviceCV.put(APDBOpenHelper2.COLUMN_DEVICE_TYPE, device.getType().toString());
        deviceCV.put(APDBOpenHelper2.COLUMN_DEVICE_SERIAL_NUMBER, device.getSerialNumber());
        deviceCV.put(APDBOpenHelper2.COLUMN_DEVICE_BLE_ADDRESS, device.getBleAddress());

        if(entryExists(APDBOpenHelper2.TABLE_DEVICE, APDBOpenHelper2.COLUMN_DEVICE_ID + "=" + device.getId())) {
            database.update(APDBOpenHelper2.TABLE_DEVICE, deviceCV, APDBOpenHelper2.COLUMN_DEVICE_ID + "=" + device.getId(), null);
        } else {
            long deviceId = database.insert(APDBOpenHelper2.TABLE_DEVICE, null, deviceCV);
            device.setId(deviceId);
        }
        return device;
    }

    @Override
    public APState save(APState state, String logtime) {
        long logid = saveLog(logtime);

        User user = saveUpdateUser(state.getUser(), logid);
        List<Glucose> glucoseList = saveGlucose(user.getGlucoseHistory(), logid, user.getId());

        List<Device> updatedDevices = new ArrayList<Device>();

        for (Device device : state.getDeviceList()) {
            Device updatedDevice = saveUpdateDevice(device, logid);
            List<Battery> batteryList = saveBattery(device.getBatteryHistory(), logid, updatedDevice.getId());

            if (device.getType() == DeviceType.GLUCAGON_PUMP || device.getType() == DeviceType.INSULIN_PUMP) {
                List<Dose> doseList = saveDose(device.getDoseHistory(), logid, updatedDevice.getId());
                List<Reservoir> reservoirList = saveReservoir(device.getReservoirHistory(), logid, updatedDevice.getId());

                updatedDevice.setDoseHistory(doseList);
                updatedDevice.setReservoirHistory(reservoirList);
            }

            updatedDevice.setBatteryHistory(batteryList);

            updatedDevices.add(updatedDevice);
        }

        AlgorithmData data = saveAlgorithmData(state.getAlgorithmData(), logid);

        return new APState(data, user, updatedDevices);
    }



    @Override
    public APState load(int logCount) {
        openReadable();

        // Check if database is empty
        if (isDBEmpty(APDBOpenHelper2.TABLE_USER) && isDBEmpty(APDBOpenHelper2.TABLE_DEVICE)) {
            Device insulin = new Device(DeviceType.INSULIN_PUMP, "", "8347384", new ArrayList<Battery>(), new ArrayList<Reservoir>(), new ArrayList<Dose>());
            Device glucagon = new Device(DeviceType.GLUCAGON_PUMP, "", "3824898", new ArrayList<Battery>(), new ArrayList<Reservoir>(), new ArrayList<Dose>());
            Device cgm = new Device(DeviceType.CGM, "26:ff:e3:4d", "3824898", new ArrayList<Battery>(), new ArrayList<Reservoir>(), new ArrayList<Dose>());
            User user = new User(1,2,3,4,5,80,120,new ArrayList<Glucose>());
            AlgorithmData data = new AlgorithmData("foo");

            List<Device> devices = new ArrayList<Device>();
            devices.add(insulin);
            devices.add(glucagon);
            devices.add(cgm);

            return new APState(data, user, devices);
        }

        User user = loadUser();
        user.setGlucoseHistory(loadGlucose(logCount, user.getId()));

        long runLogId = getLatestRunLog();
        AlgorithmData data = loadAlgorithmData(runLogId);

        List<Device> devices = loadDevices();

        for(Device device : devices) {
            if (device.getType() == DeviceType.CGM) {
                device.setBatteryHistory(loadBattery(logCount, device.getId()));

            } else if (device.getType() == DeviceType.GLUCAGON_PUMP || device.getType() == DeviceType.INSULIN_PUMP) {
                device.setBatteryHistory(loadBattery(logCount, device.getId()));
                device.setDoseHistory(loadDose(logCount, device.getId()));
                device.setReservoirHistory(loadReservoir(logCount, device.getId()));
            }
        }

        return new APState(data, user, devices);
    }

    private List<Reservoir> loadReservoir(int count, long deviceid) {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_RESERVOIR
                + " WHERE " + APDBOpenHelper2.COLUMN_RESERVOIR_DEVICE_FK_ID + " = " + deviceid
                + " ORDER BY " + APDBOpenHelper2.COLUMN_RESERVOIR_MEASURED_TIME
                + " LIMIT " + count + ";";


        List<Reservoir> reservoirList = new ArrayList<Reservoir>();

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Reservoir reservoir = new Reservoir();
            reservoir.setId(cursor.getLong(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_RESERVOIR_ID)));
            reservoir.setLevel(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_RESERVOIR_LEVEL)));
            reservoir.setMeasuredTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_RESERVOIR_MEASURED_TIME)));

            reservoirList.add(reservoir);
            cursor.moveToNext();
        }
        return reservoirList;
    }

    private List<Dose> loadDose(int count, long deviceid) {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_DOSE
                + " WHERE " + APDBOpenHelper2.COLUMN_DOSE_DEVICE_FK_ID + " = " + deviceid
                + " ORDER BY " + APDBOpenHelper2.COLUMN_DOSE_INJECTTIME
                + " LIMIT " + count + ";";

        List<Dose> doseList = new ArrayList<Dose>();

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() < 0) {
            return doseList;
        }

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Dose dose = new Dose();
            dose.setId(cursor.getLong(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DOSE_ID)));
            dose.setLevel(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DOSE_DOSE)));
            dose.setInjectTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DOSE_INJECTTIME)));

            doseList.add(dose);
            cursor.moveToNext();
        }
        return doseList;
    }

    private List<Battery> loadBattery(int count, long deviceid){
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_BATTERY
                + " WHERE " + APDBOpenHelper2.COLUMN_BATTERY_DEVICE_FK_ID + " = " + deviceid
                + " ORDER BY " + APDBOpenHelper2.COLUMN_GLUCOSE_MEASUREDTIME
                + " LIMIT " + count + ";";

        List<Battery> batteryList = new ArrayList<Battery>();

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Battery battery = new Battery();
            battery.setId(cursor.getLong(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_BATTERY_ID)));
            battery.setMeasuredTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_BATTERY_MEASUREDTIME)));
            battery.setLevel(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_BATTERY_LEVEL)));

            batteryList.add(battery);
            cursor.moveToNext();
        }
        return batteryList;
    }

    private AlgorithmData loadAlgorithmData(long runLogId) {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_ALGORITHM
                + " WHERE " + APDBOpenHelper2.COLUMN_ALGORITHM_LOG_FK_ID + ";";

        Cursor cursor = database.rawQuery(query, null);
        AlgorithmData algorithmData = new AlgorithmData();

        if (cursor.moveToFirst()) {
            algorithmData.setId(cursor.getLong(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_ALGORITHM_ID)));
            algorithmData.setFoo(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_ALGORITHM_DUMMY)));
        }

        return algorithmData;
    }

    private long getLatestRunLog() {
        String query = "SELECT " + APDBOpenHelper2.COLUMN_LOG_ID + " FROM " + APDBOpenHelper2.TABLE_LOG
                + " ORDER BY " + APDBOpenHelper2.COLUMN_LOG_DATETIME + " DESC "
                + " LIMIT 1";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_LOG_ID));
        }

        return -1;
    }

    private List<Device> loadDevices() {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_DEVICE + ";";
        List<Device> devices = new ArrayList<Device>();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Device device = new Device();
            device.setSerialNumber(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DEVICE_SERIAL_NUMBER)));
            device.setBleAddress(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DEVICE_BLE_ADDRESS)));
            device.setId(cursor.getLong(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DEVICE_ID)));
            device.setType(DeviceType.valueOf(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_DEVICE_TYPE))));

            devices.add(device);
            cursor.moveToNext();
        }
        return devices;
    }

    private User loadUser() {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_USER
                + " ORDER BY " + APDBOpenHelper2.COLUMN_USER_MODIFIEDTIME + " DESC;";

        Cursor cursor = database.rawQuery(query, null);

        User user = new User();

        if (cursor.moveToFirst()) {
            user.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_ID)));
            user.setGlucagonReaction(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_GLUCAGON_REACTIONTIME)));
            user.setGlucagonSens(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_GLUCAGON_SENS)));
            user.setInsulinReaction(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_INSULIN_REACTIONTIME)));
            user.setInsulinSens(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_INSULIN_SENS)));
            user.setCarbRatio(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_CARBRATIO)));
            user.setMinGlucose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_GLUCOSE_THRESHOLD_MIN)));
            user.setMaxGlucose(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_USER_GLUCOSE_THRESHOLD_MAX)));
        }
        return user;
    }

    private List<Glucose> loadGlucose(int count, long userid) {
        String query = "SELECT * FROM " + APDBOpenHelper2.TABLE_GLUCOSE
                + " WHERE " + APDBOpenHelper2.COLUMN_GLUCOSE_USER_FK_ID + " = " + userid
                + " ORDER BY " + APDBOpenHelper2.COLUMN_GLUCOSE_MEASUREDTIME
                + " LIMIT " + count + ";";

        List<Glucose> glucoseList = new ArrayList<Glucose>();

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {

            Glucose glucose = new Glucose();
            glucose.setId(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_GLUCOSE_ID)));
            glucose.setLevel(cursor.getInt(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_GLUCOSE_GLUCOSE)));
            glucose.setMeasuredTime(cursor.getString(cursor.getColumnIndex(APDBOpenHelper2.COLUMN_GLUCOSE_MEASUREDTIME)));

            glucoseList.add(glucose);

            cursor.moveToNext();
        }
        return glucoseList;
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

    private boolean entryExists(String table, String whereClause) {
        String where = "SELECT * FROM " + table + " WHERE " + whereClause;
        Cursor cursor = database.rawQuery(where, null);
        cursor.moveToFirst();
        int icount = cursor.getCount();
        if(icount<=0) {
            //database is empty
            Log.i(TAG, "Database is empty");
            return false;
        } else {
            return true;
        }
    }
}
