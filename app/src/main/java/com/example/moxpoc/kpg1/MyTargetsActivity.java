package com.example.moxpoc.kpg1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class MyTargetsActivity extends AppCompatActivity {

    public static  TextView scanResult;
    public ListView scannedResults;
    ArrayList<String> targets = new ArrayList();
    public static final String ACTION = "android.intent.action.START_SCAN_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_targets);

        Button addTargetScan = findViewById(R.id.addTargetBtn);
        //scanResult = findViewById(R.id.scanResult);
        scannedResults = findViewById(R.id.myTargetsList);
        addTargetScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*
                Intent intent = new Intent(ACTION);
                startActivityForResult(intent, 1);*/
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
                targets.add(scanningResult.getContents());
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(MyTargetsActivity.this, android.R.layout.simple_list_item_1, targets);
                scannedResults.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }
        } else{
            super.onActivityResult(requestCode,resultCode,intent);
            Toast toast = Toast.makeText(getApplicationContext(), "Nothing", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
