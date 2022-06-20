package com.example.simplepaint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class PaintPath extends Path{
    private final Paint paint;

    public PaintPath(){
        super();
        paint = new Paint();

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(50);
    }


    public Paint getPaint() {return paint;}

    public int getColor() {
        return paint.getColor();
    }

    public float getWidth() {
        return paint.getStrokeWidth();
    }

    public void setPaintColor(int color) {
        this.paint.setColor(color);
    }
}