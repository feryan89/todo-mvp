package com.todo.util.validation.rule;

import java.util.regex.Pattern;

import io.reactivex.Observable;

public class MatchPatternRule extends Rule {

    private Pattern pattern;

    public MatchPatternRule(String errorMessage, String pattern) {
        super(errorMessage);
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public Observable<String> validate(String input) {
        if (matchPattern(input)) {
            return Observable.just(EMPTY);
        } else {
            return Observable.just(errorMessage);
        }
    }

    private boolean matchPattern(String input) {
        return pattern.matcher(input).matches();
    }
}
