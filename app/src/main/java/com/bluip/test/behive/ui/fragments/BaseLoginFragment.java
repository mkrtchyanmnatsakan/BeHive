package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class BaseLoginFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }


    abstract boolean validateDate (String validateDate);

    abstract void setMarginRelative(int diff);

    abstract void setErrorTextInput();



}
