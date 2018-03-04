package com.pharmeasy.ui.home.prescriptionList;

import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

import java.util.List;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface PrescriptionListContract {

    interface View extends BaseView<Presenter>{
//        void onPrescriptionsSuccess(List<Prescription> prescriptions);
        void onPrescriptionAdded(Prescription prescription);
//        void onPrescriptionsException(Throwable exception);
        void onPrescriptionException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void loadPrescription(String uuid);
    }
}
