package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by petrusqui on 20/05/16.
 */
public class GamesAdapter extends RecyclerView.Adapter<GameViewHolder> {

    private static final String CLASS_NAME = GamesAdapter.class.getSimpleName();
    private List<Game> games;
    private List<Game> allGames;
    private OnGameClick listener;
    private boolean filteredByPlaying;

    // Provide a suitable constructor (depends on the kind of dataset)
    public GamesAdapter(List<Game> mDataset, OnGameClick listener) {
        games = mDataset;
        allGames = new ArrayList<>(mDataset);
        this.listener = listener;
        this.filteredByPlaying = false;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game, parent, false);
        GameViewHolder vh = new GameViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        holder.mTextView.setText(games.get(position).getTitle());
        holder.bind(position, games.get(position), listener);
    }

    public void add(Game game) {
        allGames.add(game);

        if (game.getPlaying()) {
            games.add(game);
            notifyItemInserted(games.size());
        }
    }

    public void set(int position, final Game game) {
        Game old = games.get(position);
        allGames.set(position, game);

        if (!old.getPlaying() && filteredByPlaying) {
            games.add(position, game);
            notifyItemInserted(position);
        } else if (!game.getPlaying() && filteredByPlaying) {
            games.remove(position);
            notifyItemRemoved(position);
        } else {
            games.set(position, game);
            notifyItemChanged(position);
        }
    }

    public void remove(int position) {
        games.remove(position);
        allGames.remove(position);
        notifyItemRemoved(position);
    }

    public void filterByPlaying(boolean activated) {
        if (activated != filteredByPlaying) {
            if (activated) {
                removeNotPlayingGames();
            } else {
                games = new ArrayList<>(allGames);
                notifyItemRangeChanged(0, games.size());
            }
        }

        filteredByPlaying = activated;

    }

    public boolean isFilteredByPlaying() {
        return filteredByPlaying;
    }

    private void removeNotPlayingGames() {
        for (int i = 0; i < games.size(); i++)
            if (!games.get(i).getPlaying()) {
                games.remove(i);
                notifyItemRemoved(i);
                --i;
            }
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public interface OnGameClick {
        void onGameClick(Game game);
        void onGameLongClick(int position, Game game);
        void onGameEditClick(int position, Game game);
    }
}
