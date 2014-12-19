package com.dtu.s113604.apsystem.models;

/**
 * Created by marksv on 06/12/14.
 */

/*

Patient Parameters {Carb Ratio, Insulin Sensitivity Factor, Insulin Reaction Time, Glucagon Sensitivity, Glucagon Reaction Time}

*/

public class UserDataModel {

    private int carbRatio;
    private int insulinSensitivityFactor;
    private int insulinReactionTime;
    private int glucagonSensitivityFactor;
    private int glucagonReactionTime;

    public UserDataModel(int carbRatio, int insulinSensitivityFactor, int insulinReactionTime, int glucagonSensitivityFactor, int glucagonReactionTime) {
        this.carbRatio = carbRatio;
        this.insulinSensitivityFactor = insulinSensitivityFactor;
        this.insulinReactionTime = insulinReactionTime;
        this.glucagonSensitivityFactor = glucagonSensitivityFactor;
        this.glucagonReactionTime = glucagonReactionTime;
    }

    public int getCarbRatio() {
        return carbRatio;
    }

    public void setCarbRatio(int carbRatio) {
        this.carbRatio = carbRatio;
    }

    public int getInsulinSensitivityFactor() {
        return insulinSensitivityFactor;
    }

    public void setInsulinSensitivityFactor(int insulinSensitivityFactor) {
        this.insulinSensitivityFactor = insulinSensitivityFactor;
    }

    public int getInsulinReactionTime() {
        return insulinReactionTime;
    }

    public void setInsulinReactionTime(int insulinReactionTime) {
        this.insulinReactionTime = insulinReactionTime;
    }

    public int getGlucagonSensitivityFactor() {
        return glucagonSensitivityFactor;
    }

    public void setGlucagonSensitivityFactor(int glucagonSensitivityFactor) {
        this.glucagonSensitivityFactor = glucagonSensitivityFactor;
    }

    public int getGlucagonReactionTime() {
        return glucagonReactionTime;
    }

    public void setGlucagonReactionTime(int glucagonReactionTime) {
        this.glucagonReactionTime = glucagonReactionTime;
    }

    @Override
    public String toString() {
        return "UserDataModel{" +
                "carbRatio=" + carbRatio +
                ", insulinSensitivityFactor=" + insulinSensitivityFactor +
                ", insulinReactionTime=" + insulinReactionTime +
                ", glucagonSensitivityFactor=" + glucagonSensitivityFactor +
                ", glucagonReactionTime=" + glucagonReactionTime +
                '}';
    }
}
