package com.todo.data.repository;

import rx.Completable;
import rx.Single;

public interface TodoRepository {

    Single<Boolean> isUserLoggedIn();

    Completable login(String email, String password);

    Completable register(String email, String password);

}
