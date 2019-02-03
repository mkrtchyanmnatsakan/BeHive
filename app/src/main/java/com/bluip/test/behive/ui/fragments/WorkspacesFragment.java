package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluip.test.behive.R;

public class WorkspacesFragment extends Fragment {

    public static WorkspacesFragment newInstance(){

        WorkspacesFragment workspacesFragment = new WorkspacesFragment();
        Bundle bundle = new Bundle();
        workspacesFragment.setArguments(bundle);
        return workspacesFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.workspaces_fragment,container,false);
    }
}
