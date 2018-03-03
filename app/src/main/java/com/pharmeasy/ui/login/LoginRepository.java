package com.pharmeasy.ui.signup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.MainActivity;

import dalvik.annotation.TestTargetClass;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class SignUpRepository {

    public final String TAG = getClass().getName();

    private static SignUpRepository INSTANCE = null;
    public static SignUpRepository getInstance(){
        if(INSTANCE == null)
        {
           INSTANCE = new SignUpRepository();
        }
        return INSTANCE;
    }

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public Single<User> signUp(final String username, final String password) {
        mAuth = FirebaseAuth.getInstance();
        final String email = username+ "@pharmeasy.com";

//        final Single<User> singleUser = new Single<User>() {
//            @Override
//            protected void subscribeActual(SingleObserver<? super User> observer) {
//
//            }
//        };
        Single<User> user = Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(SingleEmitter<User> e) throws Exception {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = task.getResult().getUser();
                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    User user = new User();
                                    user.setName(username);
                                    user.setUid(firebaseUser.getUid());
                                    mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
                                    e.onSuccess(user);
                                } else {
                                    e.onError(task.getException());
                                }
                            }
                        });
            }
        });
        return user;

    }



}
