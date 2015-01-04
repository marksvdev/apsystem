package com.dtu.s113604.apsystem.models;

import org.w3c.dom.Document;

import utils.XMLManager;

/**
 * Created by marksv on 06/12/14.
 */
public class AlgorithmStateModel {
    private long id;
    private String datetime;
    private Document stateProperties;

    public AlgorithmStateModel(String datetime, Document stateProperties) {
        this.datetime = datetime;
        this.stateProperties = stateProperties;
    }

    public AlgorithmStateModel(String datetime) {
        this.datetime = datetime;
        stateProperties = XMLManager.generateNewDocument();
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
        return XMLManager.DocumentToString(stateProperties);
    }

    public void setStateProperties(String state) {
        stateProperties = XMLManager.loadXMLFromString(state);
    }

    public Document getXML() {
        return stateProperties;
    }

    public void setXML(Document doc) {
        stateProperties = doc;
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
