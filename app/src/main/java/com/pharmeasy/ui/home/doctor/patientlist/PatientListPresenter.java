package com.pharmeasy.ui.home.doctor.patientlist;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListContract;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListRepository;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionsListFragment;

import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class PatientListPresenter implements PatientListContract.Presenter {

    private PatientListRepository mRepository;
    private PatientListContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    public PatientListPresenter(PatientListRepository repository, PatientListFragment
            view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadPatients() {

        Disposable disposable= mRepository.loadPatients()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            mView.onPatientAdded(user);
                        },
                        exception -> {
                            mView.onPatientException(exception);
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void requestPrescription(Doctor doctor, User user) {

        mRepository.requestPrescription(doctor,user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mView.onPatientRequestSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onPatientException(e);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
