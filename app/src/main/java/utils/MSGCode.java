package utils;

/**
 * Created by marksv on 1/4/2015.
 */
public enum MSGCode {
    EXTRA_DATA("100"),
    UPDATE_EGV("200"),
    UPDATE_BATTERY_CGM("300"),
    UPDATE_BATTERY_PUMP_INSULIN("320"),
    UPDATE_BATTERY_PUMP_GLUCAGON("340"),

    ;

    private final String msg;

    MSGCode(String msg) { this.msg = msg; }
    public String toString() { return msg; }
}
