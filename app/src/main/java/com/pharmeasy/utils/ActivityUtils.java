package com.pharmeasy.utils;

import android.drm.DrmStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pharmeasy.R;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class ActivityUtils {

    public static void showFragment(FragmentManager fm, int container, Fragment fragment)
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(container, fragment);
        ft.commit();
    }



}

