package com.example.moxpoc.kpg1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.EditText;

public class ShuffleDialog extends DialogFragment {

    public interface DialogListener{
        void onFinishDialog(String first, String second);
    }

    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.shuffle_dialog, null );
        builder.setView(view);
        Button okBtn = view.findViewById(R.id.okBtn);
        Button decBtn = view.findViewById(R.id.decBtn);
        final EditText firstName = view.findViewById(R.id.firstName);
        final EditText secondName = view.findViewById(R.id.secondName);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFinishDialog(firstName.getText().toString(), secondName.getText().toString());
                ShuffleDialog.this.dismiss();
            }
        });
        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShuffleDialog.this.dismiss();
            }
        });
        return builder.create();


    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            // Instantiate the EditNameDialogListener so we can send events to the host
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement EditNameDialogListener");
        }
    }

}
