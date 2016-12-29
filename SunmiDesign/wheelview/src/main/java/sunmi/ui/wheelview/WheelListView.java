package sunmi.ui.wheelview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;


/**
 * Created by longt on 2016/12/11.
 */

public class WheelListView extends ListView implements IWheelView {

    public static final String TAG = "WheelListView";

    /**
     * 平滑滚动持续时间
     */
    public static final int WHEEL_SMOOTH_SCROLL_DURATION = 50;

    /**
     * 滚轮滑动时展示选中项延迟时间
     */
    public static final int WHEEL_SCROLL_DELAY_DURATION = 300;

    /**
     * 滚轮滑动时展示选中项
     */
    public static final int WHEEL_SCROLL_HANDLER_WHAT = 0x0100;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHEEL_SCROLL_HANDLER_WHAT) {
                mWheelAdapter.notifyDataSetChanged();
                if (mOnWheelItemSelectedListener != null) {
                    mOnWheelItemSelectedListener.onItemSelected
                            (getCurrentPosition());
                }
            }
        }
    };

    private boolean mLoop = LOOP;   // 是否循环滚动

    private int mItemH = 0; // 每一项高度
    private OnWheelItemSelectedListener mOnWheelItemSelectedListener;
    private int mCurrentPositon;
    private BaseWheelAdapter mWheelAdapter;
    private int mWheelSize = 5;

    public WheelListView(Context context) {
        super(context);
        init();
    }

    public WheelListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WheelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WheelListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setVerticalScrollBarEnabled(false);
        setScrollingCacheEnabled(false);
        setCacheColorHint(Color.TRANSPARENT);
        setFadingEdgeLength(0);
        setOverScrollMode(OVER_SCROLL_NEVER);
        setDividerHeight(0);
//        setOnItemClickListener(mOnItemClickListener);
        super.setOnScrollListener(mOnScrollListener);
        addOnGlobalLayoutListener();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        super.setOnScrollListener(mOnScrollListener);
        mCustomerScrollListener = l;
    }

    private OnScrollListener mCustomerScrollListener;

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if(mCustomerScrollListener!=null){
                mCustomerScrollListener.onScrollStateChanged(view, scrollState);
            }
            if (scrollState == SCROLL_STATE_IDLE) {
                View itemView = getChildAt(0);
                if (itemView != null) {
                    float deltaY = itemView.getY();
                    if (deltaY == 0 || mItemH == 0) {
                        return;
                    }
                    if (Math.abs(deltaY) < mItemH / 2) {
                        int d = getSmoothDistance(deltaY);
                        smoothScrollBy(d, WHEEL_SMOOTH_SCROLL_DURATION);
                    } else {
                        int d = getSmoothDistance(mItemH + deltaY);
                        smoothScrollBy(d, WHEEL_SMOOTH_SCROLL_DURATION);
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int
                visibleItemCount, int totalItemCount) {
            if(mCustomerScrollListener!=null){
                mCustomerScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            if (visibleItemCount != 0) {
                refreshCurrentPosition();
            }
        }
    };

    /**
     * 刷新当前位置
     *
     */
    private void refreshCurrentPosition() {
        if (getChildAt(0) == null || mItemH == 0) {
            return;
        }
        int firstPosition = getFirstVisiblePosition();
        if (mLoop && firstPosition == 0) {
            return;
        }
        int position = 0;
        if (Math.abs(getChildAt(0).getY()) <= mItemH / 2) {
            position = firstPosition;
        } else {
            position = firstPosition + 1;
        }
        if (mLoop) {
//            Log.d(TAG, "refreshCurrentPosition: getChildCount:"+getChildCount()+" getChildCount()/2:"+(getChildCount()/2)
//            + " getWheelCount()"+mWheelAdapter.getWheelCount());
            position = (position + (mWheelSize/2))  % mWheelAdapter.getWheelCount();
        }

        if (position == mCurrentPositon) {
            return;
        }
        mCurrentPositon = position;
        mWheelAdapter.setCurrentPosition(position);
        mHandler.removeMessages(WHEEL_SCROLL_HANDLER_WHAT);
        mHandler.sendEmptyMessageDelayed(WHEEL_SCROLL_HANDLER_WHAT, WHEEL_SCROLL_DELAY_DURATION);
    }

    private void addOnGlobalLayoutListener() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                if (getChildCount() > 0 && mItemH == 0) {
                    mItemH = getChildAt(0).getHeight();
                    if (mItemH != 0) {
                        ViewGroup.LayoutParams params = getLayoutParams();
                        params.height = mItemH * mWheelSize;
                    } else {
                        throw new RuntimeException("wheel item is error.");
                    }
                }
            }
        });
    }

    /**
     * 平滑的滚动距离
     *
     * @param scrollDistance
     * @return
     */
    private int getSmoothDistance(float scrollDistance) {
        if (Math.abs(scrollDistance) <= 2) {
            return (int) scrollDistance;
        } else if (Math.abs(scrollDistance) < 12) {
            return scrollDistance > 0 ? 2 : -2;
        } else {
            return (int) (scrollDistance / 6);  // 减缓平滑滑动速率
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isEnabled()){
            return true;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    public void setOnWheelItemSelectedListener(OnWheelItemSelectedListener l) {
        this.mOnWheelItemSelectedListener = l;
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        if (adapter != null && adapter instanceof BaseWheelAdapter) {
            setWheelAdapter((BaseWheelAdapter) adapter);
        } else {
            throw new RuntimeException("please invoke setWheelAdapter " +
                    "method.");
        }
    }

    /**
     * 设置滚轮数据源适配器
     *
     * @param adapter
     */
    @Override
    public void setWheelAdapter(BaseWheelAdapter adapter) {
        super.setAdapter(adapter);
        mWheelAdapter = adapter;
        mWheelAdapter.setLoop(mLoop);
    }

    @Override
    public void setWheelSize(int wheelSize) {
        if ((wheelSize & 1) == 0) {
            throw new RuntimeException("wheel size must be an odd number.");
        }
        this.mWheelSize = wheelSize;
    }

    /**
     * 获取当前滚轮位置
     *
     * @return
     */
    public int getCurrentPosition() {
        return mCurrentPositon;
    }

    /**
     * 设置滚轮位置
     *
     * @param selection
     */
    @Override
    public void setSelection(final int selection) {
//        mSelection = selection;
        setVisibility(View.INVISIBLE);
        WheelListView.this.postDelayed(new Runnable() {
            @Override
            public void run() {
                WheelListView.super.setSelection(getRealPosition(selection));
                refreshCurrentPosition();
                setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    /**
     * 获得滚轮当前真实位置
     *
     * @param positon
     * @return
     */
    private int getRealPosition(int positon) {
        List data = mWheelAdapter.getData();
        if (mWheelAdapter.getData().isEmpty()) {
            return 0;
        }
        if (mLoop) {
            int d = Integer.MAX_VALUE / 2 / data.size();
            return positon + d * data.size() - getChildCount() / 2;
        }
        return positon;
    }

    public interface OnWheelItemSelectedListener {
        void onItemSelected(int position);
    }

}
