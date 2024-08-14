package com.ntarevpn.rbpessacash.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import  com.ntarevpn.rbpessacash.R;

public class LightningBackground extends View {
    private Drawable drawable;
    private int drawableWidth; // Width of the Drawable
    private int drawableHeight; // Height of the Drawable
    private int margin; // Margin around the Drawable

    public LightningBackground(Context context) {
        super(context);
        init();
    }

    public LightningBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        drawable = getResources().getDrawable(R.drawable.lightning);
        drawable.setAlpha(50);

        int size = 300;
        float aspectRatio = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
        drawableWidth = (int) (size * aspectRatio);
        drawableHeight = size;
        margin = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        drawable.setBounds(margin, margin, margin + drawableWidth, margin + drawableHeight);
        drawable.draw(canvas);

        drawable.setBounds(viewWidth - margin - drawableWidth, margin, viewWidth - margin, margin + drawableHeight);
        drawable.draw(canvas);

        drawable.setBounds(margin, viewHeight - margin - drawableHeight, margin + drawableWidth, viewHeight - margin);
        drawable.draw(canvas);

        drawable.setBounds(viewWidth - margin - drawableWidth, viewHeight - margin - drawableHeight, viewWidth - margin, viewHeight - margin);
        drawable.draw(canvas);
    }

    public void setDrawableSize(int width, int height) {
        drawableWidth = width;
        drawableHeight = height;
        invalidate(); // Redraw the view with the new width and height
    }

    public void setMargin(int margin) {
        this.margin = margin;
        invalidate(); // Redraw the view with the new margin
    }
}


