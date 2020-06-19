//Terry Tran
//terry.h.tran@gmail.com

package com.tt.supermacros;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

public class CalendarFragment extends Fragment {
    String date = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        final View view = inflater.inflate(R.layout.calendar, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();
        CalendarView simpleCalendarView;
        simpleCalendarView = (CalendarView) view.findViewById(R.id.simple_calendar_view);

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = year + "-" + month  + "-" + dayOfMonth;
            }
        });

        Button viewDateButton = (Button) view.findViewById(R.id.view_date_button);
        viewDateButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (date == "") {
                    date = mainActivity.date;
                }
                if (mainActivity.pro == false) {
                    mainActivity.showProMsg();
                } else {
                    mainActivity.date = date;
                    NotebookFragment notebookFragment = new NotebookFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, notebookFragment).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}