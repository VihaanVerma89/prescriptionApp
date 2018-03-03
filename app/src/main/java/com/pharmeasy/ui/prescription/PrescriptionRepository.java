package com.pharmeasy.ui.prescription;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pharmeasy.database.Tables;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;

import io.reactivex.Single;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionRepository {

    public final String TAG = getClass().getName();

    private static PrescriptionRepository INSTANCE = null;

    public static PrescriptionRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PrescriptionRepository();
        }
        return INSTANCE;
    }

    public Single<Prescription> addPrescription(Prescription prescription) {
        Single<Prescription> prescriptionSingle = Single.create(emitter -> {
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                String key = FirebaseDatabase.getInstance().getReference().child(Tables.USERS_PRESCRIPTIONS)
                        .child(firebaseUser.getUid())
                        .push().getKey();
                Task<Void> voidTask = FirebaseDatabase.getInstance().getReference().child(Tables.USERS_PRESCRIPTIONS)
                        .child(firebaseUser.getUid())
                        .child(key)
                        .setValue(prescription);

                voidTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (voidTask.isSuccessful()) {
                            emitter.onSuccess(prescription);
                        } else {
                            emitter.onError(voidTask.getException());
                        }
                    }
                });
            }

        });
        return prescriptionSingle;
    }


}
