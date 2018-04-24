package com.todo.data.source;

import android.support.annotation.VisibleForTesting;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.model.TaskModel;
import com.todo.util.RxFirebaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public class UserRemoteDataSource extends RemoteDataSource {

    private DatabaseReference childReference = null;

    /********* Constructors ********/

    public UserRemoteDataSource(FirebaseDatabase firebaseDatabase
            , FirebaseAuth firebaseAuth, RxFirebaseUtils rxFirebaseUtils) {
        super(firebaseDatabase, firebaseAuth, rxFirebaseUtils);
    }

    /********* Member Methods  ********/

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();

    }

    public Single<AuthResult> login(final String email, final String password) {
        return rxFirebaseUtils.getSingle(firebaseAuth.signInWithEmailAndPassword(email, password));
    }

    public Single<AuthResult> register(final String email, final String password) {
        return rxFirebaseUtils.getSingle(firebaseAuth.createUserWithEmailAndPassword(email, password));

    }

    public Single<TaskModel> createTask(TaskModel taskModel) {

        final String key = getChildReference().push().getKey();
        taskModel.setId(key);
        getChildReference().child(key).setValue(taskModel);

        return Single.just(taskModel);

    }


    public Completable updateTask(final TaskModel taskModel) {
        DatabaseReference databaseReference = getChildReference().child(taskModel.getId());
        Task<Void> voidTask = databaseReference.updateChildren(taskModel.getMap());
        return rxFirebaseUtils.getCompletable(voidTask);
    }

    public Completable deleteTask(final TaskModel taskModel) {
        return rxFirebaseUtils.getCompletable(getChildReference().child(taskModel.getId()).removeValue());


    }

    public Observable<List<TaskModel>> getTasks() {
        return rxFirebaseUtils.getObservable(getChildReference())
                .map(dataSnapshot -> {
                    List<TaskModel> taskModels = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        taskModels.add(child.getValue(TaskModel.class));
                    }
                    return taskModels;
                });
    }


    @VisibleForTesting
    DatabaseReference getChildReference() {
        if (this.childReference == null) {
            this.childReference = firebaseDatabase.
                    getReference()
                    .child(FIREBASE_CHILD_KEY_TASKS)
                    .child(firebaseAuth.getCurrentUser().getUid());
        }

        return childReference;
    }
}
