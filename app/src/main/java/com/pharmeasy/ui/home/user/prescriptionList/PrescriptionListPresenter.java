package com.pharmeasy.ui.home.user.prescriptionList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class PrescriptionListPresenter implements PrescriptionListContract.Presenter {

    private PrescriptionListRepository mRepository;
    private PrescriptionListContract.View mView;

    public PrescriptionListPresenter(PrescriptionListRepository repository, PrescriptionsListFragment
            view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadPrescription(String uuid) {

        mRepository.loadPrescription(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(prescription -> {
                            mView.onPrescriptionAdded(prescription);
                        },
                        exception -> {
                            mView.onPrescriptionException(exception);
                        });

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
