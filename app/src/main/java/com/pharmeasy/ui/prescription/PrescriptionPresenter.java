package com.pharmeasy.ui.prescription;

import com.pharmeasy.models.Prescription;
import com.pharmeasy.ui.home.PrescriptionRequestsFragment;
import com.pharmeasy.ui.login.LoginContract;
import com.pharmeasy.ui.login.LoginRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionPresenter implements PrescriptionContract.Presenter {


    private PrescriptionContract.View mView;
    private PrescriptionRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public PrescriptionPresenter(PrescriptionRepository repository, PrescriptionContract.View
            view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }


    @Override
    public void addPrescription(Prescription prescription){

        Disposable disposable = mRepository.addPrescription(prescription)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                            mView.onPrescriptionAdded(null);
                        },
                        exception -> {
                            mView.onPrescriptionException(exception);
                        });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

}
