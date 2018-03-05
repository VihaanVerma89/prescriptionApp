package com.pharmeasy.ui.home.doctor.prescriptionlist;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface DoctorPrescriptionListContract {

    interface View extends BaseView<Presenter>{
        void onPatientPrescriptionAdded(User user);
        void onPatientPrescriptionException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void loadPrescriptions(String uid);
    }
}
