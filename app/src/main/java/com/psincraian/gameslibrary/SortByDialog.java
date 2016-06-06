package com.psincraian.gameslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.psincraian.gameslibrary.R;

@SuppressLint("ValidFragment")
public class SortByDialog extends DialogFragment {

    private Activity activity;
    private String[] items;
    private OnItemClick click;

    public SortByDialog(Activity activity, String[] items, OnItemClick listener) {
        this.activity = activity;
        this.items = items;
        this.click = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.sort_by)
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        click.onItemClick(items[which]);
                    }
                });
        return builder.create();
    }

    public interface OnItemClick {
        void onItemClick(String item);
    }
}