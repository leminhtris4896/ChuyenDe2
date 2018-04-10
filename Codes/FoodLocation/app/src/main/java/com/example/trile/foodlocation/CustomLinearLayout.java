package com.example.trile.foodlocation;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * Created by TRILE on 21/03/2018.
 */

public class CustomLinearLayout extends LinearLayoutManager {
    private float MILLISECONDS_PER_INCH = 200f;


    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, float delay) {
        super(context);
        MILLISECONDS_PER_INCH = delay;
    }

    public CustomLinearLayout(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
            @Nullable
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return CustomLinearLayout.this.computeScrollVectorForPosition(targetPosition);
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }
        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }
}
