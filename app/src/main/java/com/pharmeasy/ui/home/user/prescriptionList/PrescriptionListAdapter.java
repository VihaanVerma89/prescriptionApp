package com.pharmeasy.ui.home.user.prescriptionList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pharmeasy.R;
import com.pharmeasy.models.Prescription;

import java.util.ArrayList;

/**
 * Created by vihaanverma on 04/03/18.
 */

public class PrescriptionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Prescription> mPrescriptions;
    private Context mContext;
    public PrescriptionListAdapter(Context context, ArrayList<Prescription> prescriptions)
    {
        mContext = context;
        mPrescriptions = prescriptions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription, parent, false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PrescriptionViewHolder)
        {
            PrescriptionViewHolder viewHolder = (PrescriptionViewHolder) holder;
            Prescription prescription = mPrescriptions.get(position);
            viewHolder.name.setText(prescription.getName());
            viewHolder.detail.setText(prescription.getDetail());
        }
    }

    @Override
    public int getItemCount() {
        return mPrescriptions.size();
    }

    public class PrescriptionViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{

        private TextView name, detail;
        public PrescriptionViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTV);
            detail= itemView.findViewById(R.id.detailTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
