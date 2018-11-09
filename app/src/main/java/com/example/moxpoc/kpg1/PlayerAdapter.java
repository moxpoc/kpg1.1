package com.example.moxpoc.kpg1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    public static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";
    public static final String ACTION_GAMES = "android.intent.action.START_IN_TABLES_PLAYERS";


    private LayoutInflater inflater;
    private List<Player> playerList;
    private List<String> games;
    private String table;

    PlayerAdapter(Context context, List<Player> players, String table){
        this.playerList = players;
        this.inflater = LayoutInflater.from(context);
        this.table = table;
    }

    PlayerAdapter(Context context, List<String> games){
        this.games = games;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlayerAdapter.ViewHolder holder, int position){
        if(playerList != null){
            Player player = playerList.get(position);
            holder.firstNameView.setText(player.getFirstName());
            holder.secondNameView.setText(player.getSecondName());

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ACTION);
                    intent.putExtra("position", holder.getAdapterPosition());
                    intent.putExtra("table", table);
                    inflater.getContext().startActivity(intent);
                }
            });
        }
        if(games != null){
            final String game = games.get(position);
            holder.firstNameView.setText(game);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ACTION_GAMES);
                    intent.putExtra("table", game);
                    inflater.getContext().startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount(){
        if(playerList != null) {
            return playerList.size();
        } else {
            return games.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        final TextView firstNameView, secondNameView;
        protected LinearLayout linearLayout = null;
        ViewHolder(View view){
            super(view);
            linearLayout = view.findViewById(R.id.linLayout);
            firstNameView = view.findViewById(R.id.firstNameView);
            secondNameView = view.findViewById(R.id.secondNameView);
        }
    }

}
