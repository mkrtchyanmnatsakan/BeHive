package com.bluip.test.behive.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.bluip.test.behive.ui.activitys.HomeActivity;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditTaskFragment extends Fragment implements View.OnClickListener {


    RelativeLayout backEditTaskRelative;
    RelativeLayout dateRelative;
    Button saveChangeButton;
    TextView dateTextView;
    private TaskModel taskModel;

    public static EditTaskFragment newInstance(TaskModel taskModels) {

        EditTaskFragment editTaskFragment = new EditTaskFragment();
        editTaskFragment.taskModel = taskModels;
        Bundle bundle = new Bundle();
        editTaskFragment.setArguments(bundle);

        return editTaskFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_task_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {

        backEditTaskRelative = view.findViewById(R.id.back_edit_task_relative);
        backEditTaskRelative.setOnClickListener(this);

        dateRelative = view.findViewById(R.id.date_relative);
        dateRelative.setOnClickListener(this);
        dateTextView = view.findViewById(R.id.date_text_view);

        saveChangeButton = view.findViewById(R.id.save_change_button);
        saveChangeButton.setOnClickListener(this);


    }


    private void startDatePicker() {

        new SingleDateAndTimePickerDialog.Builder(getContext())
                .bottomSheet()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd h:mm a",
                                getResources().getConfiguration().locale);

                        SimpleDateFormat sdfPM = new SimpleDateFormat("a",
                                getResources().getConfiguration().locale);

                        SimpleDateFormat sdfDay = new SimpleDateFormat("MMM dd", getResources().getConfiguration().locale);

                        SimpleDateFormat sdfTime =  new SimpleDateFormat("h:mm", getResources().getConfiguration().locale);

                        String dateString = sdf.format(date);
                        dateTextView.setText(dateString);
                        taskModel.setDueDate(new DueDate(sdfTime.format(date),sdfPM.format(date),sdfDay.format(date)));

                    }
                })
                .display();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.save_change_button:

                saveChangeTask();

                break;

            case R.id.date_relative:

                startDatePicker();

                break;

            case R.id.back_edit_task_relative:

                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }


                break;
        }

    }

    private void saveChangeTask() {


            if(getActivity() != null){
                ((HomeActivity) getActivity()).getDBHelper().updateTask(taskModel);
                getActivity().onBackPressed();
            }






    }
}
