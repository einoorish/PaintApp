package com.example.simplepaint;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import java.util.ArrayList;
import java.util.List;

public class ColorPickerDialog extends Dialog {
        
    private ColorOptionSelectedListener listener;

    private final List<Integer> colorIds = List.of(
            R.color.grey, R.color.white, R.color.black, R.color.red, R.color.orange,
            R.color.yellow, R.color.green, R.color.blue, R.color.purple, R.color.pink);


    public ColorPickerDialog(@NonNull Context context) {
        super(context);
        super.setContentView(R.layout.dialog_color_picker);
        super.setCancelable(true);
        setOwnerActivity ((Activity) context);

        Window window = super.getWindow();
        if (window != null)
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        GridLayout colorsContainer = findViewById(R.id.color_columns);

        addButtonsToContainer(colorsContainer);
    }

    private void addButtonsToContainer(GridLayout colorsContainer) {
        for (int colorId: colorIds) {
            Context context = colorsContainer.getContext();
            ImageButton button = new ImageButton(context);
            button.setElevation(5);
            button.setStateListAnimator(null);
            button.setOutlineProvider(ViewOutlineProvider.BACKGROUND);

            int color = context.getResources().getColor(colorId);

            Drawable background = AppCompatResources.getDrawable(colorsContainer.getContext(), R.drawable.ic_baseline_circle_24);
            DrawableCompat.setTint(DrawableCompat.wrap(background), color);

            button.setBackground(background);

            button.setOnClickListener(v -> {
                listener.onColorOptionSelected(color);
                dismiss();
            });

            colorsContainer.addView(button);
        }
    }

    public void setOnDialogOptionSelectedListener (ColorOptionSelectedListener listener) {
        this.listener = listener;
    }

    public interface ColorOptionSelectedListener {
        void onColorOptionSelected(int color);
    }
}