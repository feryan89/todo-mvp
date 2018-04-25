package com.todo.util.validation.rule;


import io.reactivex.Observable;

public abstract class Rule {
    static final String EMPTY = "";
    protected final String errorMessage;

    Rule(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public abstract Observable<String> validate(String input);
}
