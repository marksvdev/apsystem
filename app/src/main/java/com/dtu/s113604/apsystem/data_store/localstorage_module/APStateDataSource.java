package com.dtu.s113604.apsystem.data_store.localstorage_module;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dtu.s113604.apsystem.models.APStateModel;

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

    @Override
    // Opens a connection to the database
    public void open() {
        Log.i(TAG, "Database opened");
        database = dbhelper.getWritableDatabase();
    }

    @Override
    // Closes the connection to the database
    public void close() {
        Log.i(TAG, "Database closed");
        dbhelper.close();
    }

    // Creates a new AP State entry in the database
    @Override
    public APStateModel save(APStateModel state) {
        ContentValues values = new ContentValues();
        values.put(APDBOpenHelper.COLUMN_AP_DATETIME, state.getDatetime());
        values.put(APDBOpenHelper.COLUMN_CURRENT_GLUCOSE, state.getCurrentGlucoseValue());
        values.put(APDBOpenHelper.COLUMN_OLD_GLUCOSE, state.getLastGlucoseValue());

        long insertid = database.insert(APDBOpenHelper.TABLE_AP_STATE, null, values);
        state.setId(insertid);
        return state;

        // TODO Create tables for devicedata, algorithmstate, dosedata, userdata;
    }

    // Loads the latest stored AP State from the database
    @Override
    public APStateModel load() {

        return null;
    }
}
