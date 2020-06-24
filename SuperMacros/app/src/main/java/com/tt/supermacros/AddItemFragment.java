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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemFragment extends Fragment {
    String itemName;
    Integer itemCalories;
    Integer itemFats;
    Integer itemCarbs;
    Integer itemProtein;
    Integer itemServingSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.add_item, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity.itemName != "") {

            MyObject myObject = mainActivity.databaseH.getObject(mainActivity.itemName);

            EditText itemNameEditText = (EditText) view.findViewById(R.id.item_name_input);
            itemNameEditText.setText(mainActivity.itemName, TextView.BufferType.EDITABLE);

            EditText itemCaloriesEditText = (EditText) view.findViewById(R.id.calories_input);
            Integer calories = myObject.calories;
            itemCaloriesEditText.setText(calories.toString(), TextView.BufferType.EDITABLE);

            EditText itemFatsEditText = (EditText) view.findViewById(R.id.fats_input);
            Integer fats = myObject.fats;
            itemFatsEditText.setText(fats.toString(), TextView.BufferType.EDITABLE);

            EditText itemCarbsEditText = (EditText) view.findViewById(R.id.carbs_input);
            Integer carbs = myObject.carbs;
            itemCarbsEditText.setText(carbs.toString(), TextView.BufferType.EDITABLE);

            EditText itemProteinEditText = (EditText) view.findViewById(R.id.protein_input);
            Integer protein = myObject.protein;
            itemProteinEditText.setText(protein.toString(), TextView.BufferType.EDITABLE);

            EditText itemServingSizeEditText = (EditText) view.findViewById(R.id.serving_size_input);
            Integer servingSize = myObject.servingSize;
            itemServingSizeEditText.setText(servingSize.toString(), TextView.BufferType.EDITABLE);
        }

        Button deleteButton = (Button) view.findViewById(R.id.delete_item_button);
        Button addItemButton = (Button) view.findViewById(R.id.add_to_log_button);

        if (mainActivity.addItem == true) {
            addItemButton.setText("Add");
            deleteButton.setEnabled(false);
        } else {
            addItemButton.setText("Update");
        }

        addItemButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String title;
                if (mainActivity.addItem == true) {
                    title = "Add";
                } else {
                    title = "Update";
                }
                mainActivity.toolbar.setTitle("Your Database - Item");

                EditText itemNameEditText = (EditText) mainActivity.findViewById(R.id.item_name_input);
                itemName = itemNameEditText.getText().toString();

                EditText itemCaloriesEditText = (EditText) mainActivity.findViewById(R.id.calories_input);
                if (itemCaloriesEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                itemCalories = Integer.parseInt(itemCaloriesEditText.getText().toString());

                EditText itemFatsEditText = (EditText) mainActivity.findViewById(R.id.fats_input);
                if (itemFatsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                itemFats = Integer.parseInt(itemFatsEditText.getText().toString());

                EditText itemCarbsEditText = (EditText) mainActivity.findViewById(R.id.carbs_input);
                if (itemCarbsEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                itemCarbs = Integer.parseInt(itemCarbsEditText.getText().toString());

                EditText itemProteinEditText = (EditText) mainActivity.findViewById(R.id.protein_input);
                if (itemProteinEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                itemProtein = Integer.parseInt(itemProteinEditText.getText().toString());

                EditText itemServingSizeEditText = (EditText) mainActivity.findViewById(R.id.serving_size_input);
                if (itemServingSizeEditText.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                itemServingSize = Integer.parseInt(itemServingSizeEditText.getText().toString());

                if (itemName.isEmpty() || itemCalories < 0 || itemFats < 0 || itemCarbs < 0 || itemProtein < 0 || itemServingSize < 0 ) {
                    Toast.makeText(getActivity(), "Invalid Input", Toast.LENGTH_LONG).show();
                    return;
                }
                if (itemName.length() > 25) {
                    Toast.makeText(getActivity(), "Item Name Too Long", Toast.LENGTH_LONG).show();
                    return;
                }
                new AlertDialog.Builder(mainActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(title)
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyObject myObject = new MyObject(itemName, itemCalories, itemFats, itemCarbs, itemProtein, itemServingSize);
                                mainActivity.databaseH.addUpdateObject(myObject, mainActivity);
                                mainActivity.catalog();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText itemNameEditText = (EditText) mainActivity.findViewById(R.id.item_name_input);
                itemName = itemNameEditText.getText().toString();
                new AlertDialog.Builder(mainActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mainActivity.databaseH.deleteObject(itemName);
                                Toast.makeText(getActivity(), "Item Successfully Deleted.", Toast.LENGTH_LONG).show();
                                mainActivity.catalog();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        return view;
    }
}