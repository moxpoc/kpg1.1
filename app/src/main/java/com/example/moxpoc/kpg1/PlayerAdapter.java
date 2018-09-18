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
    private LayoutInflater inlater;
    private int layout;
    private ArrayList<Player> playerList;

    PlayerAdapter(Context context, int resource, ArrayList<Player> players){
        super(context,resource,players);
        this.playerList = players;
        this.layout = resource;
        this.inlater = LayoutInflater.from(context);
    }

    private class ViewHolder{
        final Button addButton, lockButton;
        final EditText firstNameView, secondNameView;
        ViewHolder(View view){
            addButton = (Button)view.findViewById(R.id.addButton);
            lockButton = (Button)view.findViewById(R.id.lockButton);
            firstNameView = (EditText) view.findViewById(R.id.firstNameView);
            secondNameView = (EditText)view.findViewById(R.id.secondNameView);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = inlater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Player player = playerList.get(position);
        final boolean check = viewHolder.firstNameView.getDefaultFocusHighlightEnabled();
        viewHolder.firstNameView.setText(player.getFirstName());
        viewHolder.secondNameView.setText(player.getSecondName());

        viewHolder.lockButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               if (check == true){
                   viewHolder.firstNameView.setEnabled(false);
                   viewHolder.secondNameView.setEnabled(false);
               }
               else {
                   viewHolder.firstNameView.setEnabled(true);
                   viewHolder.secondNameView.setEnabled(true);
               }
               }
            }
        });
    }
}
