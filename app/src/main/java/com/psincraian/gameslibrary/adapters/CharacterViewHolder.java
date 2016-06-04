package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Character;

/**
 * Created by petrusqui on 2/06/16.
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView avatar;


    public CharacterViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        avatar = (ImageView) view.findViewById(R.id.avatar);
    }

    public void bind(final int position, final Character character, final CharactersAdapter.OnCharacterClick listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onCharacterClick(position, character);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onCharacterLongClick(position, character);
                return true;
            }
        });
    }
}
