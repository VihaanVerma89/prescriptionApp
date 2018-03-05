package com.pharmeasy.ui.home.doctor.prescriptionlist;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrintDocumentAdapter;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pharmeasy.R;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.doctor.prescriptionlist.patientPrescipitonList.PatientPrescriptionListActivity;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionsListFragment;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class DoctorPrescriptionListFragment extends Fragment  implements
        DoctorPrescriptionListContract.View , DoctorPrescriptionListAdapter.PatientListener{

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
        initViews();
        mPresenter.loadPrescriptions(DoctorPrescriptionListRepository.getInstance().getDoctor()
                .getUid());
    }

    private void initViews(){
        initRecyclerView();
    }

    private RecyclerView mRecyclerView;
    private DoctorPrescriptionListAdapter mAdapter;
    private List<User> mPatients = new ArrayList<>();

    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.doctorPrescriptionListRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DoctorPrescriptionListAdapter(getContext(), mPatients, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPatientPrescriptionAdded(User user) {
        mPatients.add(user);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPatientPrescriptionException(Throwable exception) {
        String message = exception.getMessage();
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private DoctorPrescriptionListContract.Presenter mPresenter;
    @Override
    public void setPresenter(DoctorPrescriptionListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPatientClicked(int position) {
        User user = mPatients.get(position);
        Intent intent = new Intent(getActivity(), PatientPrescriptionListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PrescriptionsListFragment.EXTRA_USER, Parcels.wrap(user));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
