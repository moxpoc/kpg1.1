package com.example.moxpoc.kpg1;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class TargetActivity extends AppCompatActivity {
    int WIDTH = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Bundle arguments = getIntent().getExtras();
        String table = arguments.getString("table");
        DatabaseAdapter dbAdapter = new DatabaseAdapter(this, table);
        dbAdapter.open();
        Player target, player;
        TextView targetName = findViewById(R.id.targetName);
        target = dbAdapter.getTarget((arguments.getInt("position")+1));
        player = dbAdapter.getPlayer((arguments.getInt("position")+1));
        targetName.setText(target.toString());
        dbAdapter.close();
        String name = target.toString();
        CoderSHA coderSHA = new CoderSHA();
        String json = coderSHA.toJson(player,target, table);

        ImageView imageView = findViewById(R.id.targetImageView);
        try{
            Bitmap bitmap = encodeAsBitmap(json);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e){
            e.printStackTrace();
        }
    }

    public Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try{
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae){
            return null;
        }
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


