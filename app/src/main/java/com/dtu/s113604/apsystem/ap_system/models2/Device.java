package com.dtu.s113604.apsystem.ap_system.models2;

import java.util.List;

/**
 * Created by marksv on 29/01/15.
 */
public class Device {
    private long id;

    private DeviceType type;
    private String bleAddress;
    private String serialNumber;

    private Battery lBattery;
    private Reservoir lReservoir;
    private Dose lDose;

    private List<Battery> batteryHistory;
    private List<Reservoir> reservoirHistory;
    private List<Dose> doseHistory;

    public Device(DeviceType type, String bleAddress, String serialNumber, List<Battery> batteryHistory, List<Reservoir> reservoirHistory, List<Dose> doseHistory) {
        this.type = type;
        this.bleAddress = bleAddress;
        this.serialNumber = serialNumber;
        this.batteryHistory = batteryHistory;
        this.reservoirHistory = reservoirHistory;
        this.doseHistory = doseHistory;
    }

    public Device() {}

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getBleAddress() {
        return bleAddress;
    }

    public void setBleAddress(String bleAddress) {
        this.bleAddress = bleAddress;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<Battery> getBatteryHistory() {
        return batteryHistory;
    }

    public void setBatteryHistory(List<Battery> batteryHistory) {
        this.batteryHistory = batteryHistory;
    }

    public List<Reservoir> getReservoirHistory() {
        return reservoirHistory;
    }

    public void setReservoirHistory(List<Reservoir> reservoirHistory) {
        this.reservoirHistory = reservoirHistory;
    }

    public List<Dose> getDoseHistory() {
        return doseHistory;
    }

    public void setDoseHistory(List<Dose> doseHistory) {
        this.doseHistory = doseHistory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Battery getlBattery() {
        return lBattery;
    }

    public Reservoir getlReservoir() {
        return lReservoir;
    }

    public Dose getlDose() {
        return lDose;
    }

    public void addBattery(Battery battery) {
        if (battery != null) {
            batteryHistory.add(battery);
            lBattery = battery;
        }

    }

    public void addDose(Dose dose) {
        if (dose != null) {
            doseHistory.add(dose);
            lDose = dose;
        }
    }

    public void addReservoir(Reservoir res) {
        if (res != null) {
            reservoirHistory.add(res);
            lReservoir = res;
        }
    }
}
