package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluip.test.behive.R;

public class NavigationFragment extends Fragment {


    public static NavigationFragment newInstance(){

        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        navigationFragment.setArguments(bundle);
        return navigationFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.navigation_fragment,container,false);

    }

}
