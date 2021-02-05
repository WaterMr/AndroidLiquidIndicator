package com.cwj.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int colorBlue = Color.rgb(0x27, 0x77, 0xCA);
    private int colorGray = Color.rgb(0xF6, 0xF8, 0xFA);
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_progress);
        imageView = findViewById(R.id.iv_pointer);
        bitmap = Bitmap.createBitmap(102, 302, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();

        SeekBar seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.e("FFGG", progress + "");
                updateState(progress);
                textView.setText(progress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

//    ImageView imageView = findViewById(R.id.iv_pointer);
//    Bitmap bitmap = Bitmap.createBitmap(102, 302, Bitmap.Config.ARGB_8888);
//    Canvas canvas = new Canvas(bitmap);
//    Paint paint = new Paint();

    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;


    /**
     * @param progress 1-100之间的整数
     */
    private void updateState(int progress) {
        canvas.drawRGB(0xF6, 0xF8, 0xFA);

        paint.setColor(colorBlue);
        int maxHeight = 302;
        int realHeight = (int) (progress / 100.0 * maxHeight);
        Rect rect = new Rect(0, 302 - realHeight, 102, 302);
        canvas.drawRect(rect, paint);
        imageView.setImageBitmap(bitmap);
    }
}