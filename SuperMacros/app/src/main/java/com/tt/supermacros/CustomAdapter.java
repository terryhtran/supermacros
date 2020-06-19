//Terry Tran
//terry.h.tran@gmail.com

package com.tt.supermacros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String [] entries;
    String [] times;

    private static LayoutInflater inflater=null;
    public CustomAdapter(MainActivity mainActivity, String[] entriesStrings, String[] timesStrings) {
        // TODO Auto-generated constructor stub
        entries = entriesStrings;
        context = mainActivity;
        times = timesStrings;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return entries.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Entry
    {
        TextView entry;
        TextView time;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Entry entry = new Entry();
        View rowView;
        rowView = inflater.inflate(R.layout.list_row, null);
        entry.entry = (TextView) rowView.findViewById(R.id.entry_text_view);
        entry.time = (TextView) rowView.findViewById(R.id.time_text_view);
        entry.entry.setText(entries[position]);
        entry.time.setText(times[position]);
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainActivity mainActivity = ((MainActivity) context);
                mainActivity.entryName = entries[position];
                mainActivity.entryTime = times[position];
                EntryFragment entryFragment = new EntryFragment();
                mainActivity.getFragmentManager().beginTransaction().replace(R.id.fragment_container, entryFragment).addToBackStack(null).commit();
            }
        });

        return rowView;
    }

}