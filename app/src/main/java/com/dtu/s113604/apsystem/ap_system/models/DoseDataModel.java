package com.dtu.s113604.apsystem.ap_system.models;

/**
 * Created by marksv on 06/12/14.
 */
public class DoseDataModel {

    private long id;

    private int currentInsulinDose = 0;
    private int lastInsulinDose = 0;

    private int currentGlugaconDose = 0;
    private int lastGlucagonDose = 0;

    public DoseDataModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCurrentInsulinDose() {
        return currentInsulinDose;
    }

    public void setCurrentInsulinDose(int currentInsulinDose) {
        this.currentInsulinDose = currentInsulinDose;
    }

    public int getLastInsulinDose() {
        return lastInsulinDose;
    }

    public void setLastInsulinDose(int lastInsulinDose) {
        this.lastInsulinDose = lastInsulinDose;
    }

    public int getCurrentGlugaconDose() {
        return currentGlugaconDose;
    }

    public void setCurrentGlugaconDose(int currentGlugaconDose) {
        this.currentGlugaconDose = currentGlugaconDose;
    }

    public int getLastGlucagonDose() {
        return lastGlucagonDose;
    }

    public void setLastGlucagonDose(int lastGlucagonDose) {
        this.lastGlucagonDose = lastGlucagonDose;
    }
}
