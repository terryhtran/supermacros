//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NotebookFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.notebook, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        String totalCalories = mainActivity.databaseH.getTotal(mainActivity.date, "Calories");
        String totalFats = mainActivity.databaseH.getTotal(mainActivity.date, "Fats");
        String totalCarbs = mainActivity.databaseH.getTotal(mainActivity.date, "Carbs");
        String totalProtein = mainActivity.databaseH.getTotal(mainActivity.date, "Protein");

        final Integer totalFCP = Integer.parseInt(totalFats) + Integer.parseInt(totalCarbs) + Integer.parseInt(totalProtein);
        final Integer fatsPercentage = (int) Math.floor((Float.parseFloat(totalFats) / totalFCP) * 100);
        final Integer carbsPercentage = (int) Math.floor((Float.parseFloat(totalCarbs) / totalFCP) * 100);
        final Integer proteinPercentage = (int) Math.floor((Float.parseFloat(totalProtein) / totalFCP) * 100);

        MyTarget myTarget = mainActivity.databaseH.getTarget(mainActivity.date);

        TextView dateTextView = (TextView) view.findViewById(R.id.date_text_view);
        dateTextView.setText("Date: " + mainActivity.date, TextView.BufferType.EDITABLE);

        TextView calories = (TextView) view.findViewById(R.id.total_calories_text_view);
        calories.setText("Calories: " + totalCalories + " / " + myTarget.calories, TextView.BufferType.EDITABLE);

        TextView fats = (TextView) view.findViewById(R.id.total_fats_text_view);
        fats.setText("Fats: " + totalFats + " / " + myTarget.fats, TextView.BufferType.EDITABLE);

        TextView carbs = (TextView) view.findViewById(R.id.total_carbs_text_view);
        carbs.setText("Carbs: " + totalCarbs + " / " + myTarget.carbs, TextView.BufferType.EDITABLE);

        TextView protein = (TextView) view.findViewById(R.id.total_protein_text_view);
        protein.setText("Protein: " + totalProtein + " / " + myTarget.protein, TextView.BufferType.EDITABLE);

        TextView carbs_percentage = (TextView) view.findViewById(R.id.carbs_percentage_text_view);
        carbs_percentage.setText(carbsPercentage.toString() + "%", TextView.BufferType.EDITABLE);

        TextView fats_percentage = (TextView) view.findViewById(R.id.fats_percentage_text_view);
        fats_percentage.setText(fatsPercentage.toString() + "%", TextView.BufferType.EDITABLE);

        TextView protein_percentage = (TextView) view.findViewById(R.id.protein_percentage_text_view);
        protein_percentage.setText(proteinPercentage.toString() + "%", TextView.BufferType.EDITABLE);

        Button entriesButton = (Button) view.findViewById(R.id.entries_button);
        entriesButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (totalFCP == 0 && fatsPercentage == 0 && carbsPercentage == 0 && proteinPercentage == 0)  {
                    Toast.makeText(mainActivity, "0 entries for today", Toast.LENGTH_LONG).show();
                } else {
                    if (mainActivity.pro == false) {
                        mainActivity.showProMsg();
                    }
                    mainActivity.toolbar.setTitle(mainActivity.date);
                    ListAllEntriesFragment listAllEntriesFragment = new ListAllEntriesFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, listAllEntriesFragment).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}