package com.shalinijain.myapplication6;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Shalini on 31-03-2018.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView title;
    public TextView party;
    public MyViewHolder(View itemView) {
        super(itemView);
        name=(TextView) itemView.findViewById(R.id.title);
        title=(TextView)itemView.findViewById(R.id .offcie_view3);
        party=(TextView)itemView.findViewById(R.id.party);

    }
}
