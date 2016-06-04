package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.psincraian.gameslibrary.models.Game;

/**
 * Created by petrusqui on 2/06/16.
 */
public class GameViewHolder extends RecyclerView.ViewHolder {

    private static String CLASS_NAME=  GameViewHolder.class.getName();

    public TextView mTextView;

    public GameViewHolder(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(android.R.id.text1);
    }

    public void bind(final int position, final Game game, final GamesAdapter.OnGameClick listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onGameClick(game);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onGameLongClick(position, game);
                return false;
            }
        });
    }
}