package com.bluip.test.behive.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluip.test.behive.R;
import com.bluip.test.behive.db.DBHelper;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.Utils;
import com.bluip.test.behive.helpers.adapters.TaskAdapter;
import com.bluip.test.behive.helpers.listeners.TaskClickedListener;
import com.bluip.test.behive.models.Assignee;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.bluip.test.behive.ui.activitys.BaseActivity;
import com.bluip.test.behive.ui.activitys.HomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskFragment extends Fragment implements TaskClickedListener, TextView.OnEditorActionListener,
        DBHelper.DBUpdateListener ,View.OnTouchListener{


    private RecyclerView taskRecycler;
    private  EditText quickAddEditText;
    private TaskAdapter taskAdapte;
    private ImageView notTaskImage;
    private TextView notTaskText;

    private List<TaskModel> mTaskModelList;
    private Intent mSpeechRecognizerIntent;
    private SpeechRecognizer mSpeechRecognizer;
    private boolean ischeckedPermission;

    public static TaskFragment newInstance() {

        TaskFragment taskFragment = new TaskFragment();
        Bundle bundle = new Bundle();
        taskFragment.setArguments(bundle);
        return taskFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.task_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity()!= null){

            ((BaseActivity) getActivity()).getDBHelper().attachDBUpdateListener(this);

        }

        initViews(view);


    }

    @SuppressLint("StaticFieldLeak")
    private void initViews(View view) {


        quickAddEditText = view.findViewById(R.id.quick_add_edit_text);
        quickAddEditText.setOnEditorActionListener(this);
        quickAddEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        ImageView microphoneImage = view.findViewById(R.id.microphone_image);
        microphoneImage.setOnTouchListener(this);

        notTaskImage = view.findViewById(R.id.not_task_image);
        notTaskText  = view.findViewById(R.id.not_task_text);


         mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());


         mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    quickAddEditText.setText(matches.get(0));
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        taskRecycler = view.findViewById(R.id.task_recycler);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (getActivity() instanceof HomeActivity) {


            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    mTaskModelList = ((HomeActivity) getActivity()).getDBHelper().getAllTasks();

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    if(mTaskModelList!= null && !mTaskModelList.isEmpty()){

                        notTaskImage.setVisibility(View.GONE);
                        notTaskText.setVisibility(View.GONE);
                        taskAdapte = new TaskAdapter(getActivity(), mTaskModelList, TaskFragment.this);
                        taskRecycler.setAdapter(taskAdapte);
                    }else {

                        notTaskImage.setVisibility(View.VISIBLE);
                        notTaskText.setVisibility(View.VISIBLE);
                    }


                }
            }.execute();

        }

    }

    @Override
    public void onDestroy() {
        if (getActivity() != null)
        ((BaseActivity)getActivity()).getDBHelper().detachDBUpdateListener();

        super.onDestroy();
    }

    @Override
    public void taskClicked(TaskModel taskModel) {


        if (getActivity() instanceof HomeActivity) {

            ((HomeActivity) getActivity()).goToEditFragment(taskModel);


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {



        switch (requestCode) {
            case ConstantValues.MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                ischeckedPermission = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                return;
            }


        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);





    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {

            if (taskAdapte != null) {

                Date currentTime = Calendar.getInstance().getTime();

                String time   = Utils.dateFormat(getActivity(),"hh:mm",currentTime);
                String pmOrAm = Utils.dateFormat(getActivity(),"a",currentTime);
                String day    = Utils.dateFormat(getActivity(),"MMM dd",currentTime);

                DueDate dueDateCurrent = new DueDate(time, pmOrAm, day);

                List<Assignee> assignees = new ArrayList<>();

                assignees.add(new Assignee(R.drawable.user_one));
                assignees.add(new Assignee(R.drawable.user_two));
                assignees.add(new Assignee(R.drawable.user_three));
                assignees.add(new Assignee(R.drawable.user_four));
                assignees.add(new Assignee(R.drawable.user_five));
                assignees.add(new Assignee(R.drawable.user_six));

                TaskModel taskModel1 = new TaskModel(null, quickAddEditText.getText().toString().trim()
                        , ConstantValues.MEDIUM, assignees, dueDateCurrent, ConstantValues.MEDIUM, false);
                ((BaseActivity) getActivity()).getDBHelper().saveTask(taskModel1);

                taskAdapte.addNewTaskItem(taskModel1);
                taskRecycler.scrollToPosition(0);
                quickAddEditText.setHint(getResources().getString(R.string.quick_add));
                quickAddEditText.setText("");
            }


        }


        return false;
    }


    private void checkPermission(){

        if(getActivity() != null){

            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(getActivity(), "try again to request the permission", Toast.LENGTH_SHORT).show();
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            ConstantValues.MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                    // MY_PERMISSIONS_REQUEST_READ_AUDIO is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {

                ischeckedPermission = true;

            }

        }





    }

    @Override
    public void onTaskUpdate(final TaskModel updatedTask) {


        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                taskAdapte.notifyDataSetChanged();

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        checkPermission();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //when the user removed the finger

                if(ischeckedPermission){
                    mSpeechRecognizer.stopListening();
                }


                break;

            case MotionEvent.ACTION_DOWN:


                if(ischeckedPermission){

                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

                    //finger is on the button
                    quickAddEditText.setText("");
                    quickAddEditText.setHint("Listening...");
                }


                break;
        }


        return false;
    }
}
