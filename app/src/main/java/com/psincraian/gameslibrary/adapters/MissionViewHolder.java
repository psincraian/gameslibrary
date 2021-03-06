package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Character;
import com.psincraian.gameslibrary.models.Mission;

/**
 * Created by petrusqui on 2/06/16.
 */
public class MissionViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView avatar;
    public TextView level;


    public MissionViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        level = (TextView) view.findViewById(R.id.level);
    }

    public void bind(final int position, final Mission character, final MissionAdapter.OnCharacterClick listener) {
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
