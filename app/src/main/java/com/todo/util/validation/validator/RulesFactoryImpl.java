package com.todo.util.validation.validator;

import android.content.res.Resources;

import com.todo.R;
import com.todo.util.validation.rule.NotEmptyRule;
import com.todo.util.validation.rule.Rule;
import com.todo.util.validation.rule.ValidEmailRule;

import java.util.ArrayList;
import java.util.List;

public class RulesFactoryImpl implements RulesFactory {

    private Resources resources;

    public RulesFactoryImpl(Resources resources) {
        this.resources = resources;
    }

    @Override
    public List<Rule> createEmailFieldRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new NotEmptyRule(resources.getString(R.string.all_error_email_required)));
        rules.add(new ValidEmailRule(resources.getString(R.string.all_error_email_invalid)));
        return rules;
    }

    @Override
    public List<Rule> createPasswordFieldRules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new NotEmptyRule(resources.getString(R.string.all_error_password_required)));
        return rules;
    }

}
