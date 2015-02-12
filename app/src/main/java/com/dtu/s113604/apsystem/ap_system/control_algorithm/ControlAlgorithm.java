package com.dtu.s113604.apsystem.ap_system.control_algorithm;

import com.dtu.s113604.apsystem.ap_system.models2.APState;
import com.dtu.s113604.apsystem.ap_system.models2.AlgorithmData;
import com.dtu.s113604.apsystem.ap_system.models2.User;

/**
 * Created by marksv on 06/12/14.
 */
public class ControlAlgorithm implements IControlAlgorithm {

    @Override
    public int calculateInsulinDose(APState state) {
        User user = state.getUser();
        AlgorithmData algorithmState = state.getAlgorithmData();

        int lastGlucoseVal = 2;

        if (user.getGlucoseHistory().size() > 3) {
            lastGlucoseVal = user.getGlucoseHistory().get(user.getGlucoseHistory().size() - 2).getLevel();
        }


        int currentGlucoseVal = user.getlGlucose().getLevel();
        int insulinSensitivity = user.getInsulinSens();

        // Some calculations based on last glucose reading and user data...

        if (lastGlucoseVal == 0) {lastGlucoseVal = 90;}
        int newGlucoseVal = (int) (currentGlucoseVal / lastGlucoseVal) * insulinSensitivity;

        return newGlucoseVal++;
    }

    @Override
    public int calculateGlucagonDose(APState state) {
        User user = state.getUser();
        AlgorithmData algorithmState = state.getAlgorithmData();

        int lastGlucoseVal = 2;

        if (user.getGlucoseHistory().size() > 3) {
            lastGlucoseVal = user.getGlucoseHistory().get(user.getGlucoseHistory().size() - 2).getLevel();
        }


        int currentGlucoseVal = user.getlGlucose().getLevel();
        int glucagonSensitivity = user.getGlucagonSens();

        // Some calculations based on last glucose reading and user data...

        if (lastGlucoseVal == 0) {lastGlucoseVal = 90;}
        int newGlucoseVal = (int) (lastGlucoseVal / lastGlucoseVal) * glucagonSensitivity;

        return newGlucoseVal++;
    }
}
