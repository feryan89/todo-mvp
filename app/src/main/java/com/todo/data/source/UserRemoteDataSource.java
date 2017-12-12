package com.todo.data.source;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.model.Task;
import com.todo.util.RxFirebaseUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.Single;

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

    public Single<Boolean> isUserLoggedIn() {
        return Single.just(firebaseAuth.getCurrentUser() != null);

    }

    public Completable login(final String email, final String password) {
        return Completable.fromSingle(rxFirebaseUtils.getSingle(firebaseAuth.signInWithEmailAndPassword(email, password)));
    }

    public Completable register(final String email, final String password) {
        return Completable.fromSingle(rxFirebaseUtils.getSingle(firebaseAuth.createUserWithEmailAndPassword(email, password)));
    }

    public void createTask(final String title, final long deadline, final short priority) {

        final String key = getChildReference().push().getKey();
        Task task = new Task(key, title, deadline, priority);
        getChildReference().child(key).setValue(task);

    }

    public Observable<List<Task>> getTasks() {
        return rxFirebaseUtils.getObservable(getChildReference())
                .map(dataSnapshot -> {
                    List<Task> tasks = new ArrayList<>();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        tasks.add(child.getValue(Task.class));
                    }
                    return tasks;
                });
    }

    private DatabaseReference getChildReference() {
        if (this.childReference == null) {
            this.childReference = firebaseDatabase.
                    getReference()
                    .child(FIREBASE_CHILD_KEY_TASKS)
                    .child(firebaseAuth.getCurrentUser().getUid());
        }

        return childReference;
    }
}
