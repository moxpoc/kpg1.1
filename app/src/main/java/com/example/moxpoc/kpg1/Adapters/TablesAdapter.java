package com.example.moxpoc.kpg1.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.moxpoc.kpg1.MainRvHolder;
import com.example.moxpoc.kpg1.R;

import java.util.List;

public class TablesAdapter extends RecyclerView.Adapter<MainRvHolder> {
    private LayoutInflater inflater;
    private List<String> games;
    private static final String ACTION_GAMES = "android.intent.action.START_IN_TABLES_PLAYERS";

    public TablesAdapter(Context context, List<String> games){
        this.games = games;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MainRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MainRvHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainRvHolder holder, int position){
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
        return games.size();
    }

    public void dropTable(int position){
        String game = games.get(position);

        DatabaseAdapter dbAdapter = new DatabaseAdapter(inflater.getContext(), "kpg.db");
        dbAdapter.open();
        games.remove(position);
        dbAdapter.dropTable(game);
        dbAdapter.close();
        this.notifyItemRemoved(position);
    }


}
