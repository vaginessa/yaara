package com.mlieou.yaara.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mlieou.yaara.R;
import com.mlieou.yaara.activity.MainActivity;

/**
 * Created by mlieou on 1/11/18.
 */

public class SimpleNewTaskFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MainActivity activity = (MainActivity) getActivity();

        View view = activity.getLayoutInflater().inflate(R.layout.fragment_dialog_simple_new_task, null);
        EditText url = view.findViewById(R.id.et_task_link);
        builder.setTitle(R.string.simple_new_task_title).setView(view);
        builder.setPositiveButton(R.string.add, (dialogInterface, i) -> {
            activity.sumbitTask(url.getText().toString());
            getDialog().dismiss();
        });
//        builder.setNeutralButton(R.string.advance, (dialogInterface, i) -> {
//
//        });
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            getDialog().cancel();
        });
        return builder.create();
    }

}
