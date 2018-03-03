package com.pharmeasy.ui.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pharmeasy.R;
import com.pharmeasy.utils.ActivityUtils;

import io.reactivex.internal.operators.maybe.MaybeEqualSingle;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();
    }

    private SignUpPresenter mPresenter;
    private SignUpFragment mSignUpFragment;
    private void initViews(){
        mSignUpFragment = SignUpFragment.newInstance();
        ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout, mSignUpFragment);
        mPresenter = new SignUpPresenter(SignUpRepository.getInstance(), mSignUpFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
