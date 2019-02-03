package com.bluip.test.behive.ui.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bluip.test.behive.R;
import com.bluip.test.behive.db.DBHelper;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.adapters.TaskAdapter;
import com.bluip.test.behive.helpers.listeners.TaskClickedListener;
import com.bluip.test.behive.models.Assignee;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.bluip.test.behive.ui.activitys.BaseActivity;
import com.bluip.test.behive.ui.activitys.HomeActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment implements TaskClickedListener, TextView.OnEditorActionListener, DBHelper.DBUpdateListener {


    RecyclerView taskRecycler;
    EditText searchEditText;
    TaskAdapter taskAdapte;
    private List<TaskModel> mTaskModelList;

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
        ((BaseActivity) getActivity()).getDBHelper().attachDBUpdateListener(this);
        initViews(view);


    }

    @SuppressLint("StaticFieldLeak")
    private void initViews(View view) {


        searchEditText = view.findViewById(R.id.search_edit_text);
        searchEditText.setOnEditorActionListener(this);
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);


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
                    taskAdapte = new TaskAdapter(getActivity(), mTaskModelList, TaskFragment.this);
                    taskRecycler.setAdapter(taskAdapte);
                }
            }.execute();

        }

    }

    @Override
    public void onDestroy() {
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
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {

            if (taskAdapte != null) {

                DueDate dueDate1 = new DueDate("15:30", "PM", "May 1");

                List<Assignee> assignees = new ArrayList<>(6);

                assignees.add(new Assignee(R.drawable.user_one));
                assignees.add(new Assignee(R.drawable.user_two));
                assignees.add(new Assignee(R.drawable.user_three));
                assignees.add(new Assignee(R.drawable.user_four));
                assignees.add(new Assignee(R.drawable.user_five));
                assignees.add(new Assignee(R.drawable.user_six));

                TaskModel taskModel1 = new TaskModel(null, searchEditText.getText().toString().trim()
                        , ConstantValues.MEDIUM, assignees, dueDate1, ConstantValues.MEDIUM, false);
                ((BaseActivity) getActivity()).getDBHelper().saveTask(taskModel1);

                taskAdapte.addNewTaskItem(taskModel1);
                taskRecycler.scrollToPosition(0);
                searchEditText.setText("");
            }


        }


        return false;
    }

    @Override
    public void onTaskUpdate(final TaskModel updatedTask) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                taskAdapte.notifyDataSetChanged();
                Toast.makeText(getActivity(), new Gson().toJson(updatedTask), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
