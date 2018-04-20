package com.todo.data.repository;

import com.todo.data.model.Task;
import com.todo.data.source.UserRemoteDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public class TodoRepositoryImpl implements TodoRepository {

    /********* Member Fields  ********/

    private final UserRemoteDataSource userRemoteDataSource;

    /********* Constructors ********/

    public TodoRepositoryImpl(UserRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
    }

    /********* TodoRepository Inherited Methods ********/

    @Override
    public Single<Boolean> isUserLoggedIn() {
        return userRemoteDataSource.isUserLoggedIn();
    }

    @Override
    public Completable login(String email, String password) {
        return userRemoteDataSource.login(email, password);

    }

    @Override
    public Completable register(String email, String password) {
        return userRemoteDataSource.register(email, password);
    }


    @Override
    public Single<Task> createTask(Task task) {
        return userRemoteDataSource.createTask(task);
    }

    @Override
    public Completable updateTask(Task task) {
        return userRemoteDataSource.updateTask(task);
    }

    @Override
    public Completable deleteTask(Task task) {
        return userRemoteDataSource.deleteTask(task);
    }

    @Override
    public Observable<List<Task>> getTasks() {
        return userRemoteDataSource.getTasks();
    }


}
