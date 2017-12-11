package com.todo.data.repository;

import com.todo.data.source.UserRemoteDataSource;

import rx.Completable;
import rx.Single;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public class TodoRepositoryImpl implements TodoRepository {

    private UserRemoteDataSource userRemoteDataSource;

    public TodoRepositoryImpl(UserRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
    }

    @Override
    public Single<Boolean> isUserLoggedIn() {
        return userRemoteDataSource.isUserLoggedIn();
    }

    @Override
    public Completable login(String email, String password) {
        return Completable.fromSingle(userRemoteDataSource.login(email, password));

    }

    @Override
    public Completable register(String email, String password) {
        return Completable.fromSingle(userRemoteDataSource.register(email, password));
    }

}
