package com.mlieou.yaara.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.mlieou.yaara.R;

/**
 * Created by mlieou on 1/11/18.
 */

public class SimpleNewTaskFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.simple_new_task_title)
                .setView(R.layout.fragment_dialog_simple_new_task);
        builder.setPositiveButton(R.string.add, (dialogInterface, i) -> {

        });
        builder.setNeutralButton(R.string.advance, (dialogInterface, i) -> {

        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            getDialog().cancel();
        });
        return builder.create();
    }

}
