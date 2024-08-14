package com.ntarevpn.rbpessacash.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.NonNull;

public class ConnectionSwitchButton extends View {
    public static final int MODE_OFF = 0;
    public static final int MODE_LOADING = 1;
    public static final int MODE_ON = 2;

    public ConnectionSwitchButton(Context context) {
        super(context);
        init();
    }

    public ConnectionSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private ValueAnimator animator;
    private ValueAnimator colorAnimator;

    private Paint bodyPaint;
    private Paint bodyShadowPaint;
    private Paint topBevelPaint;
    private Paint arcPaint;
    private Paint powerPaint;
    private Paint powerGlowPaint;

    private RectF arcRectF;
    private RectF powerRectF;

    private int mode = MODE_OFF;
    private int powerColor = Color.parseColor("#2E2E3D");
    private int glowAlpha = 0;
    private int archAlpha = 0;
    private int archReach = 0;
    private boolean isActive = false;

    @Override
    protected void onDraw(Canvas canvas) {

        float centerX = getCenterX();
        float centerY = getCenterY();
        float radius = getRadius();
        float mReach = this.archReach;

        if (mode == MODE_LOADING) {
            mReach = 90f;
            arcPaint.setAlpha(archAlpha);
        } else if (arcPaint.getAlpha() != 255) {
            arcPaint.setAlpha(255);
        }
        powerPaint.setColor(powerColor);
        powerGlowPaint.setAlpha(glowAlpha);

        float bevelRadius = radius / 1.35f;

        canvas.rotate(-35, centerX, centerY);
        canvas.drawCircle(centerX + 9f, centerY + 19f, radius, bodyShadowPaint);
        canvas.drawCircle(centerX, centerY, radius, bodyPaint);
        canvas.drawCircle(centerX, centerY, bevelRadius, topBevelPaint);

        canvas.rotate(35, centerX, centerY);
        canvas.drawArc(arcRectF, 270, -mReach, false, arcPaint);
        canvas.drawArc(arcRectF, 90, mReach, false, arcPaint);
        canvas.drawArc(arcRectF, 90, -mReach, false, arcPaint);
        canvas.drawArc(arcRectF, 270, mReach, false, arcPaint);

        canvas.drawArc(powerRectF, -45, 270, false, powerPaint);
        canvas.drawLine(centerX, centerY - 32, centerX, powerRectF.top - 20, powerPaint);
        canvas.drawArc(powerRectF, -45, 270, false, powerGlowPaint);
        canvas.drawLine(centerX, centerY - 32, centerX, powerRectF.top - 20, powerGlowPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        initArcRectF();
        initPowerRectF();
        setPowerStrokeWidth();

        bodyPaint.setShader(getBodyShader());
        topBevelPaint.setShader(getTopBevelShader());
        arcPaint.setShader(getArcGradient());

    }

    public boolean getIsActive() {
        return mode == MODE_ON;
    }

    public void toggle() {
        if (mode == MODE_OFF) {
            setState(MODE_LOADING);
        } else if (mode == MODE_LOADING) {
            setState(MODE_ON);
        } else if (mode == MODE_ON) {
            setState(MODE_OFF);
        }
    }

    public void setState(int value) {
        if (mode == MODE_LOADING && value == MODE_OFF) {
            animator.end();
            colorAnimator.end();
        } else if (mode == value) {
            return;
        }
        mode = value;
        if (mode == MODE_LOADING) {
            setAnimationDuration(1000);
            setAnimationRepeating(true);
        } else {
            setAnimationDuration();
            setAnimationRepeating(false);
            if (mode != MODE_ON) {
                animator.reverse();
                colorAnimator.reverse();
                return;
            }
        }
        animator.start();
        colorAnimator.start();
    }

    private void init() {

        initArcRectF();
        initAnimation();
        initPowerPaint();
        initPowerGlowPaint();
        initBodyPaint();
        initBodyShadowPaint();
        initTopBevelPaint();
        initArcPaint();
        setPowerStrokeWidth();

    }

    private void initArcRectF() {

        float centerX = getCenterX();
        float centerY = getCenterY();
        float size = getRadius() / 1.35f;

        arcRectF = new RectF(centerX - size, centerY - size, centerX + size, centerY + size);

    }

    private void initPowerRectF() {

        float centerX = getCenterX();
        float centerY = getCenterY();
        float size = getRadius() / 3f;

        powerRectF = new RectF(centerX - size, centerY - size, centerX + size, centerY + size);

    }

    private void initAnimation() {

        int startColor = Color.parseColor("#2E2E3D");
        int endColor = Color.parseColor("#D6A21E");

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            if (mode != MODE_LOADING) {
                archReach = (int) (90f * value);
            } else {
                archAlpha = (int) (255f * value);
            }
            glowAlpha = (int) (128f * value);
            invalidate();
        });

