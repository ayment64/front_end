package com.aymentlili.aamoomor.Services;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyTouchListener
        implements RecyclerView.OnItemTouchListener {
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetectorCompat mGestureDetector;
    private OnTouchActionListener mOnTouchActionListener;

    public MyTouchListener(Context context, final RecyclerView recyclerView, OnTouchActionListener onTouchActionListener) {
        this.mOnTouchActionListener = onTouchActionListener;
        this.mGestureDetector = new GestureDetectorCompat(context, (GestureDetector.OnGestureListener)new GestureDetector.SimpleOnGestureListener(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                try {
                    if (Math.abs((float)(motionEvent.getY() - motionEvent2.getY())) > 250.0f) {
                        return false;
                    }
                    View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    int n = recyclerView.getChildPosition(view);
                    if (motionEvent.getX() - motionEvent2.getX() > 120.0f && Math.abs((float)f) > 200.0f) {
                        if (MyTouchListener.this.mOnTouchActionListener == null) return false;
                        if (view == null) return false;
                        MyTouchListener.this.mOnTouchActionListener.onLeftSwipe(view, n);
                        return false;
                    }
                    if (!(motionEvent2.getX() - motionEvent.getX() > 120.0f)) return false;
                    if (!(Math.abs((float)f) > 200.0f)) return false;
                    if (MyTouchListener.this.mOnTouchActionListener == null) return false;
                    if (view == null) return false;
                    MyTouchListener.this.mOnTouchActionListener.onRightSwipe(view, n);
                    return false;
                }
                catch (Exception exception) {
                    return false;
                }
            }

            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                int n = recyclerView.getChildPosition(view);
                MyTouchListener.this.mOnTouchActionListener.onClick(view, n);
                return false;
            }
        });
    }

    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
    }

    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    public static interface OnTouchActionListener {
        public void onClick(View var1, int var2);

        public void onLeftSwipe(View var1, int var2);

        public void onRightSwipe(View var1, int var2);
    }

}

