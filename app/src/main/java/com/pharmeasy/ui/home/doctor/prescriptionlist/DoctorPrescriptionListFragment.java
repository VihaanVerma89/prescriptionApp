package com.pharmeasy.ui.home.doctor.prescriptionlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pharmeasy.R;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class DoctorPrescriptionListFragment extends Fragment {

    public static DoctorPrescriptionListFragment newInstance() {

        Bundle args = new Bundle();

        DoctorPrescriptionListFragment fragment = new DoctorPrescriptionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_doctor_prescription_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
