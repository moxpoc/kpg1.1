package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateGameActivity extends AppCompatActivity {
    Button applyNameGameButton;
    EditText nameGame;
    private DatabaseAdapter adapter;
    String title;


    private static final String GAME_PATTERN = "[А-Яа-яёЁA-Za-z]{1,10}";
    private Pattern pattern = Pattern.compile(GAME_PATTERN);
    private Matcher matcher;
    public final static String NEW_ACTION = "android.intent.action.START_MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        final TextInputLayout nameLayout = findViewById(R.id.nameLayout);
        nameLayout.setHint("Название игры");
        applyNameGameButton = findViewById(R.id.applyNameGameBtn);
        nameGame = findViewById(R.id.nameGame);
        applyNameGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = nameGame.getText().toString();
                if(!validateGame(title)){
                    nameLayout.setError("Неправильное имя");
                    Toast toast = Toast.makeText(getApplicationContext(), "Имя должно содержать только буквы", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    nameLayout.setErrorEnabled(false);
                    Intent intent = new Intent(NEW_ACTION);
                    createGame(title);
                    intent.putExtra("nameGame", title);
                    startActivity(intent);
                }
            }
        });
        nameGame.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    title = nameGame.getText().toString();
                    if (!validateGame(title)) {
                        nameLayout.setError("Неправильное имя");
                        Toast toast = Toast.makeText(getApplicationContext(), "Имя должно содержать только буквы", Toast.LENGTH_LONG);
                        toast.show();
                        return false;
                    } else {
                        nameLayout.setErrorEnabled(false);
                        Intent intent = new Intent(NEW_ACTION);
                        createGame(title);
                        intent.putExtra("nameGame", title);
                        startActivity(intent);
                        return true;
                    }

                }
                return false;
            }
        });
    }

    public void createGame(String title){

        adapter = new DatabaseAdapter(CreateGameActivity.this, title, "kpg.db");
        adapter.open();
        adapter.addGame();
        adapter.close();

    }

    public boolean validateGame(String name){
        matcher = pattern.matcher(name);
        return matcher.matches();
    }
}
