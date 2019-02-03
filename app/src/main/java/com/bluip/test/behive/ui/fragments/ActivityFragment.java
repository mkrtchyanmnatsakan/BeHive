package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluip.test.behive.R;

public class ActivityFragment extends Fragment {

    public static ActivityFragment newInstance(){

        ActivityFragment activityFragment = new ActivityFragment();
        Bundle bundle = new Bundle();
        activityFragment.setArguments(bundle);
        return activityFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment,container,false);
    }
}
