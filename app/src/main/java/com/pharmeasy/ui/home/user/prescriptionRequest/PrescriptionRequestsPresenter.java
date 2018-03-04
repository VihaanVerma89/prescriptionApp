package com.pharmeasy.ui.home.user.prescriptionRequest;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionRequestsPresenter implements PrescriptionRequestsContract.Presenter {

    private PrescriptionRequestsRepository mRepository;
    private PrescriptionRequestsFragment mView;
    private CompositeDisposable mCompositeDisposable;

    public PrescriptionRequestsPresenter(PrescriptionRequestsRepository repository, PrescriptionRequestsFragment
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
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadPrescriptionRequests(String uid) {
        Disposable disposable = mRepository.loadPrescriptionRequests(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(doctor -> {
                            mView.onPrescriptionRequestAdded(doctor);
                        },
                        exception -> {
                            mView.onPrescriptionRequestException(exception);
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void allowDoctor(Doctor doctor, User user) {
        mRepository.allowDoctor(doctor, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        mView.onPrescriptionAllowed();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.onPrescriptionAllowException(e);
                    }
                });

    }
}
