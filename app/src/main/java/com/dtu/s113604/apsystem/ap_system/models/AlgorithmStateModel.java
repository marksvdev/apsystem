package com.dtu.s113604.apsystem.ap_system.models;

/**
 * Created by marksv on 06/12/14.
 */
public class AlgorithmStateModel {
    private long id;

    private String dummy = "testdata";

    public AlgorithmStateModel() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}
