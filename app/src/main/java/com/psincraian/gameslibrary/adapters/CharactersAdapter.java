package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by petrusqui on 2/06/16.
 */
public class CharactersAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    public static final int ORDER_BY_NAME = 1;
    public static final int ORDER_BY_LEVEL = 2;

    private List<Character> characters;
    private OnCharacterClick listener;
    private int order;

    public CharactersAdapter(List<Character> characters, OnCharacterClick listener) {
        this.listener = listener;
        this.characters = characters;
        order = ORDER_BY_NAME;
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
        holder.level.setText(String.valueOf(characters.get(position).getLevel()));
        holder.bind(position, characters.get(position), listener);
    }

    public void add(Character item) {
        characters.add(item);
        notifyItemInserted(characters.size());
    }

    public void edit(Character item, int position) {
        characters.set(position, item);
        notifyItemChanged(position);
    }

    public void remove(int position) {
        characters.remove(position);
        notifyItemRemoved(position);
    }

    public void orderBy(int order) {
        if (this.order != order) {
            switch (order) {
                case ORDER_BY_NAME:
                case ORDER_BY_LEVEL:
                    this.order = order;
                    break;
            }

            order();
        }

        notifyItemRangeChanged(0, characters.size());
    }

    private void order() {
        if (this.order == ORDER_BY_NAME) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character character, Character t1) {
                    return character.getName().compareTo(t1.getName());
                }
            });
        } else if (this.order == ORDER_BY_LEVEL) {
            Collections.sort(characters, new Comparator<Character>() {
                @Override
                public int compare(Character character, Character t1) {
                    return character.getLevel() - t1.getLevel();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public interface OnCharacterClick {
        void onCharacterClick(int position, Character character);
        void onCharacterLongClick(int position, Character character);
    }
}
