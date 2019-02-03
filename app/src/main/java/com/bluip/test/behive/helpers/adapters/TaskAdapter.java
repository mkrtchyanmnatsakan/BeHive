package com.bluip.test.behive.helpers.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluip.test.behive.R;
import com.bluip.test.behive.helpers.ConstantValues;
import com.bluip.test.behive.helpers.listeners.TaskClickedListener;
import com.bluip.test.behive.models.TaskModel;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_LOW = 0;
    private static final int TYPE_MEDIUM = 1;
    private static final int TYPE_HIGH = 2;
    private static final int TYPE_COMPLETED = 3;


    private Activity homeActivity;
    private LayoutInflater inflater;
    private List<TaskModel> taskModels;
    private TaskClickedListener taskClickedListener;


    public TaskAdapter(Activity homeActivity, List<TaskModel> taskModels, TaskClickedListener taskClickedListener) {
        this.homeActivity = homeActivity;
        this.taskClickedListener = taskClickedListener;
        this.inflater = LayoutInflater.from(homeActivity);
        this.taskModels = taskModels;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i) {

            case TYPE_LOW:

                View layoutViewLow = inflater.inflate(R.layout.low_item, viewGroup, false);
                return new TaskAdapter.LowViewHolder(layoutViewLow);


            case TYPE_MEDIUM:

                View layoutViewMedium = inflater.inflate(R.layout.medium_item, viewGroup, false);
                return new TaskAdapter.MediumViewHolder(layoutViewMedium);


            case TYPE_HIGH:

                View layoutViewHigh = inflater.inflate(R.layout.high_item, viewGroup, false);
                return new TaskAdapter.HighViewHolder(layoutViewHigh);


            case TYPE_COMPLETED:

                View layoutViewCompleted = inflater.inflate(R.layout.completed_item, viewGroup, false);
                return new TaskAdapter.CompletedVideoViewHolder(layoutViewCompleted);


            default:

                View layoutViewDefault = inflater.inflate(R.layout.low_item, viewGroup, false);
                return new TaskAdapter.LowViewHolder(layoutViewDefault);


        }

    }


    public void addNewTaskItem(TaskModel taskModel) {


        taskModels.add(0, taskModel);

        notifyDataSetChanged();


    }


    @Override
    public int getItemViewType(int position) {

        if (taskModels.get(position).isCompleted()) {

            return TYPE_COMPLETED;
        } else if (taskModels.get(position).getPriority().equals(ConstantValues.LOW)) {

            return TYPE_LOW;
        } else if (taskModels.get(position).getPriority().equals(ConstantValues.MEDIUM)) {

            return TYPE_MEDIUM;
        } else {
            return TYPE_HIGH;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


        switch (viewHolder.getItemViewType()) {

            case TYPE_LOW:

                LowViewHolder lowViewHolder = (LowViewHolder) viewHolder;

                bindLow(lowViewHolder, i);


                break;

            case TYPE_MEDIUM:

                MediumViewHolder mediumViewHolder = (MediumViewHolder) viewHolder;
                bindMedium(mediumViewHolder, i);

                break;

            case TYPE_HIGH:

                HighViewHolder highViewHolder = (HighViewHolder) viewHolder;
                bindHigh(highViewHolder, i);

                break;

            case TYPE_COMPLETED:

                CompletedVideoViewHolder completedVideoViewHolder = (CompletedVideoViewHolder) viewHolder;
                bindCompleted(completedVideoViewHolder, i);

                break;
        }

    }

    private void bindCompleted(CompletedVideoViewHolder completedVideoViewHolder, int position) {

    }

    private void bindHigh(HighViewHolder highViewHolder, int position) {

    }

    private void bindMedium(MediumViewHolder mediumViewHolder, int position) {

        mediumViewHolder.descriptionText.setText(taskModels.get(position).getDescription());
        mediumViewHolder.timeText.setText(taskModels.get(position).getDueDate().getTime());
    }

    private void bindLow(LowViewHolder lowViewHolder, int position) {

    }


    @Override
    public int getItemCount() {

        if (taskModels == null || taskModels.isEmpty()) {
            return 0;
        } else {

            return taskModels.size();
        }

    }


    //---------------------------- ViewHolders---------------------->

    private class LowViewHolder extends RecyclerView.ViewHolder {

        private LowViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskClickedListener.taskClicked(taskModels.get(getAdapterPosition()));
                }
            });
        }
    }

    private class MediumViewHolder extends RecyclerView.ViewHolder {
        private TextView descriptionText;
        private TextView timeText;

        private MediumViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskClickedListener.taskClicked(taskModels.get(getAdapterPosition()));
                }
            });

            descriptionText = (TextView) itemView.findViewById(R.id.description_text);
            timeText        = itemView.findViewById(R.id.time_text);

        }
    }


    private class HighViewHolder extends RecyclerView.ViewHolder {


        private HighViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskClickedListener.taskClicked(taskModels.get(getAdapterPosition()));
                }
            });
        }
    }


    private class CompletedVideoViewHolder extends RecyclerView.ViewHolder {


        private CompletedVideoViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskClickedListener.taskClicked(taskModels.get(getAdapterPosition()));
                }
            });

        }
    }


}
