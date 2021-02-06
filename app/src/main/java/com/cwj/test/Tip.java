package com.cwj.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.logging.LogRecord;

public class Tip extends FrameLayout {

    private int colorBlue = Color.rgb(0x27, 0x77, 0xCA);
    private int colorGray = Color.rgb(0xF6, 0xF8, 0xFA);

    private double lastProgress = 0;

    ImageView imageView;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    private Context context;

    public Tip(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.tip, this);
        // 获取控件
        imageView =  findViewById(R.id.iv_pointer);
        bitmap = Bitmap.createBitmap(102, 302, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(colorBlue);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void ChangeProgress(double progress) {
        //动画
        final int maxHeight = 302 -15;
        double realHeight = progress / 100.0 * maxHeight;
        final double lastHeight = lastProgress / 100.0 * maxHeight;

        Log.i("Tip", "real "+realHeight +" last"+lastHeight);

        lastProgress = progress;
        new Thread(new Runnable() {
            @Override
            public void run() {
                double LastHeight = lastHeight;
                double dHeight = realHeight - lastHeight;
                Log.i("Tip", "d hegiht is "+dHeight);
                int count = 0;
                int seed = 4;
                if(Math.abs(dHeight)<5) {
                    seed = 50;
                }

                for(int i=90;i>=0;i--) {
                    if(i%seed!=0)continue;
                    count++;
                    double test = Math.cos(i/180.0*3.1416);

                    double drawHeight = maxHeight- (LastHeight + dHeight * test);//15是图片原因产生的零位误差
                    if(drawHeight <= 0) {
                        drawHeight = 0;
                    }
//                    Log.i("Tip",count+" "+drawHeight);
                    canvas.drawRGB(0xF6, 0xF8, 0xFA);
                    Rect rect = new Rect(0, (int)drawHeight, 102, 302);
                    canvas.drawRect(rect, paint);

//                    imageView.setImageBitmap(bitmap);
                    handler.sendEmptyMessage(1);

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Tip","draw "+count+" time");

            }
        }).start();
    }

    private android.os.Handler handler =new Handler()
    {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            imageView.setImageBitmap(bitmap);
        }
    };
}
