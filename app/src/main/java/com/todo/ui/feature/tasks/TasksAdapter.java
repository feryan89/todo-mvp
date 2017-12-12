package com.todo.ui.feature.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskItemViewHolder> {

    /********* Member Fields  ********/

    private List<Task> tasks;

    /********* Constructors ********/

    TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    /********* RecyclerView.Adapter Inherited Methods ********/

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTitle.setText(task.getTitle());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /********* Member Methods  ********/

    void updateTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    /********* Nested Classes  ********/

    class TaskItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_view_priority)
        View viewPriority;

        @BindView(R.id.task_textview_title)
        TextView textViewTitle;

        @BindView(R.id.task_textview_deadline)
        TextView textViewDeadline;

        TaskItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
