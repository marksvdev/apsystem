package com.dtu.s113604.apsystem.models;

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
    private String stateProperties;

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

    public APStateModel () {}

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

    public String getStateProperties() {
        return stateProperties;
    }

    public void setStateProperties(String stateProperties) {
        this.stateProperties = stateProperties;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "APStateModel{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", stateProperties='" + stateProperties + '\'' +
                ", doseData=" + doseData +
                ", deviceData=" + deviceData +
                ", patientParameters=" + patientParameters +
                ", algorithmState=" + algorithmState +
                '}';
    }
}
