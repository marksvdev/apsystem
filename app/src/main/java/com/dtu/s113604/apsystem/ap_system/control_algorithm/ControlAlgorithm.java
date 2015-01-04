package com.dtu.s113604.apsystem.ap_system.control_algorithm;

import com.dtu.s113604.apsystem.models.APStateModel;
import com.dtu.s113604.apsystem.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.models.DoseDataModel;
import com.dtu.s113604.apsystem.models.UserDataModel;

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

        int lastGlucoseVal = Integer.parseInt(XMLManager.getValue(state.getXML(), "Glucose"));
        int insulinSensitivity = Integer.parseInt(XMLManager.getValue(userData.getXML(), "InsulinSensitivity"));

        // Some calculations based on last glucose reading and user data...

        Random rand = new Random();

        int newGlucoseVal = rand.nextInt((10 - lastGlucoseVal) + 1) + lastGlucoseVal;

        return newGlucoseVal++;
    }

    @Override
    public int calculateGlucagonDose(APStateModel state) {
        UserDataModel userData = state.getPatientParameters();
        AlgorithmStateModel algorithmState = state.getAlgorithmState();

        int lastGlucoseVal = Integer.parseInt(XMLManager.getValue(state.getXML(), "Glucose"));
        int glucagonSensitivity = Integer.parseInt(XMLManager.getValue(userData.getXML(), "GlucagonSensitivity"));

        // Some calculations based on last glucose reading and user data...

        Random rand = new Random();

        int newGlucoseVal = rand.nextInt((10 - lastGlucoseVal) + 1) + lastGlucoseVal;

        return newGlucoseVal++;
    }
}
