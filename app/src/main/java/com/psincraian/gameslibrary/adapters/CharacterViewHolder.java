package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.psincraian.gameslibrary.models.Character;

/**
 * Created by petrusqui on 2/06/16.
 */
public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;


    public CharacterViewHolder(View view) {
        super(view);
        mTextView = (TextView) view.findViewById(android.R.id.text1);
    }

    public void bind(final Character character, final CharactersAdapter.OnCharacterClick listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onCharacterClick(character);
            }
        });
    }
}
