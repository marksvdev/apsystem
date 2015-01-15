package com.dtu.s113604.apsystem.ap_system.models;

import org.w3c.dom.Document;

import utils.XMLManager;

/**
 * Created by marksv on 06/12/14.
 */
public class DeviceDataModel {
    private long id;

    private String CGMBLEAddress;
    private String CGMSerialNumber;
    private String InsulinPumpSerialNumber;
    private String GlucagonPumpSerialNumber;

    private int CGMBattery;
    private int InsulinPumpBattery;
    private int GlucagonPumpBattery;

    public DeviceDataModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCGMBLEAddress() {
        return CGMBLEAddress;
    }

    public void setCGMBLEAddress(String CGMBLEAddress) {
        this.CGMBLEAddress = CGMBLEAddress;
    }

    public String getCGMSerialNumber() {
        return CGMSerialNumber;
    }

    public void setCGMSerialNumber(String CGMSerialNumber) {
        this.CGMSerialNumber = CGMSerialNumber;
    }

    public String getInsulinPumpSerialNumber() {
        return InsulinPumpSerialNumber;
    }

    public void setInsulinPumpSerialNumber(String insulinPumpSerialNumber) {
        InsulinPumpSerialNumber = insulinPumpSerialNumber;
    }

    public String getGlucagonPumpSerialNumber() {
        return GlucagonPumpSerialNumber;
    }

    public void setGlucagonPumpSerialNumber(String glucagonPumpSerialNumber) {
        GlucagonPumpSerialNumber = glucagonPumpSerialNumber;
    }

    public int getCGMBattery() {
        return CGMBattery;
    }

    public void setCGMBattery(int CGMBattery) {
        this.CGMBattery = CGMBattery;
    }

    public int getInsulinPumpBattery() {
        return InsulinPumpBattery;
    }

    public void setInsulinPumpBattery(int insulinPumpBattery) {
        InsulinPumpBattery = insulinPumpBattery;
    }

    public int getGlucagonPumpBattery() {
        return GlucagonPumpBattery;
    }

    public void setGlucagonPumpBattery(int glucagonPumpBattery) {
        GlucagonPumpBattery = glucagonPumpBattery;
    }


    //
//    public String getStateProperties() {
//        return XMLManager.DocumentToString(stateProperties);
//    }
//
//    public void setStateProperties(String state) {
//        stateProperties = XMLManager.loadXMLFromString(state);
//    }
//
//    public Document getXML() {
//        return stateProperties;
//    }
//
//    public void setXML(Document doc) {
//        stateProperties = doc;
//    }
//
//    public DeviceDataModel(Document stateProperties) {
//        this.stateProperties = stateProperties;
//    }
//
//    public DeviceDataModel() {
//        stateProperties = XMLManager.generateNewDocument();
//    }

}
