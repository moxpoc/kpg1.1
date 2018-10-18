package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {
    Button newGameButton, myTargetsButton, myGamesButton;
    public final static String NAME_ACTION = "android.intent.action.START_CREATE_GAME_ACTIVITY";
    public static final String MY_GAME_ACTION = "android.intent.action.START_MY_GAMES_ACTIVITY";
    public static final String MY_TARGET_ACTION = "android.intent.action.START_MY_TARGETS_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        newGameButton = (Button)findViewById(R.id.newGameBtn);
        myTargetsButton = (Button)findViewById(R.id.myTargetsBtn);
        myGamesButton = (Button)findViewById(R.id.myGamesBtn);


        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NAME_ACTION);
                intent.putExtra("key", NAME_ACTION);
                startActivity(intent);
            }
        });

        myTargetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_TARGET_ACTION);
                String targets = "target";
                intent.putExtra("act", targets);
                startActivity(intent);
            }
        });

        myGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MY_GAME_ACTION);
                String games = "game";
                intent.putExtra("act", games);
                startActivity(intent);
            }
        });
    }
}
