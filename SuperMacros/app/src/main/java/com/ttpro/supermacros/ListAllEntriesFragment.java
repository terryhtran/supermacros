//Terry Tran
//terry.h.tran@gmail.com

package com.ttpro.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListAllEntriesFragment extends Fragment {
    ListView myListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.list_all, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        myListView = (ListView) view.findViewById(R.id.list_all);
        mainActivity.items = mainActivity.databaseH.readAllEntries(mainActivity.date);

        String [] entries = new String[mainActivity.items.length];
        String [] times = new String[mainActivity.items.length];

        for (int i = 0; i < mainActivity.items.length; i++) {
            entries[i] = mainActivity.items[i].split("_")[0];
            times[i] = mainActivity.items[i].split("_")[1];
        }
        myListView.setAdapter(new CustomAdapter(mainActivity, entries, times));

        return view;
    }
}