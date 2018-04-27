package com.todo.data.repository;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.todo.data.model.TaskModel;
import com.todo.data.source.UserRemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TodoRepositoryImplTest {

    @Mock
    private UserRemoteDataSource userRemoteDataSource;
    @Mock
    private AuthResult mockAuthResult;

    private TaskModel fakeTaskModel;
    private TodoRepositoryImpl todoRepository;

    @Before
    public void setUp() throws Exception {

        fakeTaskModel = new TaskModel("id", "title", 0, 1, false);
        todoRepository = new TodoRepositoryImpl(userRemoteDataSource);
    }

    @Test
    public void isUserLoggedIn_shouldReturnTrue() {

        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        when(userRemoteDataSource.getCurrentUser()).thenReturn(firebaseUser);

        TestObserver<Boolean> testObserver = todoRepository.isUserLoggedIn().test();

        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.TRUE);
        testObserver.assertComplete();

    }

    @Test
    public void isUserLoggedIn_shouldReturnFalse() {
        when(userRemoteDataSource.getCurrentUser()).thenReturn(null);

        TestObserver<Boolean> testObserver = todoRepository.isUserLoggedIn().test();
        testObserver.assertNoErrors();
        testObserver.assertValue(Boolean.FALSE);
        testObserver.assertComplete();

    }

    @Test
    public void login_shouldCompleteWithNoErrors() {

        when(userRemoteDataSource.login(anyString(), anyString())).thenReturn(Single.just(mockAuthResult));

        TestObserver testObserver = todoRepository.login(anyString(), anyString()).test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void login_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.login(anyString(), anyString())).thenReturn(Single.error(exception));

        TestObserver testObserver = todoRepository.login(anyString(), anyString()).test();

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void register_shouldCompleteWithNoError() {

        when(userRemoteDataSource.register(anyString(), anyString())).thenReturn(Single.just(mockAuthResult));

        TestObserver testObserver = todoRepository.register(anyString(), anyString()).test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void register_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.register(anyString(), anyString())).thenReturn(Single.error(exception));

        TestObserver testObserver = todoRepository.register(anyString(), anyString()).test();

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void createTask_shouldCompleteWithCreatedTask() {

        when(userRemoteDataSource.createTask(fakeTaskModel)).thenReturn(Single.just(fakeTaskModel));

        TestObserver<TaskModel> testObserver = todoRepository.createTask(fakeTaskModel).test();

        testObserver.assertNoErrors();
        testObserver.assertValue(fakeTaskModel);
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void createTask_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.createTask(fakeTaskModel)).thenReturn(Single.error(exception));

        TestObserver<TaskModel> testObserver = todoRepository.createTask(fakeTaskModel).test();

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void updateTask_shouldCompleteWithNoErrors() {

        when(userRemoteDataSource.updateTask(fakeTaskModel)).thenReturn(Completable.complete());

        TestObserver testObserver = todoRepository.updateTask(fakeTaskModel).test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void updateTask_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.updateTask(fakeTaskModel)).thenReturn(Completable.error(exception));

        TestObserver testObserver = todoRepository.updateTask(fakeTaskModel).test();

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void deleteTask_shouldCompleteWithNoErrors() {

        when(userRemoteDataSource.deleteTask(fakeTaskModel)).thenReturn(Completable.complete());

        TestObserver testObserver = todoRepository.deleteTask(fakeTaskModel).test();

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    public void deleteTask_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.deleteTask(fakeTaskModel)).thenReturn(Completable.error(exception));

        TestObserver testObserver = todoRepository.deleteTask(fakeTaskModel).test();

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void getTask_shouldReturnMockedTasks() {

        List<TaskModel> mockedTaskModels = Collections.emptyList();
        when(userRemoteDataSource.getTasks()).thenReturn(Observable.just(mockedTaskModels));

        TestObserver<List<TaskModel>> testObserver = todoRepository.getTasks().test();

        testObserver.assertNoErrors();
        testObserver.assertValue(mockedTaskModels);
        testObserver.dispose();
    }

    @Test
    public void getTask_shouldFailWithError() {

        Exception exception = new Exception();
        when(userRemoteDataSource.getTasks()).thenReturn(Observable.error(exception));

        TestObserver<List<TaskModel>> testObserver = todoRepository.getTasks().test();

        testObserver.assertError(exception);
        testObserver.dispose();
    }

}