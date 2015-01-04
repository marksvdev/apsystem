package utils;

/**
 * Created by marksv on 1/4/2015.
 */
public enum MSGCode {
    EXTRA_DATA("100"),
    UPDATE_EGV("200"),

    ;

    private final String msg;

    MSGCode(String msg) { this.msg = msg; }
    public String toString() { return msg; }
}
