package com.example.moxpoc.kpg1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

public class ShuffleDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Shuffle Shuffle")
                .setView(R.layout.shuffle_dialog)
                .setMessage("Заблокировтаь и перемешать?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)getActivity()).okClicked();
                    }
                })
                .setNegativeButton("Cancel", null   );
        return builder.create();
    }
}
