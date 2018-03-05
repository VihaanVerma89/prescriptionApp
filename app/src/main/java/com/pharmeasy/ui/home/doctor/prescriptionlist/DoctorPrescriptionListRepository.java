package com.pharmeasy.ui.home.doctor.prescriptionlist;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pharmeasy.database.Tables;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class DoctorPrescriptionListRepository {
    public final String TAG = getClass().getName();

    private static DoctorPrescriptionListRepository INSTANCE = null;

    public static DoctorPrescriptionListRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoctorPrescriptionListRepository();
        }
        return INSTANCE;
    }

    private Doctor mDoctor;

    public void setDoctor(Doctor doctor) {
        mDoctor = doctor;
    }

    public Doctor getDoctor() {
        return mDoctor;
    }

    public Flowable<User> loadPrescriptions(String uid) {
        Flowable<User> userFlowable = Flowable.create(emitter -> {

            Query child = FirebaseDatabase.getInstance().getReference()
                    .child(Tables.DOCTORS_PRESCRIPTIONS)
                    .child(uid)
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
                }
            });

        }, BackpressureStrategy.BUFFER);

        return userFlowable;
    }

}

