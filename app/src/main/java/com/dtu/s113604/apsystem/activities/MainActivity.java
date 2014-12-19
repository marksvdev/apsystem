package com.dtu.s113604.apsystem.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dtu.s113604.apsystem.R;
import com.dtu.s113604.apsystem.data_store.localstorage_module.APStateDataSource;
import com.dtu.s113604.apsystem.data_store.localstorage_module.IAPStateDataSource;
import com.dtu.s113604.apsystem.models.APStateModel;
import com.dtu.s113604.apsystem.models.AlgorithmStateModel;
import com.dtu.s113604.apsystem.models.DeviceDataModel;
import com.dtu.s113604.apsystem.models.DoseDataModel;
import com.dtu.s113604.apsystem.models.UserDataModel;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DeviceDataModel deviceData = new DeviceDataModel("123", "123", "AB:CD:EF:GH");
        UserDataModel userData = new UserDataModel(1, 2, 3, 4, 5);
        AlgorithmStateModel algoState = new AlgorithmStateModel();
        DoseDataModel doseData = new DoseDataModel();
        APStateModel state = new APStateModel(deviceData, userData, algoState, doseData);

        IAPStateDataSource dataSource = new APStateDataSource(this);
        dataSource.open();
        dataSource.save(state);
        dataSource.close();
    }


}
