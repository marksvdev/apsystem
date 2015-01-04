package utils;

import com.dtu.s113604.apsystem.models.APStateModel;

/**
 * Created by marksv on 1/4/2015.
 */
public class StateManager {

    private static StateManager instance = null;
    private APStateModel state;

    protected StateManager() {

    }
    public static StateManager getInstance() {
        if(instance == null) {
            instance = new StateManager();
        }
        return instance;
    }

    public APStateModel getState() {
        return state;
    }

    public void setState(APStateModel state) {
        this.state = state;
    }
}
