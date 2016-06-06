package com.psincraian.gameslibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by petrusqui on 6/06/16.
 */
@SuppressLint("ValidFragment")
public class ConfirmationDialog extends DialogFragment {

    private ConfirmationInterface listener;
    private Activity activity;

    public ConfirmationDialog(Activity activity, ConfirmationInterface confirmationDialog) {
        this.listener = confirmationDialog;
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.do_you_really_want_to_delete)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.yes();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }



    public interface ConfirmationInterface {
        void yes();
        void cancel();
    }
}
