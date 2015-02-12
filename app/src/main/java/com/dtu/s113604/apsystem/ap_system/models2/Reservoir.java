package com.dtu.s113604.apsystem.ap_system.models2;

/**
 * Created by marksv on 29/01/15.
 */
public class Reservoir {
    private String measuredTime;
    private int level;
    private Long id;

    public Reservoir(String measuredTime, int level) {
        this.measuredTime = measuredTime;
        this.level = level;
    }

    public Reservoir() {}

    public String getMeasuredTime() {
        return measuredTime;
    }

    public void setMeasuredTime(String measuredTime) {
        this.measuredTime = measuredTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
