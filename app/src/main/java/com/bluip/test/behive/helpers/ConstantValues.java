package com.bluip.test.behive.helpers;

import com.bluip.test.behive.helpers.adapters.TaskAdapter;
import com.bluip.test.behive.ui.fragments.EditTaskFragment;
import com.bluip.test.behive.ui.fragments.LoginPasswordFragment;
import com.bluip.test.behive.ui.fragments.LoginUserNameFragment;
import com.bluip.test.behive.ui.fragments.NavigationFragment;

public interface ConstantValues {
    String TAG_LOGIN_USER_NAME_FRAGMENT = LoginUserNameFragment.class.getSimpleName();
    String TAG_LOGIN_PASSWORD_FRAGMENT = LoginPasswordFragment.class.getSimpleName();
    String TAG_EDIT_TASK_FRAGMENT = EditTaskFragment.class.getSimpleName();
    String TAG_TASK_ADAPTER = TaskAdapter.class.getSimpleName();
    String TAG_NAVIGATION_FRAGMENT = NavigationFragment.class.getSimpleName();
    String USER_NAME = "Android";
    String PASSWORD = "123456";
    String PASSWORD_PREFERENCE_KEY = "PASSWORD_PREFERENCE_KEY";
    String IS_LOGIN_USER_PREFERENCE_KEY = "IS_LOGIN_USER_PREFERENCE_KEY";
    String LOW = "LOW";
    String MEDIUM = "MEDIUM";
    String HIGH = "HIGH";

    String ACTIVITY_TYPE   = "ACTIVITY_TYPE";
    String TASKS_TYPE      = "TASKS_TYPE";
    String MESSAGES_TYPE   = "MESSAGES_TYPE";
    String WORKSPACES_TYPE = "WORKSPACES_TYPE";
    String CONTACTS_TYPE   = "CONTACTS_TYPE";


    int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2888;
}
