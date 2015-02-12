package com.dtu.s113604.apsystem.ap_system.models2;

import java.util.List;

/**
 * Created by marksv on 29/01/15.
 */
public class User {
    private long id;

    private int carbRatio;
    private int insulinSens;
    private int insulinReaction;
    private int glucagonSens;
    private int glucagonReaction;
    private int minGlucose;
    private int maxGlucose;
    private List<Glucose> glucoseHistory;

    private Glucose lGlucose;

    private String created;
    private String modified;

    public User(int carbRatio, int insulinSens, int insulinReaction, int glucagonSens, int glucagonReaction, int minGlucose, int maxGlucose, List<Glucose> glucoseHistory) {
        this.carbRatio = carbRatio;
        this.insulinSens = insulinSens;
        this.insulinReaction = insulinReaction;
        this.glucagonSens = glucagonSens;
        this.glucagonReaction = glucagonReaction;
        this.minGlucose = minGlucose;
        this.maxGlucose = maxGlucose;
        this.glucoseHistory = glucoseHistory;
    }

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCarbRatio() {
        return carbRatio;
    }

    public void setCarbRatio(int carbRatio) {
        this.carbRatio = carbRatio;
    }

    public int getInsulinSens() {
        return insulinSens;
    }

    public void setInsulinSens(int insulinSens) {
        this.insulinSens = insulinSens;
    }

    public int getInsulinReaction() {
        return insulinReaction;
    }

    public void setInsulinReaction(int insulinReaction) {
        this.insulinReaction = insulinReaction;
    }

    public int getGlucagonSens() {
        return glucagonSens;
    }

    public void setGlucagonSens(int glucagonSens) {
        this.glucagonSens = glucagonSens;
    }

    public int getGlucagonReaction() {
        return glucagonReaction;
    }

    public void setGlucagonReaction(int glucagonReaction) {
        this.glucagonReaction = glucagonReaction;
    }

    public int getMinGlucose() {
        return minGlucose;
    }

    public void setMinGlucose(int minGlucose) {
        this.minGlucose = minGlucose;
    }

    public int getMaxGlucose() {
        return maxGlucose;
    }

    public void setMaxGlucose(int maxGlucose) {
        this.maxGlucose = maxGlucose;
    }

    public List<Glucose> getGlucoseHistory() {
        return glucoseHistory;
    }

    public void setGlucoseHistory(List<Glucose> glucoseHistory) {
        this.glucoseHistory = glucoseHistory;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Glucose getlGlucose() {
        return lGlucose;
    }

    public void addGlucose(Glucose glucose) {
        if (glucose != null) {
            glucoseHistory.add(glucose);
            lGlucose = glucose;
        }
    }
}
