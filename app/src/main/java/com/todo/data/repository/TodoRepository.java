package com.todo.data.repository;

import com.todo.data.model.TaskModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


public interface TodoRepository {

    Single<Boolean> isUserLoggedIn();

    Completable login(String email, String password);

    void logout();

    Completable register(String email, String password);


    Single<TaskModel> createTask(TaskModel taskModel);

    Completable updateTask(TaskModel taskModel);

    Completable deleteTask(TaskModel taskModel);

    Completable deleteTasks();

    Observable<List<TaskModel>> getTasks();


}
