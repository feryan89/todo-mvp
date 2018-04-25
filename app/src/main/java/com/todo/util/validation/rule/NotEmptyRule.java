package com.todo.util.validation.rule;


import io.reactivex.Observable;

public class NotEmptyRule extends Rule {

    public NotEmptyRule(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public Observable<String> validate(String input) {
        if (isEmpty(input)) {
            return Observable.just(errorMessage);
        }
        return Observable.just(EMPTY);
    }

    private boolean isEmpty(String value) {
        return value.trim().length() == 0;
    }
}