        colorAnimator = ValueAnimator.ofArgb(startColor, endColor);
        colorAnimator.addUpdateListener(animation -> {
            powerColor = (int) animation.getAnimatedValue();
            invalidate();
        });

        setAnimationDuration();
    }

    private void setAnimationRepeating(Boolean repeating) {
        if (repeating) {
            animator.setRepeatCount(Animation.INFINITE);
            colorAnimator.setRepeatCount(Animation.INFINITE);
            animator.setInterpolator(this::upAndDownInterpolate);
            colorAnimator.setInterpolator(this::upAndDownInterpolate);
        } else {
            animator.setRepeatCount(0);
            colorAnimator.setRepeatCount(0);
            animator.setInterpolator(v -> v);
            colorAnimator.setInterpolator(v -> v);
        }
    }

    private void setAnimationDuration() {
        setAnimationDuration(300);
    }

    private void setAnimationDuration(int time) {
        animator.setDuration(time);
        colorAnimator.setDuration(time);
    }

    private float upAndDownInterpolate(float v) {
        return Math.abs(v - .5f) * -2f + 1f;
    }

    private void initPowerPaint() {

        powerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        powerPaint.setStyle(Paint.Style.STROKE);
        powerPaint.setStrokeCap(Paint.Cap.ROUND);
        powerPaint.setColor(powerColor);

    }

    private float getPowerStrokeWidth() {
        return 18 * Math.min(getWidth() / 720f, getHeight() / 720f);
    }

    private void initPowerGlowPaint() {

        int color = Color.parseColor("#80D6A21E");
        powerGlowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        powerGlowPaint.setStyle(Paint.Style.STROKE);
        powerGlowPaint.setStrokeCap(Paint.Cap.ROUND);
        powerGlowPaint.setColor(color);
        powerGlowPaint.setMaskFilter(new BlurMaskFilter(4, BlurMaskFilter.Blur.NORMAL));

    }

    private void initBodyPaint() {

        bodyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setShader(getBodyShader());

    }

    @NonNull
    private Shader getBodyShader() {

        int startColor = Color.parseColor("#484857");
        int endColor = Color.parseColor("#2D2D3A");

        return getLinearGradient(startColor, endColor, getRadius());

    }

    private void initBodyShadowPaint() {

        int color = Color.parseColor("#24000000");
        bodyShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bodyShadowPaint.setColor(color);
        bodyShadowPaint.setMaskFilter(new BlurMaskFilter(14, BlurMaskFilter.Blur.NORMAL));

    }

    private void initTopBevelPaint() {

        topBevelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topBevelPaint.setShader(getTopBevelShader());

    }

    private Shader getTopBevelShader() {

        int startColor = Color.parseColor("#343441");
        int endColor = Color.parseColor("#3B3B4D");

        return getLinearGradient(startColor, endColor, getRadius() / 1.35f);

    }

    private void initArcPaint() {

        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(6);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);

        arcPaint.setShader(getArcGradient());

    }

    private Shader getArcGradient() {

        int mainColor = Color.parseColor("#D6A21E");
        int mainColorFaded = Color.parseColor("#26D6A21E");
        int mainColorTransparent = Color.parseColor("#00D6A21E");

        int[] colors = {mainColor, mainColorFaded, mainColorTransparent, mainColorFaded, mainColor};
        float[] positions = {0f, 0.1f, 0.5f, 0.9f, 1f};
        return new LinearGradient(0f, arcRectF.top, 0f, arcRectF.bottom, colors, positions, Shader.TileMode.CLAMP);

    }

    private LinearGradient getLinearGradient(int start, int end, float height) {
        return new LinearGradient(0f, getCenterY() - height / 2, 0f, getCenterY() + height / 2, start, end, Shader.TileMode.CLAMP);
    }

    private float getRadius() {
        return Math.min(getCenterX(), getCenterY()) - 60;
    }

    private float getCenterX() {
        return getWidth() / 2f;
    }

    private float getCenterY() {
        return getHeight() / 2f;
    }

    private void setPowerStrokeWidth() {
        float strokeWidth = getPowerStrokeWidth();
        powerPaint.setStrokeWidth(strokeWidth);
        powerGlowPaint.setStrokeWidth(getPowerStrokeWidth());
    }
}
