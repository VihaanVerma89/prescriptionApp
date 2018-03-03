package com.pharmeasy.ui.prescription;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pharmeasy.R;
import com.pharmeasy.models.Prescription;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionFragment extends Fragment implements PrescriptionContract.View, View.OnClickListener {

    public static PrescriptionFragment newInstance() {

        Bundle args = new Bundle();

        PrescriptionFragment fragment = new PrescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_prescription, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private EditText  mNameET, mDetailET;
    private Button mSaveBTN;
    private void initViews() {
        mNameET = getView().findViewById(R.id.nameET);
        mDetailET= getView().findViewById(R.id.detailET);
        mSaveBTN = getView().findViewById(R.id.saveBTN);
        mSaveBTN.setOnClickListener(this);
    }

    @Override
    public void onPrescriptionAdded(Prescription prescription) {
        Toast.makeText(getActivity(), "Prescription added successfully!", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }

    @Override
    public void onPrescriptionException(Throwable exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private PrescriptionContract.Presenter mPresenter;
    @Override
    public void setPresenter(PrescriptionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.saveBTN:
                onSaveButtonClicked();
                break;
        }
    }

    private void onSaveButtonClicked() {
        String name = mNameET.getText().toString();
        String detail= mDetailET.getText().toString();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(detail))
        {
            Prescription prescription = new Prescription(name, detail);
            mPresenter.addPrescription(prescription);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
