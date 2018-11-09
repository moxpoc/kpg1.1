package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "com.moxpoc.action.START_TARGET_ACTIVITY";
    List<Player> players;
    RecyclerView playerList;
    List<Player> shuffleList;
    PlayerAdapter adapter;

    EditText firstName, secondName;
    Button addBtn,lockBtn;

    private DatabaseAdapter dbAdapter;
    int pos = 0;
    String table;
    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle arguments = getIntent().getExtras();
        table = arguments.getString("nameGame");

        firstName = (EditText)findViewById(R.id.firstName);
        secondName = (EditText)findViewById(R.id.secondName);
        addBtn = (Button)findViewById(R.id.addBtn);
        lockBtn = (Button)findViewById(R.id.lockBtn);

        dbAdapter = new DatabaseAdapter(this, table, "kpg.db");
        dbAdapter.open();

        playerList = findViewById(R.id.playerList);
        //final PlayerAdapter adapter = new PlayerAdapter(this, R.layout.list_item, players);
        //playerList.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player player =  new Player(pos, firstName.getText().toString(), secondName.getText().toString());
                dbAdapter.insertPlayer(player);
                players = dbAdapter.getPlayers();

                adapter = new PlayerAdapter(MainActivity.this, players, table);
                        //dbAdapter.close();

                playerList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                firstName.setText(null);
                secondName.setText(null);
                firstName.requestFocus();
                pos = players.size();


            }
        });

        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShuffleDialog dialog = new ShuffleDialog();
                dialog.show(getFragmentManager(), "lock");
            }
        });


        /*playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("position", position);
                intent.putExtra("table", table);
                startActivity(intent);
            }
        });*/

    }


    public void okClicked(){
        shuffleList = fisherArray(players);
        for(int i = 0 ; i < shuffleList.size(); i++)
        {
            Player target = shuffleList.get(i);
            dbAdapter.insertTarget(target,(i+1));
        }
        firstName.setVisibility(View.INVISIBLE);
        secondName.setVisibility(View.INVISIBLE);
        dbAdapter.close();
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


}
