package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;

import java.util.List;

/**
 * Created by petrusqui on 2/06/16.
 */
public class CharactersAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    private List<Character> characters;
    private OnCharacterClick listener;

    public CharactersAdapter(List<Character> characters, OnCharacterClick listener) {
        this.listener = listener;
        this.characters = characters;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        CharacterViewHolder vh = new CharacterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.name.setText(characters.get(position).getName());
        holder.avatar.setImageBitmap(characters.get(position).getAvatar());
        holder.bind(characters.get(position), listener);
    }

    public void add(Character item) {
        characters.add(item);
        notifyItemInserted(characters.size());
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface OnCharacterClick {
        public void onCharacterClick(Character character);
    }
}
