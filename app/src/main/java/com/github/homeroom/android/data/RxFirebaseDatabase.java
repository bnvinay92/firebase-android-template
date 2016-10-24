package com.github.homeroom.android.data;

import com.github.homeroom.android.data.FirebaseChildEvent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import rx.AsyncEmitter;
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.functions.Action1;

import static com.github.homeroom.android.data.FirebaseChildEvent.Type.ADDED;
import static com.github.homeroom.android.data.FirebaseChildEvent.Type.CHANGED;
import static com.github.homeroom.android.data.FirebaseChildEvent.Type.MOVED;
import static com.github.homeroom.android.data.FirebaseChildEvent.Type.READY;
import static com.github.homeroom.android.data.FirebaseChildEvent.Type.REMOVED;
import static com.github.homeroom.android.data.FirebaseChildEvent.create;

/**
 * Created by Vinay on 30/09/16.
 */
public class RxFirebaseDatabase {

    public static Observable<FirebaseChildEvent> children(Query query) {
        return Observable.fromEmitter(new Action1<AsyncEmitter<FirebaseChildEvent>>() {
            @Override public void call(AsyncEmitter<FirebaseChildEvent> emitter) {
                ChildEventListener listener = new ChildEventListener() {
                    @Override public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                        emitter.onNext(create(dataSnapshot, ADDED, previousKey));
                    }

                    @Override public void onChildChanged(DataSnapshot dataSnapshot, String previousKey) {
                        emitter.onNext(create(dataSnapshot, CHANGED, previousKey));
                    }

                    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
                        emitter.onNext(create(dataSnapshot, REMOVED, null));
                    }

                    @Override public void onChildMoved(DataSnapshot dataSnapshot, String previousKey) {
                        emitter.onNext(create(dataSnapshot, MOVED, previousKey));
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                };
                ValueEventListener readyListener = new ValueEventListener() {
                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        emitter.onNext(create(dataSnapshot, READY, null));
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                };
                emitter.setCancellation(new AsyncEmitter.Cancellable() {
                    @Override public void cancel() throws Exception {
                        query.removeEventListener(listener);
                        query.removeEventListener(readyListener);
                    }
                });
                query.addChildEventListener(listener);
                query.addListenerForSingleValueEvent(readyListener);
            }
        }, AsyncEmitter.BackpressureMode.BUFFER);
    }

    public static Observable<DataSnapshot> values(Query query) {
        return Observable.fromEmitter(new Action1<AsyncEmitter<DataSnapshot>>() {
            @Override public void call(AsyncEmitter<DataSnapshot> emitter) {
                ValueEventListener listener = new ValueEventListener() {
                    @Override public void onDataChange(DataSnapshot dataSnapshot) {
                        emitter.onNext(dataSnapshot);
                    }

                    @Override public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                };
                emitter.setCancellation(new AsyncEmitter.Cancellable() {
                    @Override public void cancel() throws Exception {
                        query.removeEventListener(listener);
                    }
                });
                query.addValueEventListener(listener);
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

    public static Single<DataSnapshot> value(Query query) {
        return Single.create(singleSubscriber -> {
            ValueEventListener listener = new ValueEventListener() {
                @Override public void onDataChange(DataSnapshot dataSnapshot) {
                    singleSubscriber.onSuccess(dataSnapshot);
                }

                @Override public void onCancelled(DatabaseError databaseError) {
                    singleSubscriber.onError(databaseError.toException());
                }
            };
            query.addListenerForSingleValueEvent(listener);
            singleSubscriber.add(new Subscription() {
                @Override public void unsubscribe() {
                    query.removeEventListener(listener);
                }

                @Override public boolean isUnsubscribed() {
                    return false;
                }
            });
        });

    }
}
