package com.str.escapecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater lInflater;
    private List<MainMenuRoomObject> listStorage;
    public CustomAdapter(Context context, List<MainMenuRoomObject> customizedListView) {
        lInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }
    Integer[] imageId = {
            R.drawable.walking,
            R.drawable.la_mission,
            R.drawable.got,
    };

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.list, parent, false);
            listViewHolder.roomName = (TextView)convertView.findViewById(R.id.textViewRoomName);
            listViewHolder.roomStatus = (TextView)convertView.findViewById(R.id.textViewRoomStatus);
            listViewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageViewRoom);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }

        listViewHolder.roomName.setText(listStorage.get(position).getRoomName());
        listViewHolder.roomStatus.setText("Status: " + listStorage.get(position).getRoomStatus());
        listViewHolder.imageView.setImageResource(imageId[position]);
        return convertView;
    }

    static class ViewHolder{
        TextView roomName;
        TextView roomStatus;
        ImageView imageView;
    }
}