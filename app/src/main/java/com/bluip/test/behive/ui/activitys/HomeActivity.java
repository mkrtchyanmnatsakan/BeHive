package com.bluip.test.behive.ui.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.models.Assignee;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.bluip.test.behive.ui.fragments.ActivityFragment;
import com.bluip.test.behive.ui.fragments.ContractFragment;
import com.bluip.test.behive.ui.fragments.EditTaskFragment;
import com.bluip.test.behive.ui.fragments.MessagesFragment;
import com.bluip.test.behive.ui.fragments.NavigationFragment;
import com.bluip.test.behive.ui.fragments.TaskFragment;
import com.bluip.test.behive.ui.fragments.WorkspacesFragment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


    private  RelativeLayout root,
            menuIconRelative;
    private  FrameLayout navigationContent;
    private BottomNavigationView bottomNavigation;


    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        changeStatusBarColor();
        setContentView(R.layout.activity_home);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                     TaskFragment.newInstance())
//                    .commit();
//        }


        initViews();
    }


    public void goToEditFragment(TaskModel taskModel) {


        getSupportFragmentManager().beginTransaction().add(R.id.edit_fragment_container
                , EditTaskFragment.newInstance(taskModel), ConstantValues.TAG_EDIT_TASK_FRAGMENT)
                .addToBackStack(null)
                .commit();


    }

    private void initViews() {
        generateTasks();

        root = findViewById(R.id.root);

        navigationContent = findViewById(R.id.navigation_content);

        drawerLayout = findViewById(R.id.drawer_layout);

        menuIconRelative = findViewById(R.id.menu_icon_relative);
        menuIconRelative.setOnClickListener(this);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.tasks_item);

        setBadgeMessages();

        initDrawer();


    }



    public void navigationController(String clickedType){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                controllerDrawerLayout();
            }
        },300);




        switch (clickedType){

            case ConstantValues.ACTIVITY_TYPE:

                bottomNavigation.setSelectedItemId(R.id.activity_item);

                break;

            case ConstantValues.TASKS_TYPE:

                bottomNavigation.setSelectedItemId(R.id.tasks_item);

                break;

            case ConstantValues.MESSAGES_TYPE:

                bottomNavigation.setSelectedItemId(R.id.messages_item);

                break;

            case ConstantValues.WORKSPACES_TYPE:

                bottomNavigation.setSelectedItemId(R.id.workspaces_item);

                break;

            case ConstantValues.CONTACTS_TYPE:

                bottomNavigation.setSelectedItemId(R.id.contacts_item);

                break;

        }

    }




    public void logOut(){

        Utils.settingBooleanValuesInPreference(this,ConstantValues.IS_LOGIN_USER_PREFERENCE_KEY,false);
        finish();



    }


    private void generateTasks() {
        List<TaskModel> taskModelList = new ArrayList<>();

        if (getDBHelper().getTasksCount() == 0) {

            for (int i = 0; i < 6; i++) {

                switch (i) {

                    case 0:

                        DueDate dueDate0 = new DueDate("15:30", "PM", "May 1");

                        TaskModel taskModel0 = new TaskModel(i, getResources().getString(R.string.text_one),
                                ConstantValues.LOW, Collections.EMPTY_LIST, dueDate0, ConstantValues.LOW, false);

                        taskModelList.add(taskModel0);


                        break;

                    case 1:

                        DueDate dueDate1 = new DueDate("15:30", "PM", "May 1");

                        List<Assignee> assignees = new ArrayList<>(6);

                        assignees.add(new Assignee(R.drawable.user_one));
                        assignees.add(new Assignee(R.drawable.user_two));
                        assignees.add(new Assignee(R.drawable.user_three));
                        assignees.add(new Assignee(R.drawable.user_four));
                        assignees.add(new Assignee(R.drawable.user_five));
                        assignees.add(new Assignee(R.drawable.user_six));

                        TaskModel taskModel1 = new TaskModel(i, getResources().getString(R.string.text_two)
                                , ConstantValues.MEDIUM, assignees, dueDate1, ConstantValues.MEDIUM, false);

                        taskModelList.add(taskModel1);


                        break;

                    case 2:
                        DueDate dueDate2 = new DueDate("15:30", "PM", "May 1");

                        TaskModel taskModel2 = new TaskModel(i, getResources().getString(R.string.text_three)
                                , ConstantValues.HIGH, Collections.EMPTY_LIST, dueDate2
                                , ConstantValues.HIGH, false);

                        taskModelList.add(taskModel2);


                        break;

                    case 3:


                        List<Assignee> assignees5 = new ArrayList<>(3);

                        assignees5.add(new Assignee(R.drawable.user_one));
                        assignees5.add(new Assignee(R.drawable.user_two));
                        assignees5.add(new Assignee(R.drawable.user_three));

                        DueDate dueDate5 = new DueDate("10:05", "AM", "Jul 4");


                        TaskModel taskModel5 = new TaskModel(i, getResources().getString(R.string.text_one),
                                ConstantValues.MEDIUM, assignees5, dueDate5, ConstantValues.MEDIUM, false);

                        taskModelList.add(taskModel5);

                        break;

                    case 4:

                        List<Assignee> assignees4 = new ArrayList<>(2);

                        assignees4.add(new Assignee(R.drawable.user_one));
                        assignees4.add(new Assignee(R.drawable.user_two));

                        DueDate dueDate4 = new DueDate("14:56", "PM", "Aug 15");

                        TaskModel taskModel4 = new TaskModel(i, getResources().getString(R.string.text_five)
                                , ConstantValues.LOW, assignees4, dueDate4, ConstantValues.LOW, false);

                        taskModelList.add(taskModel4);


                        break;

                    case 5:


                        List<Assignee> assignees3 = new ArrayList<>(2);

                        assignees3.add(new Assignee(R.drawable.user_one));
                        assignees3.add(new Assignee(R.drawable.user_two));

                        DueDate dueDate3 = new DueDate("17:07", "PM", "Feb 06");

                        TaskModel taskModel3 = new TaskModel(i, getResources().getString(R.string.text_four)
                                , ConstantValues.HIGH, assignees3, dueDate3, ConstantValues.HIGH, true);

                        taskModelList.add(taskModel3);

                        break;
                }

            }



        }

         getDBHelper().saveTasks(taskModelList);

    }


    private void setBadgeMessages() {

        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigation.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);
        TextView tv = badge.findViewById(R.id.count_message_text);
        tv.setText(getResources().getString(R.string.count_message));
        itemView.addView(badge);

    }


    private void initDrawer() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.navigation_content, NavigationFragment.newInstance(), ConstantValues.
                TAG_NAVIGATION_FRAGMENT).commit();
        SmoothActionBarDrawerToggle drawerToggle = new SmoothActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }


    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorStatusBar));

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment selectedFragment = null;


        switch (menuItem.getItemId()) {

            case R.id.activity_item:

                selectedFragment = ActivityFragment.newInstance();

                break;
            case R.id.tasks_item:

                selectedFragment = TaskFragment.newInstance();

                break;

            case R.id.messages_item:

                selectedFragment = MessagesFragment.newInstance();

                break;

            case R.id.workspaces_item:

                selectedFragment = WorkspacesFragment.newInstance();

                break;

            case R.id.contacts_item:

                selectedFragment = ContractFragment.newInstance();

                break;

            default:

                selectedFragment = ActivityFragment.newInstance();

                break;


        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();

        return true;
    }


    private void controllerDrawerLayout(){

        if (drawerLayout.isDrawerOpen(navigationContent)) {

            drawerLayout.closeDrawer(navigationContent);

        } else {

            drawerLayout.openDrawer(navigationContent);

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.menu_icon_relative:

              controllerDrawerLayout();

                break;
        }

    }


    class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {
        private Runnable runnable;

        SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout,
                                    @StringRes int openDrawerContentDescRes,
                                    @StringRes int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }


        @SuppressLint("NewApi")
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            float moveFactor = (navigationContent.getWidth() * slideOffset);
            root.setTranslationX(moveFactor);

        }


        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if (runnable != null && newState == DrawerLayout.STATE_IDLE) {
                runnable.run();
                runnable = null;
            }
        }

        public void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }

    }

}
