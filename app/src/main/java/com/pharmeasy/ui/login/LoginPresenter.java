package com.pharmeasy.ui.signup;

import io.reactivex.CompletableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class SignUpPresenter implements SignUpContract.Presenter {


    private SignUpContract.View mView;
    private SignUpRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public SignUpPresenter(SignUpRepository repository, SignUpFragment view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void signUp(String username, String password) {

       Disposable disposable =  mRepository.signUp(username, password)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    mView.setUser(user);
                        },
                        exception -> {
                    mView.signUpException(exception);
                        });
       mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
