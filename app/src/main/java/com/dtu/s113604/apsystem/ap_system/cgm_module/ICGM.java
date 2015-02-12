package com.dtu.s113604.apsystem.ap_system.cgm_module;

import com.dtu.s113604.apsystem.ap_system.models2.Battery;
import com.dtu.s113604.apsystem.ap_system.models2.Glucose;

/**
 * Created by marksv on 06/12/14.
 */
public interface ICGM {
    /**
     * Fetch the latest glucose value from the CGM
     * @param bleAddress
     * @param SN
     * @return
     */
    Glucose getGlucoseValue(String bleAddress, String SN);

    /**
     * Gets the battery level of the CGM
     * @param bleAddress
     * @PARAM SN
     * @RETURN
     */
    Battery getBattery(String bleAddress, String SN);
}
