package com.dtu.s113604.apsystem.activities;

/**
 * Created by marksv on 16/01/15.
 */
public class ViewWrapper {
    // device
    private String CGMBLEAddress;
    private String CGMSN;
    private String InsulinPumpSN;
    private String GlucagonPumpSN;

    // user
    private String InsulinSensitivity;
    private String GlucagonSensitivity;
    private String GlucoseThresholdMax;
    private String GlucoseThresholdMin;
    private String CarbRatio;
    private String InsulinReactionTime;
    private String GlucagonReactionTime;

    public ViewWrapper(){}

    public String getCGMBLEAddress() {
        return CGMBLEAddress;
    }

    public void setCGMBLEAddress(String CGMBLEAddress) {
        this.CGMBLEAddress = CGMBLEAddress;
    }

    public String getCGMSN() {
        return CGMSN;
    }

    public void setCGMSN(String CGMSN) {
        this.CGMSN = CGMSN;
    }

    public String getInsulinPumpSN() {
        return InsulinPumpSN;
    }

    public void setInsulinPumpSN(String insulinPumpSN) {
        InsulinPumpSN = insulinPumpSN;
    }

    public String getGlucagonPumpSN() {
        return GlucagonPumpSN;
    }

    public void setGlucagonPumpSN(String glucagonPumpSN) {
        GlucagonPumpSN = glucagonPumpSN;
    }

    public String getInsulinSensitivity() {
        return InsulinSensitivity;
    }

    public void setInsulinSensitivity(String insulinSensitivity) {
        InsulinSensitivity = insulinSensitivity;
    }

    public String getGlucagonSensitivity() {
        return GlucagonSensitivity;
    }

    public void setGlucagonSensitivity(String glucagonSensitivity) {
        GlucagonSensitivity = glucagonSensitivity;
    }

    public String getGlucoseThresholdMax() {
        return GlucoseThresholdMax;
    }

    public void setGlucoseThresholdMax(String glucoseThresholdMax) {
        GlucoseThresholdMax = glucoseThresholdMax;
    }

    public String getGlucoseThresholdMin() {
        return GlucoseThresholdMin;
    }

    public void setGlucoseThresholdMin(String glucoseThresholdMin) {
        GlucoseThresholdMin = glucoseThresholdMin;
    }

    public String getCarbRatio() {
        return CarbRatio;
    }

    public void setCarbRatio(String carbRatio) {
        CarbRatio = carbRatio;
    }

    public String getInsulinReactionTime() {
        return InsulinReactionTime;
    }

    public void setInsulinReactionTime(String insulinReactionTime) {
        InsulinReactionTime = insulinReactionTime;
    }

    public String getGlucagonReactionTime() {
        return GlucagonReactionTime;
    }

    public void setGlucagonReactionTime(String glucagonReactionTime) {
        GlucagonReactionTime = glucagonReactionTime;
    }
}
