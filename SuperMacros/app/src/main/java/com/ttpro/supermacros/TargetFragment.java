//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TargetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.set_targets, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final MyTarget myTarget = mainActivity.databaseH.getTarget(mainActivity.date);

        EditText targetCaloriesEditText = (EditText) view.findViewById(R.id.target_calories_input);
        Integer calories = myTarget.calories;
        targetCaloriesEditText.setText(calories.toString(), TextView.BufferType.EDITABLE);

        EditText targetFatsEditText = (EditText) view.findViewById(R.id.target_fats_input);
        Integer fats = myTarget.fats;
        targetFatsEditText.setText(fats.toString(), TextView.BufferType.EDITABLE);

        EditText targetCarbsEditText = (EditText) view.findViewById(R.id.target_carbs_input);
        Integer carbs = myTarget.carbs;
        targetCarbsEditText.setText(carbs.toString(), TextView.BufferType.EDITABLE);

        EditText targetProteinEditText = (EditText) view.findViewById(R.id.target_protein_input);
        Integer protein = myTarget.protein;
        targetProteinEditText.setText(protein.toString(), TextView.BufferType.EDITABLE);

        Button setTargetButton = (Button) view.findViewById(R.id.set_target_button);

        setTargetButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mainActivity.hideKeyboard();
                
                final EditText targetCaloriesEditText = (EditText) mainActivity.findViewById(R.id.target_calories_input);
                if (targetCaloriesEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                final EditText targetCarbsEditText = (EditText) mainActivity.findViewById(R.id.target_carbs_input);
                if (targetCarbsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                final EditText targetFatsEditText = (EditText) mainActivity.findViewById(R.id.target_fats_input);
                if (targetFatsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                final EditText targetProteinEditText = (EditText) mainActivity.findViewById(R.id.target_protein_input);
                if (targetProteinEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                new AlertDialog.Builder(mainActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Set your targets")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myTarget.calories = Integer.parseInt(targetCaloriesEditText.getText().toString());
                                myTarget.carbs = Integer.parseInt(targetCarbsEditText.getText().toString());
                                myTarget.fats = Integer.parseInt(targetFatsEditText.getText().toString());
                                myTarget.protein = Integer.parseInt(targetProteinEditText.getText().toString());
                                mainActivity.databaseH.setTargets(myTarget, mainActivity.date);
                                Toast.makeText(getActivity(), "New Target Set Successfully!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }
}