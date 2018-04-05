package com.todo.ui.feature.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.todo.R;
import com.todo.data.model.Task;

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

    /********* Member Fields  ********/

    private List<Task> tasks;

    private final Subject<Task, Task> onItemClickSubject = BehaviorSubject.create();

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

    void updateTasks(List<Task> tasks) {
        this.tasks.clear();
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public Observable<Task> onItemClick() {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS);
    }


    /********* Nested Classes  ********/

    class TaskItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.task_view_priority)
        View viewPriority;

        @BindView(R.id.task_textview_title)
        TextView textViewTitle;

        @BindView(R.id.task_textview_deadline)
        TextView textViewDeadline;


        private Task task;
        private final Subject<Task, Task> clickSubject;

        public TaskItemViewHolder(View itemView, Subject<Task, Task> clickSubject) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.clickSubject = clickSubject;
        }

        public void setItem(final Task task) {
            this.task = task;
            textViewTitle.setText(task.getTitle());

        }


        @OnClick(R.id.task_layout)
        void onTaskClick() {
            clickSubject.onNext(task);
        }


    }
}
