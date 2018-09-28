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
    public static final String ACTION = "com.moxpoc.START_TARGET_ACTIVITY";
    ArrayList<Player> players = new ArrayList();
    ListView playerList;
    ArrayList<Player> shuffleList;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText firstName = (EditText)findViewById(R.id.firstName);
        final EditText secondName = (EditText)findViewById(R.id.secondName);
        final Button addBtn = (Button)findViewById(R.id.addBtn);
        final Button lockBtn = (Button)findViewById(R.id.lockBtn);
        playerList = (ListView)findViewById(R.id.playerList);
        final String addtext = getResources().getString(R.string.addplayer);
        final PlayerAdapter adapter = new PlayerAdapter(this, R.layout.list_item, players);
        playerList.setAdapter(adapter);
        players.add(new Player("1","1"));
        players.add(new Player("2","2"));
        players.add(new Player("3","3"));
        players.add(new Player("4","4"));
        pos = playerList.getCount();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addBtn.getText() == addtext) {
                    if (firstName.getText().length() != 0 || secondName.getText().length() != 0) {
                        players.add(new Player(firstName.getText().toString(), secondName.getText().toString()));
                    }
                    adapter.notifyDataSetChanged();
                    firstName.setText(null);
                    secondName.setText(null);
                    firstName.requestFocus();
                }
                if (addBtn.getText()==getResources().getString(R.string.shuffle));
                {
                    shuffleList = fisherArray(players);
                    addBtn.setClickable(false);
                }
            }
        });

        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lockBtn.getText().toString() == "Lock")
                {
                    addBtn.setText("Shuffle");
                    addBtn.setClickable(true);
                    lockBtn.setText(R.string.unlock);
                }
                if (lockBtn.getText() == "Unlock")
                {
                    addBtn.setText("Add player");
                    addBtn.setClickable(true);
                    lockBtn.setText(R.string.lock);
                }
            }
        });

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ACTION);
                intent.putExtra("obj", shuffleList.get(position));
                startActivity(intent);
                /*Player tmp;
                tmp = shuffleList.get(position);
                String name = tmp.getFirstName();
                String family = tmp.getSecondName();
                intent.putExtra("fName", name);
                intent.putExtra("sName", family);*/

            }
        });

    }

    static ArrayList fisherArray(ArrayList array){
        Random rnd = new Random();
        for(int i = array.size()-1; i>=1; i-- )
        {
            int j;
            if (i==1)
            {
                j = 0;
            }
            else {
                j = rnd.nextInt(i - 1) + 1;
            }
            Object tmp = array.get(i);
            array.set(i,array.get(j));
            array.set(j,tmp);

        }
        //array.set(array.size(), array.get(0));
        return array;
    }


}
