package com.example.simplepaint;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CanvasView canvasView;

    private ImageButton colorButton;
    private ImageButton undoButton;
    private ImageButton redoButton;
    private ImageButton saveButton;
    private ImageButton brushButton;
    private ImageButton eraserButton;
    private ImageButton bucketButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = findViewById(R.id.canvas_view);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        canvasView.init(displayMetrics.widthPixels, displayMetrics.heightPixels);


        undoButton = findViewById(R.id.undo_btn);
        undoButton.setOnClickListener(this);

        redoButton = findViewById(R.id.forward_btn);
        redoButton.setOnClickListener(this);

        saveButton = findViewById(R.id.save_btn);
        saveButton.setOnClickListener(this);

        brushButton = findViewById(R.id.brush_btn);
        brushButton.setOnClickListener(this);

        eraserButton = findViewById(R.id.eraser_btn);
        eraserButton.setOnClickListener(this);

        bucketButton = findViewById(R.id.bucket_btn);
        bucketButton.setOnClickListener(this);

        colorButton = findViewById(R.id.color_picker);
        colorButton.setOnClickListener(this);
        
       // makeAllButtonsTheSameSize();
        
        final int defaultColorId = ContextCompat.getColor(colorButton.getContext(), R.color.black);
        DrawableCompat.setTint(DrawableCompat.wrap(colorButton.getDrawable()), defaultColorId);
    }

    private boolean usingOneFinger(MotionEvent event) {
        return event.getPointerCount() == 1;
    }

    private boolean usingTwoFingers(MotionEvent event) {
        return event.getPointerCount() == 2;
    }


    @Override
    public void onClick(View v) {
        int viewID = v.getId();

        switch (viewID) {
//            case R.id.undo_btn:
//                canvasView.makeUndoMove();
//                break;
//            case R.id.forward_btn:
//                canvasView.makeRedoMove();
//                break;
            case R.id.brush_btn:
                canvasView.useBrush();
                break;
            case R.id.eraser_btn:
                canvasView.useEraser();
                break;
//            case R.id.bucket_btn:
////                canvasView.useBucket();
//                break;
            case R.id.color_picker:
                ColorPickerDialog dialog = new ColorPickerDialog(MainActivity.this);
                dialog.setOnDialogOptionSelectedListener(color -> {
                    canvasView.setColor(color);
                    DrawableCompat.setTint(DrawableCompat.wrap(colorButton.getDrawable()), color);
                });
                dialog.show();
                break;
            case R.id.save_btn:
                //TODO: handle export
                break;

        }
    }

}
