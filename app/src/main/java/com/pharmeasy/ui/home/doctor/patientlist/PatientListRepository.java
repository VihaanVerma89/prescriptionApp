package com.pharmeasy.ui.home.doctor.patientlist;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pharmeasy.database.Tables;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PatientListRepository {
    public final String TAG = getClass().getName();

    private static PatientListRepository INSTANCE = null;

    public static PatientListRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PatientListRepository();
        }
        return INSTANCE;
    }

    public Flowable<User> loadPatients() {
        Flowable<User> userFlowable = Flowable.create(emitter -> {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {

                Query child = FirebaseDatabase.getInstance().getReference()
                        .child(Tables.USERS)
                        .orderByKey();

                child.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        User user = dataSnapshot.getValue
                                (User.class);
                        emitter.onNext(user);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        emitter.onError(databaseError.toException());
                    }
                });
            } else {
                emitter.onError(new Exception("User not logged in."));
            }

        }, BackpressureStrategy.BUFFER);

        return userFlowable;
    }


}

