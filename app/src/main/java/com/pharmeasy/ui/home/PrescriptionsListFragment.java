package com.pharmeasy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pharmeasy.R;
import com.pharmeasy.ui.prescription.PrescriptionActivity;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionsListFragment extends Fragment implements View.OnClickListener{

    public static PrescriptionsListFragment newInstance() {

        Bundle args = new Bundle();

        PrescriptionsListFragment fragment = new PrescriptionsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prescription_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){
        initFab();
    }

    private FloatingActionButton mFab;
    private void initFab(){
        mFab = getView().findViewById(R.id.addPrescriptionFab);
        mFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addPrescriptionFab:
                onAddNewPrescriptionClicked();
                break;
        }
    }

    private void onAddNewPrescriptionClicked() {
        Intent intent = new Intent(getActivity(), PrescriptionActivity.class);
        startActivity(intent);
    }
}
