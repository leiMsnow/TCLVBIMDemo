package com.tencent.qcloud.xiaozhibo.ui.customviews;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/9/13
 * 跟随手指移动功能以及添加窗口功能
 */
public class BaseFloatingView extends FrameLayout implements GestureDetector.OnGestureListener{

    protected final Context mContext;
    protected WindowManager mWindowManager;
    private GestureDetector mGestureDetector;
    private WindowManager.LayoutParams layoutParams;
    private float lastX, lastY;

    public BaseFloatingView(Context context) {
        super(context);

        this.mContext = context;

        this.mGestureDetector = new GestureDetector(context, this);

    }

    public BaseFloatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mGestureDetector = new GestureDetector(context, this);
    }

    public BaseFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mGestureDetector = new GestureDetector(context, this);
    }

    protected void showView(View view) {
        showView(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    protected void showView(View view, int width, int height) {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        mWindowManager.addView(view, layoutParams);
    }

    protected void hideView() {
        if (null != mWindowManager)
            mWindowManager.removeViewImmediate(this);
        mWindowManager = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        lastX = e.getRawX();
        lastY = e.getRawY();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float nowX, nowY, tranX, tranY;
        // 获取移动时的X，Y坐标
        nowX = e2.getRawX();
        nowY = e2.getRawY();
        // 计算XY坐标偏移量
        tranX = nowX - lastX;
        tranY = nowY - lastY;
        // 移动悬浮窗
        layoutParams.x += tranX;
        layoutParams.y += tranY;
        //更新悬浮窗位置
        mWindowManager.updateViewLayout(this ,layoutParams);
        //记录当前坐标作为下一次计算的上一次移动的位置坐标
        lastX = nowX;
        lastY = nowY;
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Toast.makeText(mContext, "onLongPress", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
