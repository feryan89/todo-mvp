package com.todo.ui.feature.tasks;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.Task;
import com.todo.util.UiUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.Subject;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskItemViewHolder> {


    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;
    private final Subject<Task, Task> onItemClickSubject = BehaviorSubject.create();
    /********* Member Fields  ********/

    private final List<Task> tasks;

    /********* Constructors ********/

    TasksAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    /********* RecyclerView.Adapter Inherited Methods ********/

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(itemView, onItemClickSubject);
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        holder.setItem(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    /********* Member Methods  ********/

    Task getItem(int position) {
        return tasks.get(position);
    }

    void updateTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public Task deleteTask(Task task) {
        return removeTask(tasks.indexOf(task));
    }

    public Task removeTask(int position) {
        Task task = tasks.get(position);
        tasks.remove(position);
        notifyItemRemoved(position);
        return task;
    }

    public void restoreTask(int position, Task task) {
        tasks.add(position, task);
        notifyItemInserted(position);
    }

    public Observable<Task> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }


    /********* Nested Classes  ********/

    class TaskItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_delete_layout)
        FrameLayout taskDeleteLayout;
        @BindView(R.id.task_completed_layout)
        FrameLayout taskCompletedLayout;
        @BindView(R.id.task_layout)
        ConstraintLayout taskLayout;
        @BindView(R.id.task_view_priority)
        View viewPriority;
        @BindView(R.id.task_textview_title)
        TextView textViewTitle;

        @BindView(R.id.task_textview_deadline)
        TextView textViewDeadline;
        private final Subject<Task, Task> clickSubject;
        private Task task;

        TaskItemViewHolder(View itemView, Subject<Task, Task> clickSubject) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.clickSubject = clickSubject;
        }

        void setItem(final Task task) {
            this.task = task;
            textViewTitle.setText(task.getTitle());
            textViewDeadline.setText(DateUtils.getRelativeTimeSpanString(task.getDeadline(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
            viewPriority.setBackgroundResource(UiUtils.getPriorityColorRes(task.getPriority()));

        }


        @OnClick(R.id.task_layout)
        void onTaskClick() {
            clickSubject.onNext(task);
        }


    }
}
