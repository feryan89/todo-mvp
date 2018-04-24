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


public interface RxFirebaseUtils {


    @NonNull
    <T> Single<T> getSingle(@NonNull final Task<T> task);

    @NonNull
    Completable getCompletable(@NonNull final Task<Void> task);


    @NonNull
    Observable<DataSnapshot> getObservable(@NonNull final Query query);

    @NonNull
    Single<DataSnapshot> getSingle(@NonNull final Query query);

}