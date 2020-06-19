//Terry Tran
//terry.h.tran@gmail.com

package com.tt.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFragment extends Fragment {
    AutoCompleteTextView myAutoComplete;
    ArrayAdapter<String> myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.add, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        myAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.myautocomplete_add);
        String [] item_array = new String [] {""};
        myAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_dropdown_item_1line, item_array);
        myAutoComplete.setAdapter(myAdapter);
        final Button showItem = (Button) view.findViewById(R.id.show_item_button_add);
        showItem.setEnabled(false);
        mainActivity.addMode = true;
        mainActivity.catalogMode = false;

        myAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence userInput, int start, int before, int count) {
                mainActivity.items = mainActivity.databaseH.readAutoComplete(userInput.toString());
                myAdapter.notifyDataSetChanged();
                myAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_dropdown_item_1line, mainActivity.items);
                myAutoComplete.setAdapter(myAdapter);
                showItem.setEnabled(false);
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

        myAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mainActivity.hideKeyboard();
                showItem.setEnabled(true);
            }
        });

        Button listAllButton = (Button) view.findViewById(R.id.list_all_button_add);
        listAllButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mainActivity.hideKeyboard();
                mainActivity.toolbar.setTitle("Add - List All");
                ListAllFragment listAllFragment = new ListAllFragment();
                listAllFragment.setArguments(getActivity().getIntent().getExtras());
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, listAllFragment).addToBackStack(null).commit();
            }
        });

        Button showItemButton = (Button) view.findViewById(R.id.show_item_button_add);
        showItemButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText itemNameEditText = (EditText) mainActivity.findViewById(R.id.myautocomplete_add);
                String itemName = itemNameEditText.getText().toString();
                if (!mainActivity.databaseH.checkIfExists(itemName) || itemName.isEmpty()) {
                    String msg = "Not Found.  Please Add New Item First.";
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                } else {
                    mainActivity.toolbar.setTitle("Add - Item");
                    mainActivity.itemName = itemName;
                    LogItemFragment logItemFragment = new LogItemFragment();
                    logItemFragment.setArguments(getActivity().getIntent().getExtras());
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, logItemFragment).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}