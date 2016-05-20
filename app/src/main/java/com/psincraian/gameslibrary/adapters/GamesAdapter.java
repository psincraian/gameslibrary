package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psincraian.gameslibrary.models.Game;

import java.util.List;

/**
 * Created by petrusqui on 20/05/16.
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    private List<Game> games;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(android.R.id.text1);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GamesAdapter(List<Game> mDataset) {
        games = mDataset;
    }

    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(games.get(position).getTitle());

    }

    public void add(Game game) {
        games.add(game);
        notifyItemInserted(games.size());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
}
