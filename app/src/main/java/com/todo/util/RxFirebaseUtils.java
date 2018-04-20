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
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;


@Singleton
public class RxFirebaseUtils {

    private static final String TAG = "RxFirebaseUtils";

    @NonNull
    public <T> Single<T> getSingle(@NonNull final Task<T> task) {

        return Single.create(emitter -> {
            task.addOnSuccessListener(t -> {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(t);
                }

            });
            task.addOnFailureListener(e -> {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            });
        });


    }

    @NonNull
    public Completable getCompleteable(@NonNull final Task<Void> task) {

        return Completable.create(emitter -> {
            task.addOnSuccessListener(t -> {
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }

            });
            task.addOnFailureListener(e -> {
                if (!emitter.isDisposed()) {
                    emitter.onError(e);
                }
            });
        });


    }


    @NonNull
    public Observable<DataSnapshot> getObservable(@NonNull final Query query) {


        return Observable.create(emitter -> query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(dataSnapshot);
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


        return Single.create(emitter -> query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(dataSnapshot);
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

}