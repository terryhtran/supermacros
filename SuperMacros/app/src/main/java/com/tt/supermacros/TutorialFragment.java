//Terry Tran
//terry.h.tran@gmail.com

package com.tt.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class TutorialFragment extends Fragment {
    AutoCompleteTextView myAutoComplete;
    ArrayAdapter<String> myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.tutorial, container, false);
        return view;
    }
}