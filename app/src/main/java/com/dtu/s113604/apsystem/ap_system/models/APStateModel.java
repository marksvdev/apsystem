package com.dtu.s113604.apsystem.ap_system.models;

import org.w3c.dom.Document;

import utils.XMLManager;

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

    private int currentGlucose;
    private String currentGlucoseDateTime;
    private int lastGlucose;
    private String lastGlucoseDateTime;

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

    private String getLastGlucoseDateTime() {
        return lastGlucoseDateTime;
    }

    public void setLastGlucoseDateTime(String lastGlucoseDateTime) {
        this.lastGlucoseDateTime = lastGlucoseDateTime;
    }
}
