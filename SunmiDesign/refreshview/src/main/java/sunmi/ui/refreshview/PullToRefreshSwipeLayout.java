package sunmi.ui.refreshview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * @author shenzhou.lee on 2016/7/14.
 */
public class PullToRefreshSwipeLayout extends SwipeRefreshLayout {

    private OnPullUpListener mOnPullUpListener;

    private ListView mListView;
    private View mFooterView;
    private RelativeLayout nodata_layout;
    private RelativeLayout loading_layout;

    /**
     * 按压点
     */
    private float mDownY;
    /**
     * 最后放手位置
     */
    private float mLastY;
    /**
     * 上下滑动距离
     */
    private float mTouchDistance = 50;
    /**
     * 是否正在刷新
     */
    private boolean isPullLoading = false;
    // 无状态
    public static final int NONE = 0x01;
    // 加载中
    public static final int LOADING = 0x02;
    // 没有下一页数据
    public static final int NODATA = 0x03;

    private OnSlideListener mOnSlideListener;

    public void setOnPullUpListener(OnPullUpListener listener) {
        mOnPullUpListener = listener;
    }

    public PullToRefreshSwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setColorSchemeResources(R.color.pbColor);
        mFooterView = View.inflate(getContext(), R.layout.item_footer, null);
        nodata_layout = (RelativeLayout) mFooterView.findViewById(R.id.nodata_layout);
        loading_layout = (RelativeLayout) mFooterView.findViewById(R.id.loading_layout);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    private void getListView() {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child instanceof ListView) {
                    mListView = (ListView) child;
                    // 设置滚动监听器给ListView, 使得滚动到底部可以自动加载
                    mListView.setOnScrollListener(onScrollListener);
                    break;
                }
            }
        }
    }

    /**
     * 设置加载状态
     *
     * @param status
     */
    public synchronized void setViewStatus(int status) {
        mDownY = 0;
        mLastY = 0;
        nodata_layout.setVisibility(View.GONE);
        loading_layout.setVisibility(View.VISIBLE);

        switch (status) {
            case NONE:
                isPullLoading = false;
                nodata_layout.setVisibility(View.VISIBLE);
                loading_layout.setVisibility(View.GONE);
                break;
            case LOADING:
                isPullLoading = true;
                removeFooterView();
                addFooterView();
                break;
            case NODATA:
                isPullLoading = false;
                removeFooterView();
                break;
        }
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mOnPullUpListener != null) {
                if (!isRefreshing() && isBottom() && isPullUp() && !isPullLoading) {
                    setViewStatus(LOADING);
                    mOnPullUpListener.onPullUp();
                }
            }
        }

    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mListView == null || mOnPullUpListener == null) {
            return super.dispatchTouchEvent(ev);
        }

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = ev.getY();
                if (mDownY > ev.getY()) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.up();
                    }
                } else if (mDownY < ev.getY()) {
                    if (mOnSlideListener != null) {
                        mOnSlideListener.down();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mLastY = ev.getY();
                if (!isRefreshing() && isBottom() && isPullUp() && !isPullLoading) {
                    setViewStatus(LOADING);
                    mOnPullUpListener.onPullUp();
                    break;
                }
                if (!canChildScrollUp() && isDropDown()) {
                    removeFooterView();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void addFooterView() {
        if (mFooterView != null && mListView != null) {
            mListView.addFooterView(mFooterView);
        }
    }

    private void removeFooterView() {
        if (mFooterView != null && mListView != null) {
            mListView.removeFooterView(mFooterView);
        }
    }

    /**
     * 是否到达底部
     */
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否为上拉操作
     */
    private boolean isPullUp() {
        return (mDownY - mLastY) >= mTouchDistance;
    }

    /**
     * 是否为下拉刷新
     *
     * @return
     */
    private boolean isDropDown() {
        return (mDownY - mLastY) <= -mTouchDistance;
    }

    /**
     * 上拉加载的监听器
     */
    public interface OnPullUpListener {
        void onPullUp();
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.mOnSlideListener = onSlideListener;
    }

    /**
     * 上拉下拉的监听器（非刷新的）
     */
    public interface OnSlideListener {
        void up();

        void down();
    }

}
