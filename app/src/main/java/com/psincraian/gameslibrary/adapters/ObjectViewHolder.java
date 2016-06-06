package com.psincraian.gameslibrary.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.psincraian.gameslibrary.R;
import com.psincraian.gameslibrary.models.Object;

/**
 * Created by petrusqui on 2/06/16.
 */
public class ObjectViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView avatar;
    public TextView level;


    public ObjectViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.name);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        level = (TextView) view.findViewById(R.id.level);
    }

    public void bind(final int position, final Object character, final ObjectAdapter.OnCharacterClick listener) {
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
