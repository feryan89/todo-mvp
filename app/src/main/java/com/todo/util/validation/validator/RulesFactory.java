package com.todo.util.validation.validator;

import com.todo.util.validation.rule.Rule;

import java.util.List;

public interface RulesFactory {

    List<Rule> createEmailFieldRules();

    List<Rule> createPasswordFieldRules();

}
