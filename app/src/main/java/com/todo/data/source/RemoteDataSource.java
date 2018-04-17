package com.todo.data.source;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.util.RxFirebaseUtils;

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

public class RemoteDataSource {


    final static String FIREBASE_CHILD_KEY_TASKS = "tasks";

    final FirebaseDatabase firebaseDatabase;
    final FirebaseAuth firebaseAuth;
    final RxFirebaseUtils rxFirebaseUtils;

    RemoteDataSource(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, RxFirebaseUtils rxFirebaseUtils) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
        this.rxFirebaseUtils=rxFirebaseUtils;
    }
}
