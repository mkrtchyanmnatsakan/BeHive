package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluip.test.behive.R;

public class ContractFragment extends Fragment {

    public static ContractFragment newInstance(){

        ContractFragment contractFragment = new ContractFragment();
        Bundle bundle = new Bundle();
        contractFragment.setArguments(bundle);
        return contractFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contract_fragment,container,false);
    }
}
