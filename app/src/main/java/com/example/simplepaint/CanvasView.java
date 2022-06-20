package com.example.simplepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CanvasView extends View {

    private Canvas canvas;
    private Bitmap canvasBitmap;

    private Paint paint;
    private Path currentPath;
    private final ArrayList<DrawPath> availableUndoMoves;
    private final ArrayList<DrawPath> availableRedoMoves;

    private int currentColor;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        availableUndoMoves = new ArrayList<>();
        availableRedoMoves = new ArrayList<>();

        currentPath = new Path();
        paint = new Paint();
        paint.setStrokeWidth(50);
    }

    public void init(int width, int height) {
        initDrawingPaint();

        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBitmap);
    }

    private void initDrawingPaint() {
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBitmap, 0, 0, null);
        canvas.drawPath(currentPath, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                addPath(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(currentPath, paint);
                currentPath.reset();
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    private void addPath(float x, float y) {
        currentPath.lineTo(x, y);

        DrawPath drawPath = new DrawPath(paint.getColor(), 50, currentPath);
        availableUndoMoves.add(drawPath);
    }

    public void setColor(int color) {
        currentColor = color;
        paint.setColor(color);
    }

    public void useEraser() {
        paint.setMaskFilter(null);
        paint.setColor(Color.TRANSPARENT);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    public void useBrush() {
        paint.setColor(currentColor);
        paint.setXfermode(null);
    }

    public void makeRedoMove() {
        if (availableRedoMoves.isEmpty())
            return;
    }

    public void makeUndoMove() {
        if (availableUndoMoves.isEmpty())
            return;
    }
}
