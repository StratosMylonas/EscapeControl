package com.str.escapecontrol;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class CustomArrayAdapter implements ListAdapter {

    ArrayList<ListItem> arrayList;
    Context context;

    public CustomArrayAdapter(Context context, ArrayList<ListItem> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ListItem listItem = arrayList.get(position);

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list, null);

            TextView attribute = convertView.findViewById(R.id.attribute);
            TextView status = convertView.findViewById(R.id.status);

            attribute.setText(listItem.getAttribute());
            status.setText(listItem.getStatus());
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}
