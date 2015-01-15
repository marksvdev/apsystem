package com.dtu.s113604.apsystem.ap_system.control_algorithm;

import com.dtu.s113604.apsystem.ap_system.models.APStateModel;
import com.dtu.s113604.apsystem.ap_system.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.ap_system.models.DoseDataModel;
import com.dtu.s113604.apsystem.ap_system.models.UserDataModel;

import java.util.Random;

import utils.XMLManager;

/**
 * Created by marksv on 06/12/14.
 */
public class ControlAlgorithm implements IControlAlgorithm {

    @Override
    public int calculateInsulinDose(APStateModel state) {
        UserDataModel userData = state.getPatientParameters();
        AlgorithmStateModel algorithmState = state.getAlgorithmState();

        int lastGlucoseVal = state.getLastGlucose();
        int insulinSensitivity = state.getPatientParameters().getInsulinSensitivity();

        // Some calculations based on last glucose reading and user data...

        if (state.getLastGlucose() == 0) {state.setLastGlucose(90);}
        int newGlucoseVal = (int) (state.getCurrentGlucose() / state.getLastGlucose()) * insulinSensitivity;

        return newGlucoseVal++;
    }

    @Override
    public int calculateGlucagonDose(APStateModel state) {
        UserDataModel userData = state.getPatientParameters();
        AlgorithmStateModel algorithmState = state.getAlgorithmState();

        int lastGlucoseVal = state.getLastGlucose();
        int glucagonSensitivity = state.getPatientParameters().getGlucagonSensitivity();

        // Some calculations based on last glucose reading and user data...

        if (state.getLastGlucose() == 0) {state.setLastGlucose(90);}
        int newGlucoseVal = (int) (state.getCurrentGlucose() / state.getLastGlucose()) * glucagonSensitivity;

        return newGlucoseVal++;
    }
}
