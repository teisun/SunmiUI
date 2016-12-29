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
import android.widget.AbsListView;
import android.widget.ListView;


/**
 * Created by longtao.li on 2016/11/29.
 */
public class FlexibleListView extends ListView implements Flexible{

    private static final String TAG = "FlexibleListView";

    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    private ScrollListener scrollListener;
    private OnScrollListener customerScrollListener;


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



    private FlexibleEffect mFlexibleEffect;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    public FlexibleListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public FlexibleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public FlexibleListView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOverScrollMode(OVER_SCROLL_NEVER);
        scrollListener = new ScrollListener();
        mFlexibleEffect = new FlexibleEffect(this);

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        super.setOnScrollListener(scrollListener);
    }

    class ScrollListener implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(customerScrollListener != null){
                customerScrollListener.onScrollStateChanged(view, scrollState);
            }
            if(scrollState == OnScrollListener.SCROLL_STATE_IDLE){
                Log.d(TAG, "scrollState == OnScrollListener.SCROLL_STATE_IDLE");
                if(isBottom()||isTop()){
                    mFlexibleEffect.onFling(currentVelocity);
                    currentVelocity = 0;
                }
            }else if(scrollState == OnScrollListener.SCROLL_STATE_FLING){
//                Log.d(TAG, "scrollState == OnScrollListener.SCROLL_STATE_FLING");
            }else if(scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                Log.d(TAG, "scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL");
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if(customerScrollListener != null){
                customerScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        this.customerScrollListener = l;
        super.setOnScrollListener(scrollListener);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        Log.d("onScrollChanged", "onScrollChanged l:"+l+" t:"+t+" oldl:"+oldl+" oldt:"+oldt);
        super.onScrollChanged(l, t, oldl, oldt);

    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(OVER_SCROLL_NEVER);
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
//                LogUtil.d(TAG, "onTouchEvent ACTION_DOWN");
                mIsBeingDragged = false;
                mLastMotionY = (int) ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
//                LogUtil.d(TAG, "onTouchEvent ACTION_MOVE");

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
//                LogUtil.d(TAG, "onTouchEvent ACTION_UP");
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

    /**
     *
     * @return 判断是否在顶部
     */
    public boolean isTop() {
        int firstVisibleItem = getFirstVisiblePosition();
        if (firstVisibleItem == 0) {
            View firstVisibleItemView = getChildAt(0);
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == getPaddingTop()) {
                Log.d(TAG, "##### 滚动到顶部 ######");
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return 判断是否在底部
     */
    public boolean isBottom() {
        int firstVisibleItem = getFirstVisiblePosition();
        int visibleItemCount = getChildCount();
        int totalItemCount = getCount();
        if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            View lastVisibleItemView = getChildAt(getChildCount() - 1);
            if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == getHeight()-getPaddingBottom()) {
                Log.d(TAG, "##### 滚动到底部 ######");
                return true;
            }
        }
        return false;
    }

    @Override
    public void setResistance(float resistance) {
        mFlexibleEffect.setResistance(resistance);
    }

    public void setTopFlexible(boolean topFlexible){
        mFlexibleEffect.setTopFlexible(topFlexible);
    }
}
