package com.pharmeasy.ui.home.doctor.patientlist;

import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface PatientListContract {

    interface View extends BaseView<Presenter>{
        void onPatientAdded(User user);
        void onPatientException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void loadPatients();
    }
}
