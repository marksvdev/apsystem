package com.dtu.s113604.apsystem.data_store.localstorage_module;

import com.dtu.s113604.apsystem.models.APStateModel;

/**
 * Created by marksv on 07/12/14.
 */
public interface IAPStateDataSource {
    // Opens a connection to the database
    void open();

    // Closes the connection to the database
    void close();

    // Creates a new AP State entry in the database
    APStateModel save(APStateModel state);

    // Loads the latest stored AP State from the database
    APStateModel load();
}
