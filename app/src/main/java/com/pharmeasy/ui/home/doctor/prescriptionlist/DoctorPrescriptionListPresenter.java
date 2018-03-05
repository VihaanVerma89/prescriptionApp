package com.pharmeasy.ui.home.doctor.prescriptionlist;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListContract;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListFragment;
import com.pharmeasy.ui.home.doctor.patientlist.PatientListRepository;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class DoctorPrescriptionListPresenter implements DoctorPrescriptionListContract.Presenter {

    private DoctorPrescriptionListRepository mRepository;
    private DoctorPrescriptionListContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public DoctorPrescriptionListPresenter(DoctorPrescriptionListRepository repository,
                                          DoctorPrescriptionListFragment
            view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadPrescriptions(String uid) {
        Disposable disposable= mRepository.loadPrescriptions(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            mView.onPatientPrescriptionAdded(user);
                        },
                        exception -> {
                            mView.onPatientPrescriptionException(exception);
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }


}
