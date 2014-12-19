package com.dtu.s113604.apsystem.models;

/**
 * Created by marksv on 06/12/14.
 */
public class DoseDataModel {

    private int lastInsulinDose;
    private int currentInsulinDose;
    private int lastGlucagonDose;
    private int currentGlucagonDose;

    public int getLastInsulinDose() {
        return lastInsulinDose;
    }

    public int getCurrentInsulinDose() {
        return currentInsulinDose;
    }

    public void setCurrentInsulinDose(int value) {
        this.lastInsulinDose = this.currentInsulinDose;
        this.currentInsulinDose = value;
    }

    public int getLastGlucagonDose() {
        return lastGlucagonDose;
    }

    public int getCurrentGlucagonDose() {
        return currentGlucagonDose;
    }

    public void setCurrentGlucagonDose(int value) {
        this.lastGlucagonDose = currentGlucagonDose;
        this.currentGlucagonDose = value;
    }

    @Override
    public String toString() {
        return "GlucagonDoseModel{" +
                "lastInsulinDose=" + lastInsulinDose +
                ", currentInsulinDose=" + currentInsulinDose +
                ", lastGlucagonDose=" + lastGlucagonDose +
                ", currentGlucagonDose=" + currentGlucagonDose +
                '}';
    }
}
