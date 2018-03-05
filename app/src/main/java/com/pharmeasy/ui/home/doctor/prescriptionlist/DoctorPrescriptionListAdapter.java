package com.pharmeasy.ui.home.doctor.prescriptionlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.models.User;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by vihaanverma on 04/03/18.
 */

class DoctorPrescriptionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mPatients;
    private Context mContext;

    public interface PatientListener{
        void onPatientClicked(int position);
    }

    private WeakReference<PatientListener> mPatientListenerRef;

    public DoctorPrescriptionListAdapter(Context context, List<User> prescriptions, PatientListener patientListener) {
        mContext = context;
        mPatients = prescriptions;
        mPatientListenerRef = new WeakReference<PatientListener>(patientListener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_prescrtion,
                parent,
                false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PatientViewHolder) {
            PatientViewHolder viewHolder = (PatientViewHolder) holder;
            User user= mPatients.get(position);
            viewHolder.name.setText(user.getName());
//            viewHolder.prescriptionCount.setText(user.getDetail());
        }
    }

    @Override
    public int getItemCount() {
        return mPatients.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {

        private TextView name;

        public PatientViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mPatientListenerRef.get()!=null)
            {
                mPatientListenerRef.get().onPatientClicked(getAdapterPosition());
            }
        }
    }
}
