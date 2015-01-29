package com.dtu.s113604.apsystem.system_monitor;

import android.content.Context;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;

/**
 * Created by marksv on 21/01/15.
 */
public class InitSystemMonitor implements IISystemMonitor {

    /**
     * {@inheritDoc}
     */
    @Override
    public void startSystemMonitor(Context context, APStateModel state) {
        (new Thread(new SystemMonitor(context, state))).start();
    }
}
