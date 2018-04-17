package com.todo.ui.feature.tasks;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class TaskItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private final TaskItemTouchHelperCallback callback;

    public TaskItemTouchHelper(int dragDirs, int swipeDirs, TaskItemTouchHelperCallback callback) {
        super(dragDirs, swipeDirs);
        this.callback = callback;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            callback.onTaskDeleted(position);
        } else if (direction == ItemTouchHelper.RIGHT) {
            callback.onTaskCompleted(position);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE &&
                viewHolder instanceof TasksAdapter.TaskItemViewHolder) {

            TasksAdapter.TaskItemViewHolder taskItemViewHolder = (TasksAdapter.TaskItemViewHolder) viewHolder;
            if (dX > 0) {
                taskItemViewHolder.taskDeleteLayout.setVisibility(View.GONE);
                taskItemViewHolder.taskCompletedLayout.setVisibility(View.VISIBLE);
            } else {
                taskItemViewHolder.taskCompletedLayout.setVisibility(View.GONE);
                taskItemViewHolder.taskDeleteLayout.setVisibility(View.VISIBLE);
            }
            getDefaultUIUtil().onDraw(c, recyclerView, taskItemViewHolder.taskLayout, dX, dY,
                    actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof TasksAdapter.TaskItemViewHolder) {
            TasksAdapter.TaskItemViewHolder taskItemViewHolder = (TasksAdapter.TaskItemViewHolder) viewHolder;
            getDefaultUIUtil().clearView(taskItemViewHolder.taskLayout);
        }
    }

    public interface TaskItemTouchHelperCallback {

        void onTaskDeleted(int position);

        void onTaskCompleted(int position);
    }
}
