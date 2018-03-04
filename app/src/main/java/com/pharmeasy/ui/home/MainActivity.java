package com.pharmeasy.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pharmeasy.R;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListFragment;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListPresenter;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListRepository;
import com.pharmeasy.ui.home.doctor.prescriptionlist.DoctorPrescriptionListFragment;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListPresenter;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListRepository;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionsListFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "user";
    public static final String EXTRA_DOCTOR = "doctor";
    public static final String EXTRA_PHARMACIST = "pharmacist";

    private User mUser;
    private Doctor mDoctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBundle();
        initViews();
    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.containsKey(EXTRA_USER)) {
                mUser = Parcels.unwrap(bundle.getParcelable(EXTRA_USER));
            } else if (bundle.containsKey(EXTRA_DOCTOR)) {
                mDoctor = Parcels.unwrap(bundle.getParcelable(EXTRA_DOCTOR));
            }
        }
    }

    private void initViews() {
        initViewPager();
        initBottomNavigation();
    }

    private BottomNavigationView mBottomNavigationView;
    private MenuItem mPreviousMenuItem;

    private void initBottomNavigation() {
        mBottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(1);
                        return true;
                }
                return false;
            }
        };
        mBottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private ViewPager mViewPager;

    private void initViewPager() {
        mViewPager = findViewById(R.id.viewPager);
        ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter
                (getSupportFragmentManager(), getFragments());
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(viewPagerTabAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPreviousMenuItem != null) {
                    mPreviousMenuItem.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                mPreviousMenuItem = mBottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = null;
        if (mUser != null) {
            fragments = getUserFragments();
        } else if (mDoctor != null) {
            fragments = getDoctorFragments();
        }
        fragments = getDoctorFragments();

        return fragments;
    }


    private List<Fragment> mFragments = new ArrayList<>();
    private PrescriptionListPresenter mPrescriptionListPresenter;

    private List<Fragment> getUserFragments() {
        PrescriptionsListFragment prescriptionsListFragment = PrescriptionsListFragment
                .newInstance();
        mFragments.add(prescriptionsListFragment);
        mFragments.add(PrescriptionRequestsFragment.newInstance());

        mPrescriptionListPresenter = new PrescriptionListPresenter(PrescriptionListRepository
                .getInstance(), prescriptionsListFragment);
        return mFragments;
    }


    private PatientListPresenter mPatientListPresenter;

    private List<Fragment> getDoctorFragments() {

        PatientListFragment patientListFragment = PatientListFragment.newInstance();
        mPatientListPresenter = new PatientListPresenter(PatientListRepository.getInstance(),
                patientListFragment);

        DoctorPrescriptionListFragment doctorPrescriptionListFragment = DoctorPrescriptionListFragment.newInstance();
        mFragments.add(doctorPrescriptionListFragment);
        mFragments.add(patientListFragment);
        return mFragments;
    }
}
