package com.todo.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.model.Task;
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
    private com.google.android.gms.tasks.Task<Void> mockVoidTask;

    private RxFirebaseUtils rxFirebaseUtils;

    private UserRemoteDataSource userRemoteDataSource;


    @Before
    public void setUp() throws Exception {

        rxFirebaseUtils = new RxFirebaseUtils();
        userRemoteDataSource = Mockito.spy(new UserRemoteDataSource(mockFirebaseDatabase, mockFirebaseAuth, rxFirebaseUtils));
        doReturn(mockChildReference).when(userRemoteDataSource).getChildReference();

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
    public void registerUser_shouldReturnOnSuccess() {


    }

    @Test
    public void createTask_shouldReturnCreatedTask() {

        Task task = Mockito.mock(Task.class);
        when(mockChildReference.push()).thenReturn(mockChildReference);
        when(mockChildReference.getKey()).thenReturn("key");
        when(mockChildReference.child("key")).thenReturn(mockChildReference);
        when(mockChildReference.setValue(task)).thenReturn(mockVoidTask);

        TestObserver<Task> testObserver = userRemoteDataSource.createTask(task).test();

        testObserver.assertValue(task);


    }

    @Test
    public void updateTask_shouldReturnCreatedTask() {

        Task task = Mockito.mock(Task.class);
        when(mockChildReference.push()).thenReturn(mockChildReference);
        when(mockChildReference.getKey()).thenReturn("key");
        when(mockChildReference.child("key")).thenReturn(mockChildReference);
        when(mockChildReference.setValue(task)).thenReturn(mockVoidTask);



        TestObserver<Task> testObserver = userRemoteDataSource.createTask(task).test();

        testObserver.assertValue(task);


    }


}