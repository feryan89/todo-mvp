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

    Observable<List<Task>> getTasks();

    Completable updateTask(Task task);

    Completable deleteTask(Task task);


    void createTask(String title, long deadline, int priority, boolean completed);

}
