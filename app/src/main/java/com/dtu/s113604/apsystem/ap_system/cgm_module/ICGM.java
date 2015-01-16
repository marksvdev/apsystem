package com.dtu.s113604.apsystem.ap_system.cgm_module;

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
    int getGlucoseValue(String bleAddress, String SN);

    /**
     * Gets the battery level of the CGM
     * @param bleAddress
     * @PARAM SN
     * @RETURN
     */
    int getBattery(String bleAddress, String SN);
}
