package com.dtu.s113604.apsystem.ap_system.control_algorithm;

import com.dtu.s113604.apsystem.ap_system.models2.APState;

/**
 * Created by marksv on 06/12/14.
 */
public interface IControlAlgorithm {
    int calculateInsulinDose(APState apState);
    int calculateGlucagonDose(APState apState);
}
