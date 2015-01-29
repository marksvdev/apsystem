package com.dtu.s113604.apsystem.ap_system.models;

import com.dtu.s113604.apsystem.activities.ViewWrapper;

/**
 * Created by marksv on 06/12/14.
 */

/*
    Last CGM value
    Insulin Pump Serial Number
    Glucagon Pump Serial Number
    CGM Receiver BLE Address
    Patient Parameters {Carb Ratio, Insulin Sensitivity Factor, Insulin Reaction Time, Glucagon Sensitivity, Glucagon Reaction Time}
    Control Algorithm State?

*/

public class APStateModel {

    private long id;
    private String datetime;

    private int currentGlucose = 90;
    private String currentGlucoseDateTime = "";
    private int lastGlucose = 90;
    private String lastGlucoseDateTime = "";

    private DoseDataModel doseData;
    private DeviceDataModel deviceData;
    private UserDataModel patientParameters;
    private AlgorithmStateModel algorithmState;

    public APStateModel(DeviceDataModel deviceData, UserDataModel patientParameters, AlgorithmStateModel algorithmState, DoseDataModel doseData) {
        this.deviceData = deviceData;
        this.patientParameters = patientParameters;
        this.algorithmState = algorithmState;
        this.doseData = doseData;
    }

    public APStateModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getCurrentGlucose() {
        return currentGlucose;
    }

    public void setCurrentGlucose(int currentGlucose) {
        setLastGlucose(this.currentGlucose);
        this.currentGlucose = currentGlucose;
    }

    public int getLastGlucose() {
        return lastGlucose;
    }

    public void setLastGlucose(int lastGlucose) {
        this.lastGlucose = lastGlucose;
    }

    public DoseDataModel getDoseData() {
        return doseData;
    }

    public void setDoseData(DoseDataModel doseData) {
        this.doseData = doseData;
    }

    public DeviceDataModel getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(DeviceDataModel deviceData) {
        this.deviceData = deviceData;
    }

    public UserDataModel getPatientParameters() {
        return patientParameters;
    }

    public void setPatientParameters(UserDataModel patientParameters) {
        this.patientParameters = patientParameters;
    }

    public AlgorithmStateModel getAlgorithmState() {
        return algorithmState;
    }

    public void setAlgorithmState(AlgorithmStateModel algorithmState) {
        this.algorithmState = algorithmState;
    }

    public String getCurrentGlucoseDateTime() {
        return currentGlucoseDateTime;
    }

    public void setCurrentGlucoseDateTime(String currentGlucoseDateTime) {
        setLastGlucoseDateTime(this.currentGlucoseDateTime);
        this.currentGlucoseDateTime = currentGlucoseDateTime;
    }

    public String getLastGlucoseDateTime() {
        return lastGlucoseDateTime;
    }

    public void setLastGlucoseDateTime(String lastGlucoseDateTime) {
        this.lastGlucoseDateTime = lastGlucoseDateTime;
    }

    public ViewWrapper makeWrapper() {
        // make wrapper
        ViewWrapper wrapper = new ViewWrapper();

        wrapper.setCGMBLEAddress(getDeviceData().getCGMBLEAddress());
        wrapper.setCGMSN(getDeviceData().getCGMSerialNumber());
        wrapper.setInsulinPumpSN(getDeviceData().getInsulinPumpSerialNumber());
        wrapper.setGlucagonPumpSN(getDeviceData().getGlucagonPumpSerialNumber());
        wrapper.setInsulinSensitivity(String.valueOf(getPatientParameters().getInsulinSensitivity()));
        wrapper.setGlucagonSensitivity(String.valueOf(getPatientParameters().getGlucagonSensitivity()));
        wrapper.setGlucoseThresholdMax(String.valueOf(getPatientParameters().getGlucoseThresholdMax()));
        wrapper.setGlucoseThresholdMin(String.valueOf(getPatientParameters().getGlucoseThresholdMin()));
        wrapper.setCarbRatio(String.valueOf(getPatientParameters().getCarbRatio()));
        wrapper.setInsulinReactionTime(String.valueOf(getPatientParameters().getInsulinReactionTime()));
        wrapper.setGlucagonReactionTime(String.valueOf(getPatientParameters().getGlucagonReactionTime()));

        return wrapper;
    }

    public void makeUnWrap(ViewWrapper wrapper) {
        getDeviceData().setCGMBLEAddress(wrapper.getCGMBLEAddress());
        getDeviceData().setCGMSerialNumber(wrapper.getCGMSN());
        getDeviceData().setInsulinPumpSerialNumber(wrapper.getInsulinPumpSN());
        getDeviceData().setGlucagonPumpSerialNumber(wrapper.getGlucagonPumpSN());
        getPatientParameters().setInsulinSensitivity(Integer.valueOf(wrapper.getInsulinSensitivity()));
        getPatientParameters().setGlucagonSensitivity(Integer.valueOf(wrapper.getGlucagonSensitivity()));
        getPatientParameters().setGlucoseThresholdMax(Integer.valueOf(wrapper.getGlucoseThresholdMax()));
        getPatientParameters().setGlucoseThresholdMin(Integer.valueOf(wrapper.getGlucoseThresholdMin()));
        getPatientParameters().setCarbRatio(Integer.valueOf(wrapper.getCarbRatio()));
        getPatientParameters().setInsulinReactionTime(Integer.valueOf(wrapper.getInsulinSensitivity()));
        getPatientParameters().setGlucagonReactionTime(Integer.valueOf(wrapper.getGlucagonReactionTime()));
    }
}
