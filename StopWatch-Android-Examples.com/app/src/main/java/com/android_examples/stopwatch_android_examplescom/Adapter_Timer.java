package com.android_examples.stopwatch_android_examplescom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class Adapter_Timer extends RecyclerView.Adapter<Adapter_Timer.ViewHolder> {

    ArrayList<ModelTimer> model_descriptions;
    Context context;

    public Adapter_Timer(Context context, ArrayList<ModelTimer> model_descriptions) {
        this.model_descriptions = model_descriptions;
        this.context = context;
    }



    @Override
    public Adapter_Timer.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timer, parent, false);

        ViewHolder viewHolder1 = new ViewHolder(view);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ModelTimer description=model_descriptions.get(position);
        holder.start.setText(description.getStartTime());
        holder.end.setText(description.getEndTime());
        holder.elapsed.setText(description.getElapsedTime());
    }

    @Override
    public int getItemCount() {

        return model_descriptions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView start;
        public TextView end;
        public TextView elapsed;

        public ViewHolder(View v) {
            super(v);
            start = (TextView) v.findViewById(R.id.start);
            end = (TextView) v.findViewById(R.id.end);
            elapsed = (TextView) v.findViewById(R.id.elapsed);
        }
    }
}

