package com.example.findmypet_android_ui.ui.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
