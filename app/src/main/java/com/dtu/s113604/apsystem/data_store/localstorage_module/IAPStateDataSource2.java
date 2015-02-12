package com.dtu.s113604.apsystem.data_store.localstorage_module;

import com.dtu.s113604.apsystem.ap_system.models2.APState;

/**
 * Created by marksv on 30/01/15.
 */
public interface IAPStateDataSource2 {
    APState save(APState state, String logtime);

    APState load(int logCount);
}
