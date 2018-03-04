package com.pharmeasy.ui.home.user.prescriptionRequest;

import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface PrescriptionRequestsContract {

    interface View extends BaseView<Presenter>{
        void onPrescriptionRequestAdded(Doctor doctor);
        void onPrescriptionRequestException(Throwable exception);
        void onPrescriptionAllowed();
        void onPrescriptionAllowException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void loadPrescriptionRequests(String uid);
        void allowDoctor(Doctor doctor, User user);
    }
}
