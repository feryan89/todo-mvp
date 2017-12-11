package com.todo.data.source;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.util.RxFirebaseUtils;

import rx.Single;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public class UserRemoteDataSource extends RemoteDataSource {

    public UserRemoteDataSource(FirebaseDatabase firebaseDatabase
            , FirebaseAuth firebaseAuth, RxFirebaseUtils rxFirebaseUtils) {
        super(firebaseDatabase, firebaseAuth, rxFirebaseUtils);
    }

    /**
     * @return todo
     */
    public Single<Boolean> isUserLoggedIn() {
        return Single.just(firebaseAuth.getCurrentUser() != null);

    }

    /**
     * @param email    todo
     * @param password todo
     * @return todo
     */
    public Single<AuthResult> login(final String email, final String password) {
        return rxFirebaseUtils.getSingle(firebaseAuth.signInWithEmailAndPassword(email,
                password));
    }

    /**
     * @param email    todo
     * @param password todo
     * @return todo
     */
    @RxLogObservable(RxLogObservable.Scope.EVERYTHING)
    public Single<AuthResult> register(final String email, final String password) {
        return rxFirebaseUtils.getSingle(firebaseAuth.createUserWithEmailAndPassword(email, password));
    }
}
