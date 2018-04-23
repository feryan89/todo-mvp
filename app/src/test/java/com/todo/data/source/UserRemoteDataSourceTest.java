package com.todo.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.model.TaskModel;
import com.todo.util.RxFirebaseUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRemoteDataSourceTest {

    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private DatabaseReference mockChildReference;
    @Mock
    private AuthResult mockAuthResult;

    @Mock
    private Task<Void> mockVoidTask;
    @Mock
    private Task<AuthResult> mockAuthResultTask;
    @Captor
    private ArgumentCaptor<OnCompleteListener> testOnCompleteListener;
    @Captor
    private ArgumentCaptor<OnSuccessListener> testOnSuccessListener;
    @Captor
    private ArgumentCaptor<OnFailureListener> testOnFailureListener;


    private RxFirebaseUtils rxFirebaseUtils;

    private UserRemoteDataSource userRemoteDataSource;


    @Before
    public void setUp() throws Exception {
        setupTask(mockAuthResultTask);
        rxFirebaseUtils = new RxFirebaseUtils();
        userRemoteDataSource = Mockito.spy(new UserRemoteDataSource(mockFirebaseDatabase, mockFirebaseAuth, rxFirebaseUtils));
        doReturn(mockChildReference).when(userRemoteDataSource).getChildReference();

    }

    @SuppressWarnings("unchecked")
    private <T> void setupTask(Task<T> task) {
        when(task.addOnSuccessListener(testOnSuccessListener.capture())).thenReturn(task);
        when(task.addOnFailureListener(testOnFailureListener.capture())).thenReturn(task);
    }


    @Test
    public void getCurrentUser_shouldReturnNotNull() {

        FirebaseUser fakeLoggedInUser = Mockito.mock(FirebaseUser.class);
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(fakeLoggedInUser);

        FirebaseUser firebaseUser = userRemoteDataSource.getCurrentUser();

        Assert.assertNotNull(firebaseUser);

    }

    @Test
    public void getCurrentUser_shouldReturnNull() {

        when(mockFirebaseAuth.getCurrentUser()).thenReturn(null);

        FirebaseUser firebaseUser = userRemoteDataSource.getCurrentUser();

        Assert.assertNull(firebaseUser);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void registerUser_shouldReturnOnSuccess() {

        when(mockFirebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        TestObserver<AuthResult> testObserver = userRemoteDataSource.register(anyString(), anyString()).test();

        verify(mockAuthResultTask).addOnSuccessListener(testOnSuccessListener.capture());
        testOnSuccessListener.getValue().onSuccess(mockAuthResult);

        testObserver.assertNoErrors().assertComplete().assertValue(mockAuthResult);
        testObserver.dispose();


    }

    @Test
    @SuppressWarnings("unchecked")
    public void registerUser_shouldReturnInvalidCredentials() {

        Exception exception = new Exception("Invalid Credentials");
        when(mockFirebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        TestObserver<AuthResult> testObserver = userRemoteDataSource.register(anyString(), anyString()).test();

        verify(mockAuthResultTask).addOnFailureListener(testOnFailureListener.capture());
        testOnFailureListener.getValue().onFailure(exception);

        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void createTask_shouldReturnCreatedTask() {

        TaskModel taskModel = Mockito.mock(TaskModel.class);
        when(mockChildReference.push()).thenReturn(mockChildReference);
        when(mockChildReference.getKey()).thenReturn("key");
        when(mockChildReference.child("key")).thenReturn(mockChildReference);
        when(mockChildReference.setValue(taskModel)).thenReturn(mockVoidTask);

        TestObserver<TaskModel> testObserver = userRemoteDataSource.createTask(taskModel).test();

        testObserver.assertValue(taskModel);


    }

    @Test
    public void updateTask_shouldReturnCreatedTask() {

        TaskModel taskModel = Mockito.mock(TaskModel.class);
        when(mockChildReference.push()).thenReturn(mockChildReference);
        when(mockChildReference.getKey()).thenReturn("key");
        when(mockChildReference.child("key")).thenReturn(mockChildReference);
        when(mockChildReference.setValue(taskModel)).thenReturn(mockVoidTask);

        TestObserver<TaskModel> testObserver = userRemoteDataSource.createTask(taskModel).test();

        testObserver.assertValue(taskModel);


    }


}