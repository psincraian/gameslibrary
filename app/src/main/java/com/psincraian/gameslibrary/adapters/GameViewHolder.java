package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Game;

/**
 * Created by petrusqui on 2/06/16.
 */
public class GameViewHolder extends RecyclerView.ViewHolder {

    private static String CLASS_NAME=  GameViewHolder.class.getName();

    public TextView mTextView;
    private ImageView edit;

    public GameViewHolder(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(R.id.game_title);
        edit = (ImageView) view.findViewById(R.id.button_edit);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGameEditClick(position, game);
            }
        });
    }
}