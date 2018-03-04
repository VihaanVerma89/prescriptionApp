package com.pharmeasy.ui.home.doctor.patientlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pharmeasy.R;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class PatientListFragment extends Fragment implements PatientListContract.View{

    public static PatientListFragment newInstance() {

        Bundle args = new Bundle();

        PatientListFragment fragment = new PatientListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        mPresenter.loadPatients();
    }

    private void initViews(){
        initRecyclerView();
    }

    private RecyclerView mRecyclerView;
    private PatientListAdapter mAdapter;
    private List<User> mPatients = new ArrayList<>();

    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.patientsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PatientListAdapter(getContext(), mPatients);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPatientAdded(User user) {
        mPatients.add(user);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPatientException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private PatientListContract.Presenter mPresenter;
    @Override
    public void setPresenter(PatientListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}


