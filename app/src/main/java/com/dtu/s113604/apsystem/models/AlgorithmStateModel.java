package com.dtu.s113604.apsystem.models;

/**
 * Created by marksv on 06/12/14.
 */
public class AlgorithmStateModel {
    private long id;
    private String datetime;
    private String stateProperties;

    public AlgorithmStateModel(long id, String datetime, String stateProperties) {
        this.id = id;
        this.datetime = datetime;
        this.stateProperties = stateProperties;
    }

    public AlgorithmStateModel() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStateProperties() {
        return stateProperties;
    }

    public void setStateProperties(String stateProperties) {
        this.stateProperties = stateProperties;
    }

    @Override
    public String toString() {
        return "AlgorithmStateModel{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", stateProperties='" + stateProperties + '\'' +
                '}';
    }
}