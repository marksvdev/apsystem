package com.dtu.s113604.apsystem.ap_system.models2;

import android.util.Log;

import com.dtu.s113604.apsystem.activities.ViewWrapper;
import com.dtu.s113604.apsystem.ap_system.cgm_module.CGM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marksv on 29/01/15.
 */
public class APState {
    private AlgorithmData algorithmData;
    private User user;
    private List<Device> deviceList;

    public APState(AlgorithmData algorithmData, User user, List<Device> deviceList) {
        this.algorithmData = algorithmData;
        this.user = user;
        this.deviceList = deviceList;
    }

    public AlgorithmData getAlgorithmData() {
        return algorithmData;
    }

    public void setAlgorithmData(AlgorithmData algorithmData) {
        this.algorithmData = algorithmData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public Device getDevice(DeviceType type) {
        if (deviceList != null) {
            for (Device device : deviceList) {
                if (device.getType() == type) {
                    return device;
                }
            }
        }
        return null;
    }

    public ViewWrapper makeWrapper() {
        // make wrapper
        ViewWrapper wrapper = new ViewWrapper();

        wrapper.setCGMBLEAddress(getDevice(DeviceType.CGM).getBleAddress());
        wrapper.setCGMSN(getDevice(DeviceType.CGM).getSerialNumber());

        wrapper.setInsulinPumpSN(getDevice(DeviceType.INSULIN_PUMP).getSerialNumber());
        wrapper.setGlucagonPumpSN(getDevice(DeviceType.GLUCAGON_PUMP).getSerialNumber());

        wrapper.setInsulinSensitivity(String.valueOf(user.getInsulinSens()));
        wrapper.setGlucagonSensitivity(String.valueOf(user.getGlucagonSens()));
        wrapper.setGlucoseThresholdMax(String.valueOf(user.getMaxGlucose()));
        wrapper.setGlucoseThresholdMin(String.valueOf(user.getMinGlucose()));
        wrapper.setCarbRatio(String.valueOf(user.getCarbRatio()));
        wrapper.setInsulinReactionTime(String.valueOf(user.getInsulinReaction()));
        wrapper.setGlucagonReactionTime(String.valueOf(user.getGlucagonReaction()));

        return wrapper;
    }

    public void makeUnWrap(ViewWrapper wrapper) {
        getDevice(DeviceType.CGM).setBleAddress(wrapper.getCGMBLEAddress());
        getDevice(DeviceType.CGM).setSerialNumber(wrapper.getCGMSN());

        getDevice(DeviceType.INSULIN_PUMP).setSerialNumber(wrapper.getInsulinPumpSN());
        getDevice(DeviceType.GLUCAGON_PUMP).setSerialNumber(wrapper.getGlucagonPumpSN());

        user.setInsulinSens(Integer.valueOf(wrapper.getInsulinSensitivity()));
        user.setGlucagonSens(Integer.valueOf(wrapper.getGlucagonSensitivity()));
        user.setMaxGlucose(Integer.valueOf(wrapper.getGlucoseThresholdMax()));
        user.setMinGlucose(Integer.valueOf(wrapper.getGlucoseThresholdMin()));
        user.setCarbRatio(Integer.valueOf(wrapper.getCarbRatio()));
        user.setInsulinReaction(Integer.valueOf(wrapper.getInsulinSensitivity()));
        user.setGlucagonReaction(Integer.valueOf(wrapper.getGlucagonReactionTime()));
    }
}
