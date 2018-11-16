package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;
import com.example.moxpoc.kpg1.Adapters.PlayerAdapter;
import com.example.moxpoc.kpg1.Helpers.SwipeHelper;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ShuffleDialog.DialogListener {

    public static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";
    List<Player> players;
    RecyclerView playerList;
    List<Player> shuffleList;
    PlayerAdapter adapter;
    FloatingActionButton addBtn;
    ItemTouchHelper.Callback callback;
    ItemTouchHelper touchHelper;

    private DatabaseAdapter dbAdapter;
    int pos = 0;
    String table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
        table = arguments.getString("nameGame");
        addBtn = findViewById(R.id.addBtn);

        dbAdapter = new DatabaseAdapter(this, table, "kpg.db");
        dbAdapter.open();
        playerList = findViewById(R.id.playerList);
        playerList.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShuffleDialog dialog = new ShuffleDialog();
                dialog.show(getFragmentManager(), "lock");
            }
        });

        addBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                shuffleList = fisherArray(players);
                for(int i = 0 ; i < shuffleList.size(); i++)
                {
                    Player target = shuffleList.get(i);
                    dbAdapter.insertTarget(target,(i+1));
                }
                dbAdapter.close();
                Toast toast = Toast.makeText(getApplicationContext(), "Я перемешался", Toast.LENGTH_LONG);
                toast.show();
                return true;
            }
        });

    }

    static List<Player> fisherArray(List<Player> array){
        Random rnd = new Random();
        int j;
        for(int i = array.size()-1; i>=1; i-- )
        {

            j = rnd.nextInt(i);
            Player tmp = array.get(i);
            array.set(i,array.get(j));
            array.set(j,tmp);

        }
        //array.set(array.size(), array.get(0));
        return array;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity.this, StartScreenActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onFinishDialog(String first, String second){
        if(first != null && second !=null) {
            Player player = new Player(pos, first, second);
            dbAdapter.insertPlayer(player);
            players = dbAdapter.getPlayers();
            adapter = new PlayerAdapter(MainActivity.this, players, table);
            playerList.setAdapter(adapter);

            callback = new SwipeHelper(adapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(playerList);

            pos = players.size();
        }
    }

}
