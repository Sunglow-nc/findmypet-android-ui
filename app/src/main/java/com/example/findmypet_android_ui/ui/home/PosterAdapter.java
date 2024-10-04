package com.example.findmypet_android_ui.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmypet_android_ui.R;
import com.example.findmypet_android_ui.databinding.PosterLayoutBinding;
import com.example.findmypet_android_ui.model.Poster;

import java.util.ArrayList;
import java.util.List;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {
    List<Poster> posterList;
    Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    public PosterAdapter(RecyclerViewInterface recyclerViewInterface, Context context, List<Poster> posterList) {
        this.recyclerViewInterface = recyclerViewInterface;
        this.context = context;
        this.posterList = posterList;
    }

    @NonNull
    @Override
    public PosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PosterLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.poster_layout,
                parent,
                false
        );
        return new PosterViewHolder(binding, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {
        holder.binding.setPoster(posterList.get(position));
    }

    @Override
    public int getItemCount() {
        return posterList.size();
    }

    public void setFilteredList(ArrayList<Poster> filteredList) {
        this.posterList = filteredList;
        notifyDataSetChanged();
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder{
        PosterLayoutBinding binding;

        public PosterViewHolder(PosterLayoutBinding binding, RecyclerViewInterface recyclerViewInterface) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
