package com.pharmeasy.ui.home.user.prescriptionRequest;

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

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionRequestsRepository {
    public final String TAG = getClass().getName();

    private static PrescriptionRequestsRepository INSTANCE = null;

    public static PrescriptionRequestsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PrescriptionRequestsRepository();
        }
        return INSTANCE;
    }

    public Flowable<Doctor> loadPrescriptionRequests(String uid) {
        Flowable<Doctor> prescriptionFlowable =
                Flowable.create(new FlowableOnSubscribe<Doctor>() {
                    @Override
                    public void subscribe(FlowableEmitter<Doctor> emitter) throws Exception {

                        Query child = FirebaseDatabase.getInstance().getReference()
                                .child(Tables.USERS_PRESCRIPTION_REQUESTS)
                                .child(uid)
                                .orderByKey();

                        child.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Doctor doctor = dataSnapshot.getValue
                                        (Doctor.class);
                                emitter.onNext(doctor);

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
//                                emitter.onError(databaseError.toException());
                            }
                        });
                    }
                }, BackpressureStrategy.BUFFER);

        return prescriptionFlowable;
    }


    private User mUser;

    public void setUser(User user) {
        mUser = user;
    }

    public User getUser() {
        return mUser;
    }


    public Completable allowDoctor(Doctor doctor, User user) {
        Completable completable = Completable.create(source -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child(Tables.DOCTORS_PRESCRIPTIONS)
                    .child(doctor.getUid());

            String key = ref.push().getKey();
            ref.child(key).setValue(user).addOnCompleteListener(task -> {
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

