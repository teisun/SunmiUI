package sunmi.ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EdgeEffect;


/**
 * TODO: document your custom view class.
 */
public class FadingEdgeView extends ViewGroup {

    private static final String TAG = "FadingEdgeView";
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private EdgeEffect mEdgeGlowLeft;
    private EdgeEffect mEdgeGlowRight;
    private float mXDown;
    private float mXLastMove;
    private float mXMove;

    public FadingEdgeView(Context context) {
        super(context);
        init(null, 0);
    }

    public FadingEdgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FadingEdgeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    private void init(AttributeSet attrs, int defStyle) {

        setWillNotDraw(false);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FadingEdgeView, defStyle, 0);


        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "onTouchEvent event:"+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "onTouchEvent ACTION_DOWN");
                mXDown = event.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(TAG, "onTouchEvent ACTION_MOVE");
                mXMove = event.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                canOverScroll(event);
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG, "onTouchEvent ACTION_UP");

                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        canvas.rotate(10);
        canvas.translate(getWidth()/3, 50);

//        canvas.rotate(270);
        // 创建画笔
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色

        canvas.drawText("画圆：", 10, 20, p);// 画文本
        canvas.drawCircle(60, 20, 10, p);// 小圆
        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
        canvas.drawCircle(120, 20, 20, p);// 大圆

        int scrolledX = (int) (mXLastMove - mXMove);
        if (mEdgeGlowLeft != null) {
//            final int scrollX = mScrollX;
            final int scrollX = scrolledX;
            if (!mEdgeGlowLeft.isFinished()) {
                final int restoreCount = canvas.save();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

//                canvas.rotate(270);
//                canvas.translate(-height + getPaddingTop(), Math.min(0, scrollX));
                mEdgeGlowLeft.setSize(height, getWidth());
                if (mEdgeGlowLeft.draw(canvas)) {
//                    postInvalidateOnAnimation();
                    postInvalidate();
                }
                canvas.restoreToCount(restoreCount);
                Log.d(TAG, "mEdgeGlowLeft canvas.restoreToCount(restoreCount)");
            }
            if (!mEdgeGlowRight.isFinished()) {
                final int restoreCount = canvas.save();
                final int width = getWidth();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

//                canvas.translate(getWidth()/2, 0);
//                canvas.rotate(90);
//                canvas.translate(-getPaddingTop(),
//                        -(Math.max(getScrollRange(), scrollX) + width));
                mEdgeGlowRight.setSize(height, width);
                if (mEdgeGlowRight.draw(canvas)) {
//                    postInvalidateOnAnimation();
                    postInvalidate();
                }
                canvas.restoreToCount(restoreCount);
                Log.d(TAG, "mEdgeGlowRight canvas.restoreToCount(restoreCount)");
            }
        }

    }

    private int getScrollRange(){
        int scrollRange = 0;
        if(getChildCount() > 0){
            View child = getChildAt(0);
            scrollRange = Math.max(0, child.getWidth() - (getWidth() - getPaddingLeft() - getPaddingRight()));
        }
        return scrollRange;
    }

    private void canOverScroll(MotionEvent ev) {
        Log.d(TAG, "canOverScroll");
        int mActivePointerId = ev.getPointerId(0);
        final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
        if (activePointerIndex == -1) {
            LogUtil.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
            return;
        }

        int scrolledX = (int) (mXLastMove - mXMove);
        if(scrolledX < 0){
            //左滑
        }else {
            //右滑
        }

        int deltaX = scrolledX;
        final int oldX = (int) ev.getX();
        final int range = getScrollRange();
        final int overscrollMode = getOverScrollMode();
        final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);
        if (canOverscroll) {
            final int pulledToX = oldX + 10;
            if (pulledToX < 0) {
                mEdgeGlowRight.onPull((float) deltaX / getWidth());
//                mEdgeGlowLeft.onPull((float) deltaX / getWidth(),
//                        1.f - ev.getY(activePointerIndex) / getHeight());
                if (!mEdgeGlowRight.isFinished()) {
                    mEdgeGlowRight.onRelease();
                }
            } else if (pulledToX > range) {
                mEdgeGlowRight.onPull((float) deltaX / getWidth());
//                mEdgeGlowRight.onPull((float) deltaX / getWidth(),
//                        ev.getY(activePointerIndex) / getHeight());
                if (!mEdgeGlowLeft.isFinished()) {
                    mEdgeGlowLeft.onRelease();
                }
            }
            if (mEdgeGlowLeft != null
                    && (!mEdgeGlowLeft.isFinished() || !mEdgeGlowRight.isFinished())) {
//                postInvalidateOnAnimation();
                postInvalidate();
            }
        }
    }

    @Override
    public void setOverScrollMode(int mode) {
        if(mode != OVER_SCROLL_NEVER){
            if(mEdgeGlowLeft == null){
                Context context = getContext();
                mEdgeGlowLeft = new EdgeEffect(context);
                mEdgeGlowRight = new EdgeEffect(context);
            }
        }else{
            mEdgeGlowLeft = null;
            mEdgeGlowRight = null;
        }
        super.setOverScrollMode(mode);
    }

}
