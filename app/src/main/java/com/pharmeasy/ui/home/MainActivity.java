package com.pharmeasy.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.pharmeasy.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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
                }
                else
                {
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

    private List<Fragment> mFragments;

    private List<Fragment> getFragments() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(PrescriptionsListFragment.newInstance());
        mFragments.add(PrescriptionRequestsFragment.newInstance());
        return mFragments;
    }

}
