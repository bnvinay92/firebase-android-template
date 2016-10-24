package com.github.homeroom.android.data;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.firebase.database.DataSnapshot;

/**
 * Created by Vinay on 30/09/16.
 */
@AutoValue
public abstract class FirebaseChildEvent {

    public abstract DataSnapshot dataSnapshot();
    public abstract Type type();
    @Nullable public abstract String previousKey();

    public enum Type {
        ADDED, READY, CHANGED, REMOVED, MOVED
    }

    public static FirebaseChildEvent create(DataSnapshot snapshot, Type type, @Nullable String previousKey) {
        return new AutoValue_FirebaseChildEvent(snapshot, type, previousKey);
    }
}
