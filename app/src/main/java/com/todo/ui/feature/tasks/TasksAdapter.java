package com.todo.ui.feature.tasks;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.util.DateUtils;
import com.todo.util.UiUtils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
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

    private final DateUtils dateUtils;
    private final List<TaskModel> taskModels;

    /********* Constructors ********/

    TasksAdapter(@NonNull DateUtils dateUtils, @NonNull List<TaskModel> taskModels) {
        this.dateUtils = dateUtils;
        this.taskModels = taskModels;
    }

    /********* RecyclerView.Adapter Inherited Methods ********/

    @Override
    public TaskItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskItemViewHolder(itemView, dateUtils, onItemClickSubject);
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

    public void add(int index, TaskModel taskModel) {

        if (index > taskModels.size()) {
            throw new IllegalStateException("Index is no in range");
        } else {
            taskModels.add(index, taskModel);
            notifyItemInserted(index);
        }
    }

    public void addAll(@NonNull Collection<TaskModel> collection) {
        int position = taskModels.size();
        taskModels.addAll(collection);
        notifyItemRangeInserted(position, collection.size());

    }


    public TaskModel remove(@NonNull TaskModel taskModel) {

        int index = taskModels.indexOf(taskModel);
        if (taskModels.remove(taskModel)) {
            notifyItemRemoved(index);

        }
        return taskModel;
    }

    @Nullable
    public TaskModel remove(int index) {

        TaskModel taskModel = null;
        if (index >= taskModels.size()) {
            throw new IllegalStateException("Index is not in range");
        } else {
            taskModel = taskModels.remove(index);
            if (taskModel != null) {
                notifyItemRemoved(index);
            }

        }
        return taskModel;

    }

    TaskModel get(int index) {
        if (index < 0 || index >= taskModels.size()) {
            throw new IllegalStateException("Index is not in range");
        }
        return taskModels.get(index);
    }


    public void clear() {
        taskModels.clear();
        notifyDataSetChanged();
    }


    public Observable<TaskModel> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }


    /********* Nested Classes  ********/

    static class TaskItemViewHolder extends RecyclerView.ViewHolder {

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
        private DateUtils dateUtils;

        TaskItemViewHolder(View itemView, DateUtils dateUtils, Subject<TaskModel> clickSubject) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.dateUtils = dateUtils;
            this.clickSubject = clickSubject;
        }

        void setItem(final TaskModel taskModel) {
            this.taskModel = taskModel;
            textViewTitle.setText(taskModel.getTitle());
            textViewDeadline.setText(dateUtils.getDisplayDate(taskModel.getDeadline()));
            viewPriority.setBackgroundResource(UiUtils.getPriorityColorRes(taskModel.getPriority()));

        }


        @OnClick(R.id.task_layout)
        void onTaskClick() {
            clickSubject.onNext(taskModel);
        }


    }
}
