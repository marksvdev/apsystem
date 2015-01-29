package com.dtu.s113604.apsystem.system_monitor;

import android.content.Context;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;

/**
 * Created by marksv on 21/01/15.
 */
public interface IISystemMonitor {
    /**
     * Starts a system monitor thread
     * @param context
     * @param state
     */
    void startSystemMonitor(Context context, APStateModel state);
}
