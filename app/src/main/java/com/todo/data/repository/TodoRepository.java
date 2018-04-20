package com.todo.data.repository;

import com.todo.data.model.Task;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface TodoRepository {

    Single<Boolean> isUserLoggedIn();

    Completable login(String email, String password);

    Completable register(String email, String password);


    Single<Task> createTask(Task task);

    Completable updateTask(Task task);

    Completable deleteTask(Task task);


    Observable<List<Task>> getTasks();


}
