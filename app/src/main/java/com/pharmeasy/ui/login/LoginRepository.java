package com.pharmeasy.ui.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pharmeasy.database.Tables;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class LoginRepository {

    public final String TAG = getClass().getName();

    private static LoginRepository INSTANCE = null;

    public static LoginRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LoginRepository();
        }
        return INSTANCE;
    }

    public Single<Object> login(String username, String password)
    {
        Flowable<User> userFlowable = loginUser(username, password);
        Flowable<Doctor> doctorFlowable = loginDoctor(username, password);

        return  Flowable.concat(userFlowable, doctorFlowable)
                .firstElement()
                .toSingle();
    }

    private FirebaseAuth mAuth;
    public Flowable<User> loginUser(final String username, final String password) {
        mAuth = FirebaseAuth.getInstance();
        final String email = username + "@pharmeasy.com";
        Flowable<User> userSingle = Flowable.create(emitter -> {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            FirebaseDatabase.getInstance().getReference()
                                    .child(Tables.USERS)
                                    .child(firebaseUser.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User user= dataSnapshot.getValue(User.class);
                                            if(user!=null)
                                            {
                                                emitter.onNext(user);
                                            }
                                            emitter.onComplete();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            emitter.onError(databaseError.toException());
                                        }
                                    });
                        }
                        else{
                            emitter.onError(task.getException());
                        }
                    });
        }, BackpressureStrategy.BUFFER);
        return userSingle;
    }

    public Flowable<Doctor> loginDoctor(String username, String password)
    {
        mAuth = FirebaseAuth.getInstance();
        final String email = username + "@pharmeasy.com";
        Flowable<Doctor> doctorFlowable= Flowable.create(source -> {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener( task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = task.getResult().getUser();
                            FirebaseDatabase.getInstance().getReference()
                                    .child(Tables.DOCTORS)
                                    .child(firebaseUser.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Doctor doctor = dataSnapshot.getValue(Doctor.class);
                                            if(doctor!=null)
                                            {
                                                source.onNext(doctor);
                                            }
                                            source.onComplete();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            source.onError(databaseError.toException());
                                        }
                                    });
                        }
                        else{
                            source.onError(task.getException());
                        }
                    });
        }, BackpressureStrategy.BUFFER);
        return doctorFlowable;
    }


}
