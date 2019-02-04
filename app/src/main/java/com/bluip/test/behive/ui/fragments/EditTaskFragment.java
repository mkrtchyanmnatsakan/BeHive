package com.bluip.test.behive.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.helpers.adapters.ImagePickerAdapter;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.bluip.test.behive.ui.activitys.HomeActivity;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class EditTaskFragment extends Fragment implements View.OnClickListener {


    private  RelativeLayout backEditTaskRelative;
    private RelativeLayout dateRelative;
    private RelativeLayout addPhotoRelative;
    private Button saveChangeButton;
    private TextView dateTextView;
    private TextView priorityText;
    private RecyclerView photoRecycler;
    private EditText descriptionEdit;
    private ImageView clearDescriptionImage;


    private TaskModel taskModel;

    private ArrayList<Image> images;
    private ImagePickerAdapter imagePickerAdapter;

    public static EditTaskFragment newInstance(TaskModel taskModels) {

        EditTaskFragment editTaskFragment = new EditTaskFragment();

        editTaskFragment.images = new ArrayList<>();
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

        String dateTask = taskModel.getDueDate().getDay() + " " +
                taskModel.getDueDate().getTime() + " " +
                taskModel.getDueDate().getPm();

        dateTextView.setText(dateTask);

        saveChangeButton = view.findViewById(R.id.save_change_button);
        saveChangeButton.setOnClickListener(this);

        addPhotoRelative = view.findViewById(R.id.add_photo_relative);
        addPhotoRelative.setOnClickListener(this);

        photoRecycler = view.findViewById(R.id.photo_recycler);

        descriptionEdit = view.findViewById(R.id.description_edit);
        descriptionEdit.setImeOptions(EditorInfo.IME_ACTION_DONE);
        descriptionEdit.setRawInputType(InputType.TYPE_CLASS_TEXT);

        if(!taskModel.getDescription().isEmpty()){

            descriptionEdit.setText(taskModel.getDescription());

        }


        priorityText = view.findViewById(R.id.priority_text);

        if(!taskModel.getPriority().isEmpty()){

            priorityText.setText(taskModel.getPriority());
        }


        clearDescriptionImage = view.findViewById(R.id.clear_description_image);
        clearDescriptionImage.setOnClickListener(this);


        photoRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        //Allow ScrollView to intercept touch events once again.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle RecyclerView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });



    }


    private void startDatePicker() {

        new SingleDateAndTimePickerDialog.Builder(getContext())
                .bottomSheet()
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {

                        dateTextView.setText(Utils.dateFormat(getActivity(),"MMM dd hh:mm a",date));

                        String time = Utils.dateFormat(getActivity(),"hh:mm",date);
                        String pmOrAm = Utils.dateFormat(getActivity(),"a",date);
                        String day = Utils.dateFormat(getActivity(),"MMM dd",date);

                        taskModel.setDueDate(new DueDate(time,pmOrAm,day));

                    }
                })
                .display();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.clear_description_image:

                descriptionEdit.setText("");

                break;


            case R.id.add_photo_relative:

                startImagePicker();

                break;

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null){

            images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);

            if(imagePickerAdapter == null){
                imagePickerAdapter = new ImagePickerAdapter(getActivity(),images);

                photoRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,true));
                photoRecycler.setAdapter(imagePickerAdapter);
                photoRecycler.setHasFixedSize(true);

            }else {

                imagePickerAdapter.setNewImageList(images);

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startImagePicker() {

        ImagePicker.with(this)
                .setToolbarColor("#80a1fd")
                .setFolderMode(true)
                .setFolderTitle("Folder")
                .setImageTitle("Tap to select")
                .setMaxSize(5)
                .setShowCamera(true)
                .start();


    }




    private void saveChangeTask() {

            if(getActivity() != null){
                if(!descriptionEdit.getText().toString().trim().isEmpty()){

                    taskModel.setDescription(descriptionEdit.getText().toString());

                }
                ((HomeActivity) getActivity()).getDBHelper().updateTask(taskModel);
                getActivity().onBackPressed();
            }

    }
}
