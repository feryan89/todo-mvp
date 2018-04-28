package com.todo.ui.feature.launcher;

import com.todo.data.repository.TodoRepository;
import com.todo.util.SchedulerProvider;
import com.todo.util.SchedulerProviderTestImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Observable;

import io.reactivex.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LauncherPresenterTest {

    @Spy
    private SchedulerProvider schedulerProvider = new SchedulerProviderTestImpl();

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private LauncherPresenter launcherPresenter;

    private LauncherContract.View view;

    @Before
    public void setUp() {
        view = mock(LauncherContract.View.class);
        launcherPresenter = new LauncherPresenter();
        launcherPresenter.attachView(view);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void showNextActivity_ShouldShowLoginActivity() {

        // Setup conditions of the test
        when(todoRepository.isUserLoggedIn()).thenReturn(Single.just(Boolean.FALSE));

        // Execute the code under test
        launcherPresenter.showNextActivity();

        // Make assertions on the results..
        verify(view, times(1)).showLoginActivity();
    }

    @Test
    public void showNextActivity_ShouldShowTaskActivity() {

        // Setup conditions of the test
        when(todoRepository.isUserLoggedIn()).thenReturn(Single.just(Boolean.TRUE));

        // Execute the code under test
        launcherPresenter.showNextActivity();

        // Make assertions on the results..
        verify(view, times(1)).showTasksActivity();
    }
}