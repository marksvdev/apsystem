package com.dtu.s113604.apsystem.ap_system.models;

import org.w3c.dom.Document;

import utils.XMLManager;

/**
 * Created by marksv on 06/12/14.
 */
/*

Patient Parameters {Carb Ratio, Insulin Sensitivity Factor, Insulin Reaction Time, Glucagon Sensitivity, Glucagon Reaction Time}

*/

public class UserDataModel {

    private long id;

    private int carbRatio;
    private int insulinSensitivity;
    private int insulinReactionTime;
    private int glucagonSensitivity;
    private int glucagonReactionTime;

    private int glucoseThresholdMax;
    private int glucoseThresholdMin;


    public UserDataModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCarbRatio() {
        return carbRatio;
    }

    public void setCarbRatio(int carbRatio) {
        this.carbRatio = carbRatio;
    }

    public int getInsulinSensitivity() {
        return insulinSensitivity;
    }

    public void setInsulinSensitivity(int insulinSensitivity) {
        this.insulinSensitivity = insulinSensitivity;
    }

    public int getInsulinReactionTime() {
        return insulinReactionTime;
    }

    public void setInsulinReactionTime(int insulinReactionTime) {
        this.insulinReactionTime = insulinReactionTime;
    }

    public int getGlucagonSensitivity() {
        return glucagonSensitivity;
    }

    public void setGlucagonSensitivity(int glucagonSensitivity) {
        this.glucagonSensitivity = glucagonSensitivity;
    }

    public int getGlucagonReactionTime() {
        return glucagonReactionTime;
    }

    public void setGlucagonReactionTime(int glucagonReactionTime) {
        this.glucagonReactionTime = glucagonReactionTime;
    }

    public int getGlucoseThresholdMax() {
        return glucoseThresholdMax;
    }

    public void setGlucoseThresholdMax(int glucoseThresholdMax) {
        this.glucoseThresholdMax = glucoseThresholdMax;
    }

    public int getGlucoseThresholdMin() {
        return glucoseThresholdMin;
    }

    public void setGlucoseThresholdMin(int glucoseThresholdMin) {
        this.glucoseThresholdMin = glucoseThresholdMin;
    }
}
