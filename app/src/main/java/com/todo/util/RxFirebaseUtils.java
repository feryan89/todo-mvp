package com.todo.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.todo.data.exception.FirebaseDataException;

import javax.inject.Singleton;

import rx.Emitter;
import rx.Observable;
import rx.Single;

@Singleton
public class RxFirebaseUtils {

    @NonNull
    public <T> Single<T> getSingle(@NonNull final Task<T> task) {

        return Single.fromEmitter(emitter -> {
            task.addOnSuccessListener(emitter::onSuccess);
            task.addOnFailureListener(emitter::onError);
        });

    }

    @NonNull
    public Observable<DataSnapshot> getObservable(@NonNull final Query query) {

        return Observable.create(emitter -> query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emitter.onNext(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                emitter.onError(new FirebaseDataException(databaseError));
            }
        }), Emitter.BackpressureMode.BUFFER);

    }

    @NonNull
    public Single<DataSnapshot> getSingle(@NonNull final Query query) {

        return Single.fromEmitter(emitter -> query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emitter.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                emitter.onError(new FirebaseDataException(databaseError));
            }
        }));

    }

}