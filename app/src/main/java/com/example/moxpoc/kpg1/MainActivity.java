package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final static String nameVariableKey = "NAME_VARIABLE";
    ArrayList<Player> players = new ArrayList();
    ListView playerList;
    ArrayList<Player> shuffleList;
    Intent intent;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText firstName = (EditText)findViewById(R.id.firstName);
        final EditText secondName = (EditText)findViewById(R.id.secondName);
        Button addBtn = (Button)findViewById(R.id.addBtn);
        final Button shuffleBtn = (Button)findViewById(R.id.shuffleBtn);
        playerList = (ListView)findViewById(R.id.playerList);
        final PlayerAdapter adapter = new PlayerAdapter(this, R.layout.list_item, players);
        playerList.setAdapter(adapter);


        pos = playerList.getCount();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText() != null || secondName.getText() != null ) {
                    players.add(new Player(firstName.getText().toString(), secondName.getText().toString()));
                }
                adapter.notifyDataSetChanged();
                firstName.setText(null);
                secondName.setText(null);
                firstName.requestFocus();
                playerList.smoothScrollToPosition(0);
            }
        });

        shuffleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shuffleList = fisherArray(players);

            }
        });

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(view.getContext(), TargetActivity.class);
                Player tmp;
                tmp = shuffleList.get(position);
                String name = tmp.getFirstName();
                String family = tmp.getSecondName();
                intent.putExtra("fName", name);
                intent.putExtra("sName", family);
            }
        });

    }

    static ArrayList fisherArray(ArrayList array){
        Random rnd = new Random();
        for(int i = array.size() - 1; i>=1; i-- )
        {
            int j = rnd.nextInt(i+1);
            Object tmp = array.get(j);
            array.set(j,array.get(i));
            array.set(i,tmp);

        }
        array.set(array.size(), array.get(0));
        return array;
    }

}
