package com.example.findmypet_android_ui.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmypet_android_ui.model.Poster;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PosterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PosterViewHolder extends RecyclerView.ViewHolder{

    }
}
