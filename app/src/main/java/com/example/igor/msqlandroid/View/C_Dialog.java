package com.example.igor.msqlandroid.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.igor.msqlandroid.R;

/**
 * Created by Igor on 15/04/2018.
 */

public class C_Dialog extends AppCompatDialogFragment {
        private TextView textView;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view =inflater.inflate(R.layout.comentar_dialog,null);

        builder.setView(view)
        .setTitle("Hola")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),"lalallaa",Toast.LENGTH_SHORT).show();
                    }
                });
        //textView=(TextView)view.findViewById(R.id.textView4);
        return builder.create();
    }
}
