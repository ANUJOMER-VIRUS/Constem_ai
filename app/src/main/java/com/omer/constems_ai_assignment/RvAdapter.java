package com.omer.constems_ai_assignment;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    Context context;
    List<RecordModel>recordModels;

    public RvAdapter(Context context, List<RecordModel> recordModels) {
        this.context = context;
        this.recordModels = recordModels;
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int position) {
     holder.view.setVideoURI(Uri.parse(recordModels.get(position).getVideo()));
     holder.step.setText(recordModels.get(position).getStep());
        holder.ec.setText(recordModels.get(position).getEndcor());
        holder.sc.setText(recordModels.get(position).getStartcor());

    }

    @Override
    public int getItemCount() {
        return recordModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView view;
        TextView step,sc,ec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView.findViewById(R.id.video_view);
            step=itemView.findViewById(R.id.steps);
            sc=itemView.findViewById(R.id.startCor);
            ec=itemView.findViewById(R.id.endcor);
        }
    }
}
