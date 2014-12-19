package com.dtu.s113604.apsystem.ap_system.control_algorithm;

import com.dtu.s113604.apsystem.models.APStateModel;

/**
 * Created by marksv on 06/12/14.
 */
public class ControlAlgorithm implements IControlAlgorithm {

    @Override
    public int calculateInsulinDose(APStateModel apState) {
        return 0;
    }

    @Override
    public int calculateGlucagonDose(APStateModel apState) {
        return 0;
    }
}
