package com.todo.util.validation.validator;

import com.todo.util.StringUtils;
import com.todo.util.validation.rule.Rule;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RulesValidatorImpl implements RulesValidator {

    private StringUtils stringUtils;

    public RulesValidatorImpl(StringUtils stringUtils) {
        this.stringUtils = stringUtils;
    }

    public Observable<String> validate(Observable<String> inputObservable, List<Rule> rules) {
        if (inputObservable == null) {
            throw new RuntimeException("Input Observable is null");
        }
        return inputObservable
                .switchMap((Function<String, ObservableSource<String>>) input ->
                        Observable.fromIterable(rules)
                                .concatMap(rule -> rule.validate(input))
                                .buffer(rules.size())
                                .map(this::getErrorMessageOrEmpty));

    }

    private String getErrorMessageOrEmpty(List<String> errorMessages) {
        for (String errorMessage : errorMessages) {
            if (stringUtils.isNotEmpty(errorMessage)) {
                return errorMessage;
            }
        }
        return StringUtils.EMPTY;
    }
}
