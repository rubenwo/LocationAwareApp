package com.ruben.woldhuis.androideindopdrachtapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruben.woldhuis.androideindopdrachtapp.Models.Meetup;
import com.ruben.woldhuis.androideindopdrachtapp.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AllEventsAdapter extends RecyclerView.Adapter<com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder> implements Serializable {

    Context mContext;
    Meetup meetup;
    private ArrayList<Meetup> dataSource;

    public AllEventsAdapter(Context context, ArrayList<Meetup> dataArgs) {
        dataSource = dataArgs;
        this.mContext = context;
    }

    @Override
    public com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new com.ruben.woldhuis.androideindopdrachtapp.Adapters.AllEventsAdapter.MyViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull AllEventsAdapter.MyViewHolder myViewHolder, int i) {
        meetup = dataSource.get(i);
        myViewHolder.title.setText(meetup.getEventName());
    }


    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view, final Context context) {
            super(view);
            title = (TextView) view.findViewById(R.id.event_name_TextView);
        }

    }
}