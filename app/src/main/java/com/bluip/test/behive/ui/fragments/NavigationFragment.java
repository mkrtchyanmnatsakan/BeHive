package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.ui.activitys.HomeActivity;

public class NavigationFragment extends Fragment implements View.OnClickListener {


    public static NavigationFragment newInstance(){

        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        navigationFragment.setArguments(bundle);
        return navigationFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.navigation_fragment,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    private void initViews(View view) {

        TextView userNameText = view.findViewById(R.id.user_name_text);
        userNameText.setText(ConstantValues.USER_NAME);

        LinearLayout logOutLinear = view.findViewById(R.id.log_out_linear);
        logOutLinear.setOnClickListener(this);

        LinearLayout activityLinear = view.findViewById(R.id.activity_linear);
        activityLinear.setOnClickListener(this);

        LinearLayout taskLinear = view.findViewById(R.id.task_linear);
        taskLinear.setOnClickListener(this);

        LinearLayout messagesLinear = view.findViewById(R.id.messages_linear);
        messagesLinear.setOnClickListener(this);

        LinearLayout workspacesLinear = view.findViewById(R.id.workspaces_linear);
        workspacesLinear.setOnClickListener(this);

        LinearLayout contractLinear = view.findViewById(R.id.contract_linear);
        contractLinear.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(getActivity() != null){

            switch (v.getId()){

                case R.id.activity_linear:

                        ((HomeActivity)getActivity()).navigationController(ConstantValues.ACTIVITY_TYPE);

                    break;

                case R.id.task_linear:

                        ((HomeActivity)getActivity()).navigationController(ConstantValues.TASKS_TYPE);

                    break;

                case R.id.messages_linear:

                        ((HomeActivity)getActivity()).navigationController(ConstantValues.MESSAGES_TYPE);

                    break;

                case R.id.workspaces_linear:

                        ((HomeActivity)getActivity()).navigationController(ConstantValues.WORKSPACES_TYPE);

                    break;

                case R.id.contract_linear:

                        ((HomeActivity)getActivity()).navigationController(ConstantValues.CONTACTS_TYPE);

                    break;

                case R.id.log_out_linear:

                        ((HomeActivity)getActivity()).logOut();

                    break;


            }
        }


    }
}
