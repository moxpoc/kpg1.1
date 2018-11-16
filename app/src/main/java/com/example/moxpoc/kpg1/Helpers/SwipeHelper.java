package com.example.moxpoc.kpg1.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.moxpoc.kpg1.Adapters.PlayerAdapter;
import com.example.moxpoc.kpg1.Adapters.ScanAdapter;
import com.example.moxpoc.kpg1.Adapters.TablesAdapter;

public class SwipeHelper extends ItemTouchHelper.SimpleCallback {
    private PlayerAdapter mAdapter;
    private TablesAdapter tAdapter;
    private ScanAdapter sAdapter;

    public SwipeHelper(TablesAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.END);
        this.tAdapter = adapter;
    }

    public SwipeHelper(PlayerAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.END);
        this.mAdapter = adapter;
    }
    public SwipeHelper(ScanAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.END);
        this.sAdapter = adapter;
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,@NonNull  RecyclerView.ViewHolder source,@NonNull
            RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction){
        if(mAdapter != null){
            mAdapter.deleteItem(viewHolder.getAdapterPosition());
        }
        if(tAdapter != null){
            tAdapter.dropTable(viewHolder.getAdapterPosition());
        }
        if(sAdapter != null){
            sAdapter.dropTable(viewHolder.getAdapterPosition());
        }
    }

}
