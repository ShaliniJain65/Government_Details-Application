package com.shalinijain.myapplication6;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 31-03-2018.
 */

public class GovernmentAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<Government> governments;
    private MainActivity mainActivity;

    public GovernmentAdapter(MainActivity ma, ArrayList<Government> gov) {
        mainActivity = ma;
        governments = gov;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.governmententry, parent, false);
                 itemView.setOnClickListener(mainActivity);
                 itemView.setOnLongClickListener(mainActivity);
           return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Government gov=governments.get(position);
        holder.name.setText(gov.getOffice_name());
        //holder.name.setGravity(50);
        holder.title.setText(gov.getOfficial_name());
        holder.party.setText("(" + gov.getParty() + ")");

    }
    @Override
    public int getItemCount() {
        return governments.size();
    }
}
