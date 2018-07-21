package com.testtask.applicationa.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.testtask.applicationa.R;
import com.testtask.applicationa.entity.ImageModel;

import java.util.ArrayList;
import java.util.List;

import static com.testtask.applicationa.utils.Consts.DOWNLOADED;
import static com.testtask.applicationa.utils.Consts.ERROR;
import static com.testtask.applicationa.utils.Consts.UNDEFINED;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {

    List<ImageModel> imageModels = new ArrayList<>();
    OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public HistoryRecyclerAdapter(OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(List<ImageModel> imageModels){
        this.imageModels = imageModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        ImageModel image = imageModels.get(position);
        holder.bind(image, position);
    }

    @Override
    public int getItemCount() {
        return imageModels.size();
    }


    class HistoryViewHolder extends RecyclerView.ViewHolder{

        Context context;
        TextView imageTextView;
        ConstraintLayout constraintLayoutBg;

        HistoryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            constraintLayoutBg = itemView.findViewById(R.id.constraint_bg);
            imageTextView = itemView.findViewById(R.id.item_text);
        }

        void bind(ImageModel imageModel, int position){
            constraintLayoutBg.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
            imageTextView.setText(imageModel.getImageUrl());
            setBackgroundStatus(imageModel.getStatus());
        }

        private void setBackgroundStatus(int status){
            switch (status){
                case DOWNLOADED:
                    constraintLayoutBg.setBackgroundColor(context.getResources().getColor(android.R.color.holo_green_dark));
                    break;
                case ERROR:
                    constraintLayoutBg.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
                    break;
                case UNDEFINED:
                    constraintLayoutBg.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                    break;
            }
        }

    }

}
