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
import com.todo.data.model.TaskModel;
import com.todo.util.UiUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskItemViewHolder> {


    private static final long CLICK_THROTTLE_WINDOW_MILLIS = 300L;
    private final Subject<TaskModel> onItemClickSubject = PublishSubject.create();
    /********* Member Fields  ********/

    private final List<TaskModel> taskModels;

    /********* Constructors ********/

    TasksAdapter(List<TaskModel> taskModels) {
        this.taskModels = taskModels;
    }

    /********* RecyclerView.Adapter Inherited Methods ********/

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(itemView, onItemClickSubject);
    }

    @Override
    public void onBindViewHolder(TaskItemViewHolder holder, int position) {
        holder.setItem(taskModels.get(position));
    }

    @Override
    public int getItemCount() {
        return taskModels.size();
    }

    /********* Member Methods  ********/

    TaskModel getItem(int position) {
        return taskModels.get(position);
    }

    void updateTasks(List<TaskModel> taskModels) {
        this.taskModels.clear();
        this.taskModels.addAll(taskModels);
        notifyDataSetChanged();
    }

    public TaskModel deleteTask(TaskModel taskModel) {
        return removeTask(taskModels.indexOf(taskModel));
    }

    public TaskModel removeTask(int position) {
        TaskModel taskModel = taskModels.get(position);
        taskModels.remove(position);
        notifyItemRemoved(position);
        return taskModel;
    }

    public void restoreTask(int position, TaskModel taskModel) {
        taskModels.add(position, taskModel);
        notifyItemInserted(position);
    }

    public Observable<TaskModel> onItemClick() {
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
        private final Subject<TaskModel> clickSubject;
        private TaskModel taskModel;

        TaskItemViewHolder(View itemView, Subject<TaskModel> clickSubject) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.clickSubject = clickSubject;
        }

        void setItem(final TaskModel taskModel) {
            this.taskModel = taskModel;
            textViewTitle.setText(taskModel.getTitle());
            textViewDeadline.setText(DateUtils.getRelativeTimeSpanString(taskModel.getDeadline(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
            viewPriority.setBackgroundResource(UiUtils.getPriorityColorRes(taskModel.getPriority()));

        }


        @OnClick(R.id.task_layout)
        void onTaskClick() {
            clickSubject.onNext(taskModel);
        }


    }
}
