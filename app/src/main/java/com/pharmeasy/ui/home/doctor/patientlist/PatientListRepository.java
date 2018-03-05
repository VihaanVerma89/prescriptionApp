package com.pharmeasy.ui.home.doctor.patientlist;

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
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;

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

    private Doctor mDoctor;

    public void setDoctor(Doctor doctor) {
        mDoctor = doctor;
    }

    public Doctor getDoctor() {
        return mDoctor;
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
                    }
                });
            } else {
                emitter.onError(new Exception("User not logged in."));
            }

        }, BackpressureStrategy.BUFFER);

        return userFlowable;
    }


    public Completable requestPrescription(Doctor doctor, User user) {
        Completable completable = Completable.create(source -> {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child(Tables.USERS_PRESCRIPTION_REQUESTS)
                    .child(user.getUid());

            String key = ref.push().getKey();
            ref.child(key).setValue(doctor).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    source.onComplete();
                } else {
                    source.onError(task.getException());
                }
            });

        });

        return completable;
    }
}

