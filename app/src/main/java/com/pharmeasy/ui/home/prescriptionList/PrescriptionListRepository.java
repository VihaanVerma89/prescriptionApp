package com.pharmeasy.ui.home.prescriptionList;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pharmeasy.database.Tables;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.login.LoginRepository;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionListRepository {
    public final String TAG = getClass().getName();

    private static PrescriptionListRepository INSTANCE = null;

    public static PrescriptionListRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PrescriptionListRepository();
        }
        return INSTANCE;
    }

    public Flowable<Prescription> loadPrescription(String uuid) {
        Flowable<Prescription> prescriptionFlowable =
                Flowable.create(new FlowableOnSubscribe<Prescription>() {
                    @Override
                    public void subscribe(FlowableEmitter<Prescription> emitter) throws Exception {

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (currentUser != null) {

                            Query child = FirebaseDatabase.getInstance().getReference()
                                    .child(Tables.USERS_PRESCRIPTIONS)
                                    .child(currentUser.getUid())
                                    .orderByKey();

                            child.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                    Prescription prescription = dataSnapshot.getValue
                                            (Prescription.class);
                                    emitter.onNext(prescription);

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
                        }
                        else {
                            emitter.onError(new Exception("User not logged in."));
                        }
                    }
                }, BackpressureStrategy.BUFFER);

        return prescriptionFlowable;
    }


}

