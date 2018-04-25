package com.todo.util.validation.validator;

import com.todo.util.validation.rule.Rule;

import java.util.List;

import io.reactivex.Observable;

public interface RulesValidator {

    Observable<String> validate(Observable<String> input, List<Rule> rules);


}
