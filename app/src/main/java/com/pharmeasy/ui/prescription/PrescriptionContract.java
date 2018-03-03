package com.pharmeasy.ui.prescription;

import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.BasePresenter;
import com.pharmeasy.ui.BaseView;

/**
 * Created by vihaanverma on 03/03/18.
 */

public interface PrescriptionContract {

    interface View extends BaseView<Presenter>{
        void onPrescriptionAdded(Prescription prescription);
        void onPrescriptionException(Throwable exception);
    }

    interface Presenter extends BasePresenter{
        void addPrescription(Prescription prescription);
    }
}
