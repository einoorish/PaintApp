package com.example.simplepaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CanvasView extends View {

    private Canvas canvas;

    private Paint paint;
    private PaintPath currentPath;
    private final ArrayList<PaintPath> availableUndoMoves;
    private final ArrayList<PaintPath> availableRedoMoves;

    private int currentColor;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        availableUndoMoves = new ArrayList<>();
        availableRedoMoves = new ArrayList<>();

        currentPath = new PaintPath();
        paint = new Paint();

        canvas = new Canvas();
    }


    protected void onDraw(Canvas canvas) {
        for(PaintPath path: availableUndoMoves){
            canvas.drawPath(path, path.getPaint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentPath = new PaintPath();
                currentPath.reset();
                currentPath.moveTo(touchX, touchY);
                currentPath.setPaintColor(paint.getColor());
                availableUndoMoves.add(currentPath);
                break;

            case MotionEvent.ACTION_MOVE:
                currentPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(currentPath, currentPath.getPaint());
                break;
            default:
                return false;
        }

        invalidate();
        return true;
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
        if (!availableRedoMoves.isEmpty()) {
            PaintPath move = availableRedoMoves.remove(availableRedoMoves.size() - 1);
            availableUndoMoves.add(move);
            invalidate();
        }
    }

    public void makeUndoMove() {
        if (!availableUndoMoves.isEmpty()) {
            PaintPath move = availableUndoMoves.remove(availableUndoMoves.size() - 1);
            availableRedoMoves.add(move);
            invalidate();
        }
    }
}