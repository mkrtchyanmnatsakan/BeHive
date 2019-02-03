package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluip.test.behive.R;

public class MessagesFragment extends Fragment {

    public static MessagesFragment newInstance(){

        MessagesFragment messagesFragment = new MessagesFragment();
        Bundle bundle = new Bundle();
        messagesFragment.setArguments(bundle);
        return messagesFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.messages_fragment,container,false);
    }
}
