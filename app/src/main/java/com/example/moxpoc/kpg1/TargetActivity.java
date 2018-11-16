package com.example.moxpoc.kpg1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moxpoc.kpg1.Adapters.DatabaseAdapter;
import com.example.moxpoc.kpg1.Helpers.JsonTargetHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.Hashtable;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class TargetActivity extends AppCompatActivity {
    int WIDTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Bundle arguments = getIntent().getExtras();
        String table = arguments.getString("table");
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this, table, "kpg.db");
        dbAdapter.open();
        Player target, player;
        TextView targetName = findViewById(R.id.targetName);
        target = dbAdapter.getTarget((arguments.getInt("position")+1));
        player = dbAdapter.getPlayer((arguments.getInt("position")+1));
        dbAdapter.close();
        JsonTargetHelper jsonTargetHelper = new JsonTargetHelper(player,target, table);

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<JsonTargetHelper> jsonAdapter = moshi.adapter(JsonTargetHelper.class);
        String json = jsonAdapter.toJson(jsonTargetHelper);

        targetName.setText(jsonTargetHelper.getNextText());
        ImageView imageView = findViewById(R.id.targetImageView);
        WIDTH = 800;
        try{
            Bitmap bitmap = encodeAsBitmap(json);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e){
            e.printStackTrace();
        }
    }

    public Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        QRCodeWriter writer = new QRCodeWriter();

        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        result = writer.encode(str,BarcodeFormat.QR_CODE, WIDTH, WIDTH, hints);
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for(int y = 0; y < h; y++){
            int offset = y * w;
            for(int x = 0; x < w; x++){
                pixels[offset + x] = result.get(x,y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, WIDTH, 0 ,0,w,h);
        return bitmap;
    }
}


