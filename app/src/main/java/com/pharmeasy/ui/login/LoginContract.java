package com.pharmeasy.ui.login;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter>{
        void setUser(User user);
        void setDoctor(Doctor doctor);
        void loginException(Throwable exception);
    }

    interface Presenter extends BasePresenter{

        void login(String username, String password);
    }
}
