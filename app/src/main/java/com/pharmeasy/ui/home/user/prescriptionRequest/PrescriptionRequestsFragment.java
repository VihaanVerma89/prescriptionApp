package com.pharmeasy.ui.home.user.prescriptionRequest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pharmeasy.R;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListAdapter;

import java.util.ArrayList;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionRequestsFragment extends Fragment implements
        PrescriptionRequestsContract.View, PrescriptionRequestsAdapter.PrescriptionRequestsListener {

    public static PrescriptionRequestsFragment newInstance() {

        Bundle args = new Bundle();

        PrescriptionRequestsFragment fragment = new PrescriptionRequestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prescription_requests, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        mPresenter.loadPrescriptionRequests(FirebaseAuth.getInstance().getUid());
    }

    private void initViews() {
        initRecyclerView();
    }

    private RecyclerView mRecyclerView;
    private PrescriptionRequestsAdapter mAdapter;

    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.prescriptionRequestsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PrescriptionRequestsAdapter(getContext(), mDoctors, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Doctor> mDoctors = new ArrayList<>();

    @Override
    public void onPrescriptionRequestAdded(Doctor doctor) {
        mDoctors.add(doctor);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPrescriptionRequestException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPrescriptionAllowed() {
        Toast.makeText(getActivity(), "Prescription shared with doctor.", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onPrescriptionAllowException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private PrescriptionRequestsContract.Presenter mPresenter;

    @Override
    public void setPresenter(PrescriptionRequestsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onAllowedClicked(int position) {
        Doctor doctor = mDoctors.get(position);
        mPresenter.allowDoctor(doctor, PrescriptionRequestsRepository.getInstance().getUser());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
