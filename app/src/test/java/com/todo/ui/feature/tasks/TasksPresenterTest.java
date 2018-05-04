package com.todo.ui.feature.tasks;

import com.todo.data.model.TaskModel;
import com.todo.data.model.TaskModelComparator;
import com.todo.data.repository.TodoRepository;
import com.todo.util.UiSchedulersTransformer;
import com.todo.util.UiSchedulersTransformerTestImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TasksPresenterTest {


    private TaskModel fakeTask;
    private List<TaskModel> fakeTasks;

    @Spy
    private UiSchedulersTransformer uiSchedulersTransformer = new UiSchedulersTransformerTestImpl();

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TasksPresenter tasksPresenter;

    private TasksContract.View view;

    @Before
    public void setUp() {

        view = Mockito.mock(TasksContract.View.class);
        tasksPresenter = Mockito.spy(new TasksPresenter());
        tasksPresenter.attachView(view);
        MockitoAnnotations.initMocks(this);

        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);

        fakeTasks = new ArrayList<>();
        TaskModel taskModel = new TaskModel("Id1", "Title3", System.currentTimeMillis(), TaskModel.PRIORITY_2, false);
        fakeTasks.add(taskModel);
        taskModel = new TaskModel("Id2", "Title2", System.currentTimeMillis(), TaskModel.PRIORITY_1, true);
        fakeTasks.add(taskModel);
        taskModel = new TaskModel("Id2", "Title2", System.currentTimeMillis(), TaskModel.PRIORITY_3, false);
        fakeTasks.add(taskModel);


    }

    @Test
    public void addTask_shouldShowAddEditTaskActivity() {

        tasksPresenter.addTask();

        verify(view).showAddEditTaskActivity();
    }

    @Test
    public void getTask_shouldShowEmptyView() {


        // Setup conditions of the test
        when(todoRepository.getTasks()).thenReturn(Observable.just(Collections.emptyList()));

        // Execute the code under test
        tasksPresenter.getTasks();

        // Make assertions on the results

        verify(view).showTasksEmptyView();

    }

    @Test
    public void getTask_byDateSort_shouldShowTasks() {


        // Setup conditions of the test
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));


        // Execute the code under test
        tasksPresenter.getTasks();

        // Make assertions on the results
        verify(view).showTasks(fakeTasks);

    }

    @Test
    public void getTask_byPriority_shouldShowTasks() {


        // Setup conditions of the test
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));

        // Execute the code under test
        tasksPresenter.setTasksSortType(TasksSortType.BY_PRIORITY);
        tasksPresenter.getTasks();

        // Make assertions on the results

        Collections.sort(fakeTasks, new TaskModelComparator.ByPriorityComparator());
        verify(view).showTasks(fakeTasks);

    }

    @Test
    public void getTask_byTitle_shouldShowTasks() {


        // Setup conditions of the test
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));

        // Execute the code under test
        tasksPresenter.setTasksSortType(TasksSortType.BY_TITLE);
        tasksPresenter.getTasks();

        // Make assertions on the results

        Collections.sort(fakeTasks, new TaskModelComparator.ByTitleComparator());
        verify(view).showTasks(fakeTasks);

    }

    @Test
    public void deleteTask_shouldComplete() {


        // Setup conditions of the test
        when(todoRepository.deleteTask(fakeTask)).thenReturn(Completable.complete());
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));

        // Execute the code under test
        tasksPresenter.deleteTask(fakeTask);

        // Make assertions on the results

        verify(todoRepository).deleteTask(fakeTask);
        verify(tasksPresenter).getTasks();

    }

    @Test
    public void updateTask_shouldComplete() {


        // Setup conditions of the test
        when(todoRepository.updateTask(fakeTask)).thenReturn(Completable.complete());
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));

        // Execute the code under test
        tasksPresenter.updateTask(fakeTask);

        // Make assertions on the results

        verify(todoRepository).updateTask(fakeTask);

    }

    @Test
    public void updateTask_shouldThrowError() {


        // Setup conditions of the test
        when(todoRepository.updateTask(fakeTask)).thenReturn(Completable.error(new Exception()));
        when(todoRepository.getTasks()).thenReturn(Observable.just(fakeTasks));

        // Execute the code under test
        tasksPresenter.updateTask(fakeTask);

        // Make assertions on the results

        verify(tasksPresenter).getTasks();

    }

}
