package com.todo.ui.validator;

import android.content.res.Resources;

import com.todo.R;
import com.todo.ui.model.ValidationResultViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

/**
 * @author Waleed Sarwar
 * @since Nov 26, 2017
 */

public class EmailValidator {

    /********* Constant Fields  ********/

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /********* Member Fields  ********/

    private Pattern pattern;
    private Resources resources;

    /********* Constructors ********/

    @Inject
    public EmailValidator(Resources resources) {
        this.pattern = Pattern.compile(EMAIL_PATTERN);
        this.resources = resources;
    }

    /********* Member Methods  ********/

    public ValidationResultViewModel validate(final String email) {

        Matcher matcher = pattern.matcher(email);

        if (email.isEmpty()) {
            return ValidationResultViewModel.failure(resources.getString(R.string.all_error_email_required));
        } else if (!matcher.matches()) {
            return ValidationResultViewModel.failure(resources.getString(R.string.all_error_email_invalid));
        } else {
            return ValidationResultViewModel.success();
        }

    }
}

