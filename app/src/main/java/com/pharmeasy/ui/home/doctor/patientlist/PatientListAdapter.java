package com.pharmeasy.ui.home.doctor.patientlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.models.Prescription;
import com.pharmeasy.models.User;
import com.pharmeasy.ui.home.user.prescriptionList.PrescriptionListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vihaanverma on 04/03/18.
 */

class PatientListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> mPatients;
    private Context mContext;

    public PatientListAdapter(Context context, List<User> prescriptions) {
        mContext = context;
        mPatients = prescriptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient, parent,
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

        private TextView name, prescriptionCount;

        public PatientViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            prescriptionCount = itemView.findViewById(R.id.prescriptionCountTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
