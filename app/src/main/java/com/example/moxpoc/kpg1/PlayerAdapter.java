package com.example.moxpoc.kpg1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerAdapter extends ArrayAdapter<Player> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Player> playerList;

    PlayerAdapter(Context context, int resource, ArrayList<Player> players){
        super(context,resource,players);
        this.playerList = players;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        final Button delButton;
        final TextView firstNameView, secondNameView;
        ViewHolder(View view){
            delButton = (Button)view.findViewById(R.id.delButton);
            firstNameView = (TextView) view.findViewById(R.id.firstNameView);
            secondNameView = (TextView) view.findViewById(R.id.secondNameView);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Player player = playerList.get(position);
        viewHolder.firstNameView.setText(player.getFirstName());
        viewHolder.secondNameView.setText(player.getSecondName());

        viewHolder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerList.remove(player);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
