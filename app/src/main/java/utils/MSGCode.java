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
    ALARM_GLUCOSE_HIGH("400"),
    ALARM_GLUCOSE_LOW("401"),
    ALARM_BATTERY_CGM("402"),
    ALARM_BATTERY_GLUCAGON("403"),
    ALARM_BATTERY_INSULIN("404"),

    ALARM_GLUCOSE_NORMAL("420"), ALARM_BATTERY_CGM_OK("440"), ALARM_BATTERY_GLUCAGON_OK("460"), ALARM_BATTERY_INSULIN_OK("480");

    private final String msg;

    MSGCode(String msg) { this.msg = msg; }
    public String toString() { return msg; }
}
