//Terry Tran
//terry.h.tran@gmail.com

package com.tt.supermacros;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EntryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.entry_item, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        MyEntry myEntry = mainActivity.databaseH.getEntry(mainActivity.entryName, mainActivity.entryTime);

        TextView itemNameTextView = (TextView) view.findViewById(R.id.name_text_view);
        itemNameTextView.setText(mainActivity.entryName, TextView.BufferType.EDITABLE);

        TextView timeTextView = (TextView) view.findViewById(R.id.entry_time_text_view);
        timeTextView.setText(mainActivity.entryTime, TextView.BufferType.EDITABLE);

        TextView caloriesTextView = (TextView) view.findViewById(R.id.calories_text_view);
        caloriesTextView.setText(caloriesTextView.getText() + myEntry.calories.toString(), TextView.BufferType.EDITABLE);

        TextView fatsTextView = (TextView) view.findViewById(R.id.fats_text_view);
        fatsTextView.setText(fatsTextView.getText() + myEntry.fats.toString(), TextView.BufferType.EDITABLE);

        TextView carbsTextView = (TextView) view.findViewById(R.id.carbs_text_view);
        carbsTextView.setText(carbsTextView.getText() + myEntry.carbs.toString(), TextView.BufferType.EDITABLE);

        TextView proteinTextView = (TextView) view.findViewById(R.id.protein_text_view);
        proteinTextView.setText(proteinTextView.getText() + myEntry.protein.toString(), TextView.BufferType.EDITABLE);

        TextView servings = (TextView) view.findViewById(R.id.servings_text_view);
        servings.setText(servings.getText() + myEntry.servings.toString(), TextView.BufferType.EDITABLE);

        Button deleteEntryButton = (Button) view.findViewById(R.id.delete_entry_button);
        deleteEntryButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(mainActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.databaseH.deleteEntry(mainActivity.entryName, mainActivity.entryTime);
                                ListAllEntriesFragment listAllEntriesFragment = new ListAllEntriesFragment();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, listAllEntriesFragment).addToBackStack(null).commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        return view;
    }
}