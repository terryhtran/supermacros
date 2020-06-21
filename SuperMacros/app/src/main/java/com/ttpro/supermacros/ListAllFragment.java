//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListAllFragment extends Fragment {
    ArrayAdapter<String> myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.list_all, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        final ListView myListView = (ListView) view.findViewById(R.id.list_all);

        mainActivity.items = mainActivity.databaseH.readAll();
        myAdapter = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_list_item_1, mainActivity.items);
        myListView.setAdapter(myAdapter);

        myListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = myAdapter.getItem(position).toString();
                mainActivity.itemName = item;
                if (mainActivity.catalogMode) {
                    mainActivity.toolbar.setTitle("Your Database - Item");
                    AddItemFragment addItemFragment = new AddItemFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, addItemFragment).addToBackStack(null).commit();
                }
                if (mainActivity.addMode) {
                    mainActivity.toolbar.setTitle("Add - Item");
                    LogItemFragment logItemFragment = new LogItemFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, logItemFragment).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}