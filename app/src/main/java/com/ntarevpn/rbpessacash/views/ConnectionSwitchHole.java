package com.ntarevpn.rbpessacash.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class ConnectionSwitchHole extends View {
    public ConnectionSwitchHole(Context context) {
        super(context);
        init();
    }

    public ConnectionSwitchHole(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {

        float centerX = getCenterX();
        float centerY = getCenterY();
        float radius = getRadius();

        canvas.drawCircle(centerX, centerY, radius, paint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        initPaintShader();

    }

    private void init() {

        initPaint();

    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initPaintShader();
    }

    private void initPaintShader() {
        float centerX = getCenterX();
        float centerY = getCenterY();
        float radius = Math.max(getRadius(), 50);
        int color1 = Color.parseColor("#3C3C4C");
        int color2 = Color.parseColor("#32323E");

        int[] colors = new int[]{color1, color2};
        float[] positions = new float[]{0.85f, 1f};
        RadialGradient gradient = new RadialGradient(centerX, centerY, radius, colors, positions, Shader.TileMode.CLAMP);

        paint.setShader(gradient);
    }

    private float getRadius() {
        return Math.min(getCenterX(), getCenterY());
    }

    private float getCenterX() {
        return getWidth() / 2f;
    }

    private float getCenterY() {
        return getHeight() / 2f;
    }
}
