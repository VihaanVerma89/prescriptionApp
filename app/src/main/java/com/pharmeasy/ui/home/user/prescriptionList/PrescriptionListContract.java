package com.pharmeasy.ui.home.user.prescriptionList;

import com.pharmeasy.models.Prescription;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface PrescriptionListContract {

    interface View extends BaseView<Presenter>{
        void onPrescriptionAdded(Prescription prescription);
        void onPrescriptionException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void loadPrescription(String uuid);
    }
}
