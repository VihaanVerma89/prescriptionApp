package com.pharmeasy.ui.login;

import com.pharmeasy.ui.signup.SignUpContract;
import com.pharmeasy.ui.signup.SignUpFragment;
import com.pharmeasy.ui.signup.SignUpRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by vihaanverma on 03/03/18.
 */

public class LoginPresenter implements LoginContract.Presenter {


    private LoginContract.View mView;
    private LoginRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public LoginPresenter(LoginRepository repository, LoginContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void login(String username, String password) {

       Disposable disposable =  mRepository.login(username, password)
               .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    mView.setUser(user);
                        },
                        exception -> {
                    mView.loginException(exception);
                        });
       mCompositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }
}
