package com.todo.ui.model;

import android.support.annotation.Nullable;

/**
 * @author Waleed Sarwar
 * @since Nov 26, 2017
 */

public final class ValidationResultViewModel {

    public static ValidationResultViewModel success() {
        return new ValidationResultViewModel(true, null);
    }

    public static ValidationResultViewModel failure(@Nullable String errorMessage) {
        return new ValidationResultViewModel(false, errorMessage);
    }

    private boolean valid;
    private String errorMessage;

    public ValidationResultViewModel() {
    }

    public ValidationResultViewModel(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
