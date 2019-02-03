package com.bluip.test.behive.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bluip.test.behive.db.DBHelper;

public class BaseActivity extends AppCompatActivity {

    DBHelper mDBHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public synchronized DBHelper getDBHelper() {
        if (mDBHelper == null) mDBHelper = new DBHelper(this);
        return mDBHelper;
    }
}
