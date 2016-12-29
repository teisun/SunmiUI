package sunmi.ui.flexible;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ScrollView;


/**
 * Created by longtao.li on 2016/12/7.
 */
public class FlexibleScrollView extends ScrollView implements Flexible{
    private static final String TAG = "FlexibleScrollView";

    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    private FlexibleEffect mFlexibleEffect;

    private int mLastMotionY;
    private int mActivePointerId = INVALID_POINTER;
    /**
     * True if the user is currently dragging this ScrollView around. This is
     * not the same as 'is being flinged', which can be checked by
     * mScroller.isFinished() (flinging begins when the user lifts his finger).
     */
    private boolean mIsBeingDragged = false;

    private int mTouchSlop;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;
    private float currentVelocity;

    public FlexibleScrollView(Context context) {
        super(context);
        init();
    }

    public FlexibleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlexibleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlexibleScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        mFlexibleEffect = new FlexibleEffect(this);
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                mLastMotionY = (int) ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
//                    Log.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
                    break;
                }

                final int y = (int) ev.getY(activePointerIndex);
                int deltaY = mLastMotionY - y;

                if (!mIsBeingDragged && Math.abs(deltaY) > mTouchSlop) {
                    mIsBeingDragged = true;
                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }
                }
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    mLastMotionY = y;
                    mFlexibleEffect.onPull((float) deltaY / getHeight());
                    if(!mFlexibleEffect.isFinished()){
                        return true;
                    }

                }

                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    mActivePointerId = INVALID_POINTER;
                    mIsBeingDragged = false;
                    if (mFlexibleEffect != null&&mFlexibleEffect.isPulling()) {
                        mFlexibleEffect.onAbsorb(-1);
                    }
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    currentVelocity = mVelocityTracker.getYVelocity(mActivePointerId);
                    Log.d(TAG, "currentVelocity:"+mVelocityTracker.getYVelocity(mActivePointerId) );
                    recycleVelocityTracker();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(ev);
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    public boolean isTop() {
        return getScrollY() == 0;
    }

    @Override
    public boolean isBottom() {
        View childView = getChildAt(0);
        boolean isBottom = false;
        if(childView != null){
            isBottom = childView.getMeasuredHeight() <= getScrollY() + getHeight();
        }
        return isBottom;
    }

    @Override
    public void setResistance(float resistance) {
        mFlexibleEffect.setResistance(resistance);
    }

    @Override
    public void setTopFlexible(boolean topFlexible) {
        mFlexibleEffect.setTopFlexible(topFlexible);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(isBottom()||isTop()){
            mFlexibleEffect.onFling(currentVelocity);
            currentVelocity = 0;
        }

    }
}
