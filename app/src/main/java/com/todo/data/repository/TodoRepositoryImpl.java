package com.todo.data.repository;

import com.todo.data.model.TaskModel;
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
        return Single.just(userRemoteDataSource.getCurrentUser() != null);
    }

    @Override
    public Completable login(String email, String password) {
        return Completable.fromSingle(userRemoteDataSource.login(email, password));

    }

    @Override
    public Completable register(String email, String password) {
        return Completable.fromSingle(userRemoteDataSource.register(email, password));
    }


    @Override
    public Single<TaskModel> createTask(TaskModel taskModel) {
        return userRemoteDataSource.createTask(taskModel);
    }

    @Override
    public Completable updateTask(TaskModel taskModel) {
        return userRemoteDataSource.updateTask(taskModel);
    }

    @Override
    public Completable deleteTask(TaskModel taskModel) {
        return userRemoteDataSource.deleteTask(taskModel);
    }

    @Override
    public Observable<List<TaskModel>> getTasks() {
        return userRemoteDataSource.getTasks();
    }


}
