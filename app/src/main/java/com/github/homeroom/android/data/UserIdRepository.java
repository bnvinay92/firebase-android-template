package com.github.homeroom.android.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import rx.Single;

/**
 * Created by Vinay on 05/10/16.
 */
public class UserIdRepository {

    private static final String PATH = "users";

    private final FirebaseAuth firebaseAuth;
    private final DatabaseReference users;

    @Inject public UserIdRepository(FirebaseAuth firebaseAuth, DatabaseReference databaseReference) {
        this.firebaseAuth = firebaseAuth;
        this.users = databaseReference.child(PATH);
    }

    private Single<FirebaseUser> firebaseUser() {
        if (firebaseAuth.getCurrentUser() == null) {
            return newUser();
        } else {
            return Single.fromCallable(firebaseAuth::getCurrentUser);
        }
    }

    private Single<FirebaseUser> newUser() {
        return Single.create(emitter -> firebaseAuth.signInAnonymously()
                .addOnSuccessListener(authResult -> emitter.onSuccess(authResult.getUser()))
                .addOnFailureListener(emitter::onError));
    }

    public Single<String> userId() {
        return firebaseUser().map(FirebaseUser::getUid);
    }
}
