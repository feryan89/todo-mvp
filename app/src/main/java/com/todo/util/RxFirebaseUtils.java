package com.todo.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;

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
    public <T> Observable<T> getObservable(@NonNull final Task<T> task) {

        return Observable.create(emitter -> {
            task.addOnSuccessListener(emitter::onNext);
            task.addOnFailureListener(emitter::onError);
        }, Emitter.BackpressureMode.BUFFER);
    }

}