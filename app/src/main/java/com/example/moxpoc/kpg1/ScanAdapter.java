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

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ViewHolder> {
    public static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";
    public static final String ACTION_GAMES = "android.intent.action.START_IN_TABLES_PLAYERS";


    private LayoutInflater inflater;
    private List<Player> playerList;
    private List<String> games;
    private String table;

    ScanAdapter(Context context, List<Player> players, String table){
        this.playerList = players;
        this.inflater = LayoutInflater.from(context);
        this.table = table;
    }

    ScanAdapter(Context context, List<String> games){
        this.games = games;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ScanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScanAdapter.ViewHolder holder, int position){
            final String game = games.get(position);
            holder.firstNameView.setText(game);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(inflater.getContext(), Target2Activity.class);
                    intent.putExtra("table", game);
                    inflater.getContext().startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount(){
        return games.size();
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
