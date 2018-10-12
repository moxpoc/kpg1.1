package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateGameActivity extends AppCompatActivity {
    Button applyNameGameButton;
    EditText nameGame;
    private DatabaseAdapter adapter;

    public final static String NEW_ACTION = "android.intent.action.START_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        applyNameGameButton = (Button)findViewById(R.id.applyNameGameBtn);
        nameGame = (EditText)findViewById(R.id.nameGame);
        String title = nameGame.getText().toString();
        applyNameGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NEW_ACTION);
                String title = nameGame.getText().toString();
                adapter = new DatabaseAdapter(CreateGameActivity.this, title);
                createGame(nameGame.getText().toString());
                intent.putExtra("nameGame", title);
                startActivity(intent);
            }
        });
    }

    public void createGame(String title){
        adapter.open();
        adapter.addGame();
        adapter.close();

    }
}
