package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Mission;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by petrusqui on 2/06/16.
 */
public class MissionAdapter extends RecyclerView.Adapter<MissionViewHolder> {

    public static final int ORDER_BY_NAME = 1;
    public static final int ORDER_BY_LEVEL = 2;

    private List<Mission> characters;
    private OnCharacterClick listener;
    private int order;

    public MissionAdapter(List<Mission> characters, OnCharacterClick listener) {
        this.listener = listener;
        this.characters = characters;
        order = ORDER_BY_NAME;
    }

    @Override
    public MissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mission, parent, false);
        MissionViewHolder vh = new MissionViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MissionViewHolder holder, int position) {
        holder.name.setText(characters.get(position).getTitle());
        holder.level.setText(String.valueOf(characters.get(position).getLevel()));
        holder.bind(position, characters.get(position), listener);
    }

    public void add(Mission item) {
        characters.add(item);
        notifyItemInserted(characters.size());
    }

    public void edit(Mission item, int position) {
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
            Collections.sort(characters, new Comparator<Mission>() {
                @Override
                public int compare(Mission character, Mission t1) {
                    return character.getTitle().compareTo(t1.getTitle());
                }
            });
        } else if (this.order == ORDER_BY_LEVEL) {
            Collections.sort(characters, new Comparator<Mission>() {
                @Override
                public int compare(Mission character, Mission t1) {
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
        void onCharacterClick(int position, Mission character);
        void onCharacterLongClick(int position, Mission character);
    }
}
