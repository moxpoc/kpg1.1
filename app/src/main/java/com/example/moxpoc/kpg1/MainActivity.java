package com.example.moxpoc.kpg1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<Player> players = new ArrayList();
    ListView playerList;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText firstName = (EditText)findViewById(R.id.firstName);
        final EditText secondName = (EditText)findViewById(R.id.secondName);
        Button addBtn = (Button)findViewById(R.id.addBtn);
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
                playerList.smoothScrollToPosition(0);
            }
        });


    }
}
