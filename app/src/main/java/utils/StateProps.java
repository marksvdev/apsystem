package utils;

/**
 * Created by marksv on 1/3/2015.
 */
public enum StateProps {
    // device
    CGMBLEAddress("CGMBLEAddress"),
    CGMSerialNumber("CGMSerialNumber"),
    InsulinPumpSerialNumber("InsulinPumpSerialNumber"),
    GlucagonPumpSerialNumber("GlucagonPumpSerialNumber"),

    // ap
    Glucose("Glucose"),
    GlucoseTime("GlucoseTime"),

    // dose
    Insulin("Insulin"),
    Glucagon("Glucagon"),

    // user
    InsulinSensitivity("InsulinSensitivity"),
    GlucagonSensitivity("GlucagonSensitivity"),

    ;

    private final String text;

    /**
     * @param text
     */
    private StateProps(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
