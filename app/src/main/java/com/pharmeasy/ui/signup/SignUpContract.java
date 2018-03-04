package com.pharmeasy.ui.signup;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.Pharmacist;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface SignUpContract {

    interface View extends BaseView<Presenter>{
        void setUser(User user);
        void setDoctor(Doctor doctor);
        void setPharmacist(Pharmacist pharmacist);
        void signUpException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void signUpUser(String username, String password);
        void signUpDoctor(String username, String password);
        void signUpPharmacist(String username, String password);
    }
}
