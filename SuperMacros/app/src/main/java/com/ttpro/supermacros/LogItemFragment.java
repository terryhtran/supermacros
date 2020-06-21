//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.log_item, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        MyObject myObject = mainActivity.databaseH.getObject(mainActivity.itemName);

        TextView itemNameTextView = (TextView) view.findViewById(R.id.name_text_view);
        itemNameTextView.setText(mainActivity.itemName, TextView.BufferType.EDITABLE);

        TextView caloriesTextView = (TextView) view.findViewById(R.id.calories_text_view);
        caloriesTextView.setText(caloriesTextView.getText() + myObject.calories.toString(), TextView.BufferType.EDITABLE);

        TextView fatsTextView = (TextView) view.findViewById(R.id.fats_text_view);
        fatsTextView.setText(fatsTextView.getText() + myObject.fats.toString(), TextView.BufferType.EDITABLE);

        TextView carbsTextView = (TextView) view.findViewById(R.id.carbs_text_view);
        carbsTextView.setText(carbsTextView.getText() + myObject.carbs.toString(), TextView.BufferType.EDITABLE);

        TextView proteinTextView = (TextView) view.findViewById(R.id.protein_text_view);
        proteinTextView.setText(proteinTextView.getText() + myObject.protein.toString(), TextView.BufferType.EDITABLE);

        TextView servingSizeTextView = (TextView) view.findViewById(R.id.serving_size_text_view);
        servingSizeTextView.setText(servingSizeTextView.getText() + myObject.servingSize.toString(), TextView.BufferType.EDITABLE);

        mainActivity.itemName = "";

        final EditText servingsEditText = (EditText) view.findViewById(R.id.serving_size_input);
        servingsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence userInput, int start, int before, int count) {
                TextView itemNameTextView = (TextView) mainActivity.findViewById(R.id.name_text_view);
                String itemName = itemNameTextView.getText().toString();
                MyObject myObject = mainActivity.databaseH.getObject(itemName);

                if (userInput.toString().isEmpty()) {
                    userInput = "1";
                }

                TextView caloriesTextView = (TextView) mainActivity.findViewById(R.id.calories_text_view);
                Integer caloriesTotal = myObject.calories * Integer.parseInt(userInput.toString());
                caloriesTextView.setText("Calories: " + caloriesTotal.toString(), TextView.BufferType.EDITABLE);

                TextView fatsTextView = (TextView) mainActivity.findViewById(R.id.fats_text_view);
                Integer fatsTotal = myObject.fats * Integer.parseInt(userInput.toString());
                fatsTextView.setText("Fats: " + fatsTotal.toString(), TextView.BufferType.EDITABLE);

                TextView carbsTextView = (TextView) mainActivity.findViewById(R.id.carbs_text_view);
                Integer carbsTotal = myObject.carbs * Integer.parseInt(userInput.toString());
                carbsTextView.setText("Carbs: " + carbsTotal.toString(), TextView.BufferType.EDITABLE);

                TextView proteinTextView = (TextView) mainActivity.findViewById(R.id.protein_text_view);
                Integer proteinTotal = myObject.protein * Integer.parseInt(userInput.toString());
                proteinTextView.setText("Protein: " + proteinTotal.toString(), TextView.BufferType.EDITABLE);

                TextView servingSizeTextView = (TextView) mainActivity.findViewById(R.id.serving_size_text_view);
                Integer servingSizeTotal = myObject.servingSize * Integer.parseInt(userInput.toString());
                servingSizeTextView.setText("Serving Size: " + servingSizeTotal.toString(), TextView.BufferType.EDITABLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // stub
            }
        });

        final Button logItemButton = (Button) view.findViewById(R.id.add_to_log_button);
        logItemButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                TextView itemNameTextView = (TextView) mainActivity.findViewById(R.id.name_text_view);
                String itemName = itemNameTextView.getText().toString();
                MyEntry myEntry = new MyEntry(itemName);

                TextView caloriesTextView = (TextView) mainActivity.findViewById(R.id.calories_text_view);
                String [] calories = caloriesTextView.getText().toString().split(" ");
                myEntry.calories = Integer.parseInt(calories[1]);

                TextView fatsTextView = (TextView) mainActivity.findViewById(R.id.fats_text_view);
                String [] fats = fatsTextView.getText().toString().split(" ");
                myEntry.fats = Integer.parseInt(fats[1]);

                TextView carbsTextView = (TextView) mainActivity.findViewById(R.id.carbs_text_view);
                String [] carbs = carbsTextView.getText().toString().split(" ");
                myEntry.carbs = Integer.parseInt(carbs[1]);

                TextView proteinTextView = (TextView) mainActivity.findViewById(R.id.protein_text_view);
                String [] protein = proteinTextView.getText().toString().split(" ");
                myEntry.protein = Integer.parseInt(protein[1]);

                EditText servingsEditText = (EditText) mainActivity.findViewById(R.id.serving_size_input);
                String servings = servingsEditText.getText().toString();
                myEntry.servings = Integer.parseInt(servings);

                myEntry.divideServings();

                mainActivity.databaseH.logEntry(myEntry, mainActivity);
                mainActivity.hideKeyboard();
                mainActivity.notebook();
            }
        });

        return view;
    }
}