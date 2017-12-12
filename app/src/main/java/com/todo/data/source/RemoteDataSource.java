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

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    RxFirebaseUtils rxFirebaseUtils;

    RemoteDataSource(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth, RxFirebaseUtils rxFirebaseUtils) {
        this.firebaseDatabase = firebaseDatabase;
        this.firebaseAuth = firebaseAuth;
        this.rxFirebaseUtils=rxFirebaseUtils;
    }
}
