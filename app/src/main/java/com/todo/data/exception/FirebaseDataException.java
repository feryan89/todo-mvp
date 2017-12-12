package com.todo.data.exception;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseError;

/**
 * @author Waleed Sarwar
 * @since Dec 13, 2017
 */

public class FirebaseDataException extends Exception {

    private DatabaseError error;

    public FirebaseDataException(@NonNull DatabaseError error) {
        this.error = error;
    }

    public DatabaseError getError() {
        return error;
    }

    @Override
    public String toString() {
        return "RxFirebaseDataException{" +
                "error=" + error +
                '}';
    }
}
