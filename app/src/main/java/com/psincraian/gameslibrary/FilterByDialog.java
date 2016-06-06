package com.psincraian.gameslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FilterByDialog extends DialogFragment {

    private Activity activity;
    private String[] items;
    private boolean[] checked;
    private OnItemClick click;
    private List<String> mSelectedItems;

    public FilterByDialog(Activity activity, String[] items, boolean[] checked, OnItemClick listener) {
        this.activity = activity;
        this.items = items;
        this.checked = checked;
        this.click = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSelectedItems = new ArrayList();  // Where we track the selected items
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set the dialog title
        builder.setTitle(R.string.filter)
                .setMultiChoiceItems(items, checked,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(items[which]);
                                } else if (mSelectedItems.contains(items[which])) {
                                    mSelectedItems.remove(items[which]);
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        click.onItemClick(mSelectedItems);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public interface OnItemClick {
        void onItemClick(List<String> selected);
    }
}