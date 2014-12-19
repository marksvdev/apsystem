package com.dtu.s113604.apsystem.models;

/**
 * Created by marksv on 06/12/14.
 */
/*

Patient Parameters {Carb Ratio, Insulin Sensitivity Factor, Insulin Reaction Time, Glucagon Sensitivity, Glucagon Reaction Time}

*/

public class UserDataModel {

    private long id;
    private String datetime;

    private String stateProperties;

    public UserDataModel(long id, String datetime, String stateProperties) {
        this.id = id;
        this.datetime = datetime;
        this.stateProperties = stateProperties;
    }

    public UserDataModel() {}

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
        return "UserDataModel{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", stateProperties='" + stateProperties + '\'' +
                '}';
    }
}
