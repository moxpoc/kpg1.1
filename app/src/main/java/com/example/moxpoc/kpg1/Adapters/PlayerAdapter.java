package com.example.moxpoc.kpg1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moxpoc.kpg1.MainRvHolder;
import com.example.moxpoc.kpg1.Player;
import com.example.moxpoc.kpg1.R;

import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<MainRvHolder> {
    private static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";


    private LayoutInflater inflater;
    private List<Player> playerList;
    private String table;

    public PlayerAdapter(Context context, List<Player> players, String table){
        this.playerList = players;
        this.inflater = LayoutInflater.from(context);
        this.table = table;
    }

    @NonNull
    @Override
    public MainRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MainRvHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MainRvHolder holder, int position){
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


    }

    @Override
    public int getItemCount(){
        return playerList.size();
    }

    public void deleteItem(int position){
        Player player = playerList.get(position);
        long id = player.getId();

        DatabaseAdapter dbAdapter = new DatabaseAdapter(inflater.getContext(), table, "kpg.db");
        dbAdapter.open();
        playerList.remove(position);
        dbAdapter.delete(id);
        dbAdapter.close();
        this.notifyItemRemoved(position);
    }


}
