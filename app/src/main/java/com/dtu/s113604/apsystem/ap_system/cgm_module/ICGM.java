package com.dtu.s113604.apsystem.ap_system.cgm_module;

/**
 * Created by marksv on 06/12/14.
 */
public interface ICGM {
    int getGlucoseValue(String bleAddress, String SN);
    String getInfo(String bleAddress, String SN);
    int getBattery(String bleAddress, String SN);
}
