package com.dtu.s113604.apsystem.data_store.localstorage_module;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;

/**
 * Created by marksv on 07/12/14.
 */
public interface IAPStateDataSource {
    // Creates a new AP State entry in the database
    APStateModel save(APStateModel state);

    // Loads the latest stored AP State from the database
    APStateModel load();
}
