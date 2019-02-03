package com.bluip.test.behive.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.bluip.test.behive.db.DBHelper;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class BaseActivity extends AppCompatActivity {

    DBHelper mDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Fabric.with(this,new Crashlytics());

        super.onCreate(savedInstanceState);
    }


    public synchronized DBHelper getDBHelper() {
        if (mDBHelper == null) mDBHelper = new DBHelper(this);
        return mDBHelper;
    }
}
