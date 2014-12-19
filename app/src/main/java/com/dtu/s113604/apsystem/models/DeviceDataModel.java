package com.dtu.s113604.apsystem.models;

/**
 * Created by marksv on 06/12/14.
 */
public class DeviceDataModel {
    private String insulinPumpSerialNumber;
    private String glucagonPumpSerialNumber;
    private String CGMReceiverBLEAddress;

    public DeviceDataModel(String insulinPumpSerialNumber, String glucagonPumpSerialNumber, String CGMReceiverBLEAddress) {
        this.insulinPumpSerialNumber = insulinPumpSerialNumber;
        this.glucagonPumpSerialNumber = glucagonPumpSerialNumber;
        this.CGMReceiverBLEAddress = CGMReceiverBLEAddress;
    }

    public String getInsulinPumpSerialNumber() {
        return insulinPumpSerialNumber;
    }

    public void setInsulinPumpSerialNumber(String insulinPumpSerialNumber) {
        this.insulinPumpSerialNumber = insulinPumpSerialNumber;
    }

    public String getGlucagonPumpSerialNumber() {
        return glucagonPumpSerialNumber;
    }

    public void setGlucagonPumpSerialNumber(String glucagonPumpSerialNumber) {
        this.glucagonPumpSerialNumber = glucagonPumpSerialNumber;
    }

    public String getCGMReceiverBLEAddress() {
        return CGMReceiverBLEAddress;
    }

    public void setCGMReceiverBLEAddress(String CGMReceiverBLEAddress) {
        this.CGMReceiverBLEAddress = CGMReceiverBLEAddress;
    }

    @Override
    public String toString() {
        return "DeviceDataModel{" +
                "insulinPumpSerialNumber='" + insulinPumpSerialNumber + '\'' +
                ", glucagonPumpSerialNumber='" + glucagonPumpSerialNumber + '\'' +
                ", CGMReceiverBLEAddress='" + CGMReceiverBLEAddress + '\'' +
                '}';
    }
}
