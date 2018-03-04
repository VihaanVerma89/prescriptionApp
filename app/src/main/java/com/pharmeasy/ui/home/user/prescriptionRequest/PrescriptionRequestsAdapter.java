package com.pharmeasy.ui.home.user.prescriptionRequest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.models.Doctor;
import com.pharmeasy.models.User;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by vihaanverma on 04/03/18.
 */

class PrescriptionRequestsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Doctor> mDoctors;
    private Context mContext;

    public interface PrescriptionRequestsListener{
        void onAllowedClicked(int position);
    }

    private WeakReference<PrescriptionRequestsListener> mListenerRef;

    public PrescriptionRequestsAdapter(Context context, List<Doctor> doctors,
                                       PrescriptionRequestsListener listener) {
        mContext = context;
        mDoctors = doctors;
        mListenerRef = new WeakReference<PrescriptionRequestsListener>(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription_request, parent,
                false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PatientViewHolder) {
            PatientViewHolder viewHolder = (PatientViewHolder) holder;
            Doctor doctor= mDoctors.get(position);
            viewHolder.name.setText(doctor.getName());
//            viewHolder.prescriptionCount.setText(user.getDetail());
        }
    }

    @Override
    public int getItemCount() {
        return mDoctors.size();
    }

    public class PatientViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {

        private TextView name;
        private Button allowBtn;

        public PatientViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.doctorNameTV);
            allowBtn = itemView.findViewById(R.id.allowBTN);
            allowBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListenerRef.get()!=null)
            {
                mListenerRef.get().onAllowedClicked(getAdapterPosition());
            }
        }
    }
}
