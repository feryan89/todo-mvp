package com.todo.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.todo.data.exception.FirebaseDataException;

import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


public class RxFirebaseUtilsImpl implements RxFirebaseUtils {


    private RxIdlingResource rxIdlingResource;

    public RxFirebaseUtilsImpl(RxIdlingResource rxIdlingResource) {
        this.rxIdlingResource = rxIdlingResource;
    }

    @NonNull
    public <T> Single<T> getSingle(@NonNull final Task<T> task) {

        rxIdlingResource.increment();

        return Single.create(emitter -> {
            task.addOnSuccessListener(t -> {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(t);
                    rxIdlingResource.decrement();
                }

            });
            task.addOnFailureListener(e -> {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                    rxIdlingResource.decrement();
                }
            });
        });


    }

    @NonNull
    public Completable getCompletable(@NonNull final Task<Void> task) {

        rxIdlingResource.increment();

        return Completable.create(emitter -> {
            task.addOnSuccessListener(t -> {
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                    rxIdlingResource.decrement();
                }

            });
            task.addOnFailureListener(e -> {
                rxIdlingResource.increment();
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                    rxIdlingResource.decrement();
                }
            });
        });


    }


    @NonNull
    public Observable<DataSnapshot> getObservable(@NonNull final Query query) {

        rxIdlingResource.increment();

        return Observable.create(emitter -> query.addValueEventListener(new ValueEventListener() {

            boolean firstTime = true;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(dataSnapshot);
                    if (firstTime) {
                        rxIdlingResource.decrement();
                        firstTime = false;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (!emitter.isDisposed()) {
                    emitter.onError(new FirebaseDataException(databaseError));
                }
            }
        }));

    }

    @NonNull
    public Single<DataSnapshot> getSingle(@NonNull final Query query) {

        rxIdlingResource.increment();

        return Single.create(emitter -> query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(dataSnapshot);
                    rxIdlingResource.decrement();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (!emitter.isDisposed()) {
                    rxIdlingResource.decrement();
                    emitter.onError(new FirebaseDataException(databaseError));
                }
            }

        }));

    }

}