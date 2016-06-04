package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.psincraian.gameslibrary.models.Game;

import java.util.List;

/**
 * Created by petrusqui on 20/05/16.
 */
public class GamesAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private List<Game> games;
    private OnGameClick listener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GamesAdapter(List<Game> mDataset, OnGameClick listener) {
        games = mDataset;
        this.listener = listener;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        GameViewHolder vh = new GameViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        holder.mTextView.setText(games.get(position).getTitle());
        holder.bind(position, games.get(position), listener);
    }

    public void add(Game game) {
        games.add(game);
        notifyItemInserted(games.size());
    }

    public void remove(int position) {
        games.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public interface OnGameClick {
        void onGameClick(Game game);
        void onGameLongClick(int position, Game game);
    }
}
