package com.example.moxpoc.kpg1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TargetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Bundle arguments = getIntent().getExtras();
        final Player player;
        TextView targetName = (TextView)findViewById(R.id.targetName);
        //String target = arguments.get("fName").toString() + " " + arguments.get("sName").toString();
        player = arguments.getParcelable("obj");
        targetName.setText(player.getFirstName() + " " + player.getSecondName());
    }
}
