package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;
import com.example.moxpoc.kpg1.Adapters.ScanAdapter;
import com.example.moxpoc.kpg1.Helpers.JsonTargetHelper;
import com.example.moxpoc.kpg1.Helpers.SwipeHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;


public class MyTargetsActivity extends AppCompatActivity {

    Player player;
    String table;
    public RecyclerView scannedResults;
    ArrayList<String> targets = new ArrayList<>();
    private DatabaseAdapter dbAdapter;
    ScanAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_targets);

        FloatingActionButton addTargetScan = findViewById(R.id.addTargetBtn);
        scannedResults = findViewById(R.id.myTargetsList);

        dbAdapter = new DatabaseAdapter(MyTargetsActivity.this,"myScan.db");
        dbAdapter.open();
        targets = showGames();
        arrayAdapter = new ScanAdapter(MyTargetsActivity.this, targets);
        scannedResults.setAdapter(arrayAdapter);
        SwipeHelper callback = new SwipeHelper(arrayAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(scannedResults);
        addTargetScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MyTargetsActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan new target");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if(scanningResult != null){
            if(scanningResult.getContents()==null){
                Toast.makeText(getApplicationContext(),"ooops smth wrong",Toast.LENGTH_SHORT).show();
            }
            else {
                String json = scanningResult.getContents();
                JsonTargetHelper jsonTargetHelper = new JsonTargetHelper();
                Moshi moshi = new Moshi.Builder().build();
                JsonAdapter<JsonTargetHelper> jsonAdapter = moshi.adapter(JsonTargetHelper.class);
                try {
                    jsonTargetHelper = jsonAdapter.fromJson(json);
                } catch (Exception e ){
                    System.out.print("Error");
                }
                table = jsonTargetHelper.getGame();
                dbAdapter = new DatabaseAdapter(MyTargetsActivity.this,table, "myScan.db");
                dbAdapter.open();
                dbAdapter.addScanGame();
                if(dbAdapter.getCount() != 0 && dbAdapter.getJsonTarget(dbAdapter.getCount()).getNextText().equals(jsonTargetHelper.getCurrentText())
                        && !dbAdapter.getJsonTarget(dbAdapter.getCount()).getNextText().equals(dbAdapter.getJsonTarget(dbAdapter.getCount()).getCurrentText())) {
                    dbAdapter.updateScan(jsonTargetHelper.getCurrentText(), jsonTargetHelper.getNextText());
                    Toast toast = Toast.makeText(getApplicationContext(), "Цель успешно добавлена", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Неверная цель", Toast.LENGTH_LONG);
                    toast.show();
                }
                if(dbAdapter.getCount() == 0){
                    dbAdapter.insertScanTarget(jsonTargetHelper.getCurrentText(), jsonTargetHelper.getNextText());
                    Toast toast = Toast.makeText(getApplicationContext(), "New Game", Toast.LENGTH_LONG);
                    toast.show();
                }

                targets = showGames();
                arrayAdapter = new ScanAdapter(MyTargetsActivity.this, targets );
                scannedResults.setAdapter(arrayAdapter);

                SwipeHelper callback = new SwipeHelper(arrayAdapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(scannedResults);
            }
        } else{
            super.onActivityResult(requestCode,resultCode,intent);
            Toast toast = Toast.makeText(getApplicationContext(), "Nothing scanned", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public ArrayList<String> showGames(){
        return  dbAdapter.getTables();
    }
}
