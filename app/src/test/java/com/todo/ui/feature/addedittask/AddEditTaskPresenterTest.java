package com.todo.ui.feature.addedittask;

import android.content.res.Resources;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.data.repository.TodoRepository;
import com.todo.device.TaskReminderScheduler;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.UiSchedulersTransformer;
import com.todo.util.UiSchedulersTransformerTestImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import io.reactivex.Completable;
import io.reactivex.Single;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddEditTaskPresenterTest {


    private static final String FAKE_TITLE_REQUIRED_TEST = "title_required_test";

    private TaskModel fakeTask;

    @Spy
    private UiSchedulersTransformer uiSchedulersTransformer = new UiSchedulersTransformerTestImpl();

    @Mock
    private TodoRepository todoRepository;

    @Mock
    TaskReminderScheduler taskReminderScheduler;

    @Spy
    StringUtils stringUtils = new StringUtilsImpl();

    @Mock
    private Resources resources;

    @InjectMocks
    private AddEditTaskPresenter addEditTaskPresenter;


    private AddEditTaskContract.View view;

    @Before
    public void setUp() {

        view = Mockito.mock(AddEditTaskContract.View.class);
        addEditTaskPresenter = Mockito.spy(new AddEditTaskPresenter());
        addEditTaskPresenter.attachView(view);
        MockitoAnnotations.initMocks(this);

        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);

    }


    @Test
    public void createTask_taskWithEmptyTitle_shouldShowSnackBar() {

        // Setup conditions of the test
        fakeTask.setTitle("");
        when(resources.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST);


        // Execute the code under test
        addEditTaskPresenter.createTask(fakeTask);

        // Make assertions on the results
        verify(view).showSnackBar(FAKE_TITLE_REQUIRED_TEST);

    }

    @Test
    public void createTask_validTask_shouldCreateSuccessfully() {

        // Setup conditions of the test
        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);
        when(todoRepository.createTask(fakeTask)).thenReturn(Single.just(fakeTask));
        when(resources.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST);


        // Execute the code under test
        addEditTaskPresenter.createTask(fakeTask);

        // Make assertions on the results
        verify(view).finishActivity();

    }

    @Test
    public void updateTask_taskWithEmptyTitle_shouldShowSnackBar() {

        // Setup conditions of the test
        fakeTask.setTitle("");
        when(resources.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST);


        // Execute the code under test
        addEditTaskPresenter.updateTask(fakeTask);

        // Make assertions on the results
        verify(view).showSnackBar(FAKE_TITLE_REQUIRED_TEST);

    }

    @Test
    public void updateTask_validTask_shouldUpdateSuccessfully() {

        // Setup conditions of the test
        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);
        when(todoRepository.updateTask(fakeTask)).thenReturn(Completable.complete());
        when(resources.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST);


        // Execute the code under test
        addEditTaskPresenter.updateTask(fakeTask);

        // Make assertions on the results
        verify(view).finishActivity();

    }

    @Test
    public void scheduleReminder_reminderValueGreaterThanZero_shouldScheduleTask() {

        // Setup conditions of the test
        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);
        fakeTask.setReminder(System.currentTimeMillis());

        // Execute the code under test
        addEditTaskPresenter.scheduleReminder(fakeTask);

        // Make assertions on the results
        verify(taskReminderScheduler).scheduleTaskReminder(fakeTask);

    }

    @Test
    public void scheduleReminder_reminderValueZero_shouldNotScheduleTask() {

        // Setup conditions of the test
        fakeTask = new TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false);

        // Execute the code under test
        addEditTaskPresenter.scheduleReminder(fakeTask);

        // Make assertions on the results
        verify(taskReminderScheduler,times(0)).scheduleTaskReminder(fakeTask);

    }


}