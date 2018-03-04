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
import com.pharmeasy.models.User;

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

    private FirebaseAuth mAuth;

    public Single<User> login(final String username, final String password) {
        mAuth = FirebaseAuth.getInstance();
        final String email = username + "@pharmeasy.com";
        Single<User> userSingle = Single.create(emitter -> {
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
                                            emitter.onSuccess(dataSnapshot.getValue(User.class));
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
        });
        return userSingle;
    }


}
