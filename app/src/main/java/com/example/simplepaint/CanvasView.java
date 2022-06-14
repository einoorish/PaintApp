package com.example.simplepaint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CanvasView extends View {
    private static final int DEFAULT_BG_COLOUR = Color.TRANSPARENT;

    private final ArrayList<DrawPath> availableUndoMoves;
    private final ArrayList<DrawPath> availableRedoMoves;

    private Canvas canvas;
    private Bitmap bitmap;
    private final Paint paint;

    private int currentColour;

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        availableUndoMoves = new ArrayList<>();
        availableRedoMoves = new ArrayList<>();
    }

    public void init(int width, int height) {
        paint.setColor(currentColour);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void setColour(int colour) {
        currentColour = colour;
    }

    public void makeUndoMove() {
        if (availableUndoMoves.isEmpty())
            return;
    }

    public void makeRedoMove() {
        if (availableRedoMoves.isEmpty())
            return;
    }

    public void selectBrush() {
    }


    public void selectEraser() {
    }

    public void selectBucket() {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if (availableUndoMoves.size() != 0) {
            // get the most recently drawn path
            DrawPath lastPath = availableUndoMoves.get(availableUndoMoves.size() - 1);
            // set the paint object attributes
            paint.setColor(lastPath.getColour());
            paint.setStrokeWidth(lastPath.getWidth());
            paint.setMaskFilter(null);
            // draw the path
            this.canvas.drawPath(lastPath.getPath(), paint);
        }
        // draw the bitmap to the canvas
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

}