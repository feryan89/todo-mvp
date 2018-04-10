package com.todo.data.repository;

import com.todo.data.model.Task;

import java.util.List;

import rx.Completable;
import rx.Observable;
import rx.Single;

public interface TodoRepository {

    Single<Boolean> isUserLoggedIn();

    Completable login(String email, String password);

    Completable register(String email, String password);


    void createTask(Task task);

    Completable updateTask(Task task);

    Completable deleteTask(Task task);


    Observable<List<Task>> getTasks();


}
