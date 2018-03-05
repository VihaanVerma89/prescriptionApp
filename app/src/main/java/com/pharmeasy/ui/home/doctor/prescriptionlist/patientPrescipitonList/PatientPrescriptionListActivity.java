package com.pharmeasy.ui.home.doctor.prescriptionlist.patientPrescipitonList;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pharmeasy.R;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListAdapter;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListPresenter;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListRepository;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionsListFragment;
import com.pharmeasy.ui.login.LoginFragment;
import com.pharmeasy.ui.login.LoginPresenter;
import com.pharmeasy.ui.login.LoginRepository;
import com.pharmeasy.utils.ActivityUtils;

import org.parceler.Parcels;

/**
 * Created by vihaanverma on 05/03/18.
 */

public class PatientPrescriptionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);
        initViews();
    }

    private void setTitle(){
        User user = Parcels.unwrap(getIntent().getExtras().getParcelable(PrescriptionsListFragment
                .EXTRA_USER));
        setTitle(user.getName() + "'s Prescription");
    }

    private PrescriptionListPresenter mPresenter;
    private PrescriptionsListFragment mFragment;
    private void initViews(){
        setTitle();
        mFragment = PrescriptionsListFragment.newInstance(getIntent().getExtras());
        ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout, mFragment);
        mPresenter = new PrescriptionListPresenter(PrescriptionListRepository.getInstance(), mFragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
