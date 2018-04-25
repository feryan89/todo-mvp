package com.todo.util.validation.rule;

import io.reactivex.Observable;

public class ValidEmailRule extends MatchPatternRule {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public ValidEmailRule(String errorMessage) {
        super(errorMessage, EMAIL_PATTERN);
    }

    @Override
    public Observable<String> validate(String input) {
        return super.validate(input);
    }
}
