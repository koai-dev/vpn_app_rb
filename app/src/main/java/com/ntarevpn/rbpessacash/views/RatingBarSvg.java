package com.ntarevpn.rbpessacash.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.appcompat.widget.AppCompatRatingBar;

import  com.ntarevpn.rbpessacash.R;

public class RatingBarSvg extends AppCompatRatingBar {
    private Bitmap mSampleTile = null;

    private Shape drawableShape() {
        float[] roundedCorners = {5f, 5f, 5f, 5f, 5f, 5f, 5f, 5f};
        return new RoundRectShape(roundedCorners, null, null);
    }

    public RatingBarSvg(Context context) {
        this(context, null);
    }

    public RatingBarSvg(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ratingBarStyle);
    }

    public RatingBarSvg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayerDrawable drawable = (LayerDrawable) tileify(getProgressDrawable(), false);
        setProgressDrawable(drawable);
    }

    private Drawable tileify(Drawable drawable, boolean clip) {
        if (drawable instanceof DrawableWrapperCompat) {
            Drawable inner = ((DrawableWrapperCompat) drawable).getDrawable();
            if (inner != null) {
                inner = tileify(inner, clip);
                ((DrawableWrapperCompat) drawable).setDrawable(inner);
            }
        } else if (drawable instanceof LayerDrawable) {
            int numberOfLayers = ((LayerDrawable) drawable).getNumberOfLayers();
            Drawable[] outDrawables = new Drawable[numberOfLayers];

            for (int i = 0; i < numberOfLayers; i++) {
                int id = ((LayerDrawable) drawable).getId(i);
                outDrawables[i] = tileify(((LayerDrawable) drawable).getDrawable(i),
                        id == android.R.id.progress || id == android.R.id.secondaryProgress);
            }

            LayerDrawable newBg = new LayerDrawable(outDrawables);

            for (int i = 0; i < numberOfLayers; i++) {
                newBg.setId(i, ((LayerDrawable) drawable).getId(i));
            }

            return newBg;
        } else if (drawable instanceof BitmapDrawable) {
            Bitmap tileBitmap = ((BitmapDrawable) drawable).getBitmap();
            if (mSampleTile == null) {
                mSampleTile = tileBitmap;
            }

            ShapeDrawable shapeDrawable = new ShapeDrawable(drawableShape());
            BitmapShader bitmapShader = new BitmapShader(tileBitmap,
                    Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            shapeDrawable.getPaint().setShader(bitmapShader);
            shapeDrawable.getPaint().setColorFilter(((BitmapDrawable) drawable).getPaint().getColorFilter());
            return clip ? new ClipDrawable(shapeDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL) : shapeDrawable;
        } else {
            return tileify(getBitmapDrawableFromVectorDrawable(drawable), clip);
        }

        return drawable;
    }

    private BitmapDrawable getBitmapDrawableFromVectorDrawable(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth() + (2),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mSampleTile != null) {
            int width = mSampleTile.getWidth() * getNumStars();
            setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), getMeasuredHeight());
        }
    }
}
