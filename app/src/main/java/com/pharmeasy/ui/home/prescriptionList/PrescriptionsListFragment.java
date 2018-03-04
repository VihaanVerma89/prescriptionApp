package com.pharmeasy.ui.home.prescriptionList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pharmeasy.R;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.ui.prescription.PrescriptionActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionsListFragment extends Fragment implements PrescriptionListContract.View,
        View
        .OnClickListener{

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
        loadPrescriptions();
    }

    private void initViews(){
        initFab();
        initRecyclerView();
    }

    private FloatingActionButton mFab;
    private void initFab(){
        mFab = getView().findViewById(R.id.addPrescriptionFab);
        mFab.setOnClickListener(this);
    }

    private RecyclerView mRecyclerView;
    private PrescriptionListAdapter mAdapter;

    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.prescriptionsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PrescriptionListAdapter(getContext(), mPrescriptions);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void loadPrescriptions(){
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null)
        {
            mPresenter.loadPrescription(currentUser.getUid());
        }
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

    private PrescriptionListContract.Presenter mPresenter;
    @Override
    public void setPresenter(PrescriptionListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private ArrayList<Prescription> mPrescriptions = new ArrayList<>();
    @Override
    public void onPrescriptionAdded(Prescription prescription) {
        mPrescriptions.add(prescription);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrescriptionException(Throwable exception) {

    }
}
