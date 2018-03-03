package com.pharmeasy.ui.signup;

import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface SignUpContract {

    interface View extends BaseView<Presenter>{

        void setUser(User user);

        void signUpException(Throwable exception);
    }

    interface Presenter extends BasePresenter{

        void signUp(String username, String password);
    }
}
