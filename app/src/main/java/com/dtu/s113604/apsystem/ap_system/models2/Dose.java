package com.dtu.s113604.apsystem.ap_system.models2;

/**
 * Created by marksv on 29/01/15.
 */
public class Dose {
    private String injectTime;
    private int level;
    private Long id;

    public Dose(String injectTime, int level) {
        this.injectTime = injectTime;
        this.level = level;
    }

    public Dose() {}

    public String getInjectTime() {
        return injectTime;
    }

    public void setInjectTime(String injectTime) {
        this.injectTime = injectTime;
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
