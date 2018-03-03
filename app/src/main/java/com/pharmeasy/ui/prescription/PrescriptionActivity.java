package com.pharmeasy.ui.prescription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pharmeasy.R;
import com.pharmeasy.ui.login.LoginFragment;
import com.pharmeasy.ui.login.LoginPresenter;
import com.pharmeasy.ui.login.LoginRepository;
import com.pharmeasy.utils.ActivityUtils;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();
    }

    private PrescriptionPresenter mPresenter;
    private PrescriptionFragment mPrescriptionFragment;

    private void initViews() {
        mPrescriptionFragment = PrescriptionFragment.newInstance();
        ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout, mPrescriptionFragment);
        mPresenter = new PrescriptionPresenter(PrescriptionRepository.getInstance(),
                mPrescriptionFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

