package com.ntarevpn.rbpessacash.util;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class SwipeUpGestureDetector implements View.OnTouchListener {
    private final GestureDetector gestureDetector;

    public SwipeUpGestureDetector(Context context, OnSwipeUpListener listener) {
        gestureDetector = new GestureDetector(context, new GestureListener(listener));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    public interface OnSwipeUpListener {
        Boolean onSwipeUp();
    }

    private static class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final OnSwipeUpListener listener;

        private GestureListener(OnSwipeUpListener listener) {
            this.listener = listener;
        }

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 400;
        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            float absX = Math.abs(diffX);
            float absY = Math.abs(diffY);

            Log.d("TAG", "DiffX " + diffX + "\nDiff Y "+ diffY + "\nVel X "+ velocityX + "\nVel Y "+velocityY);

            if (absX < absY && absY > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD && diffY < 0) {
                return listener.onSwipeUp();
            }

            return false;
        }
    }
}
