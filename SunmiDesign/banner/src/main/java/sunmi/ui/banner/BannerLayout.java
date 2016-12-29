package sunmi.ui.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tomcat on 2016/10/25.
 */
public class BannerLayout extends ViewGroup {
    private static final String TAG = "BannerLayout";
    private static final String TAG1 = "BannerLayout1";
    private static final String TAG2 = "BannerLayout2";

    private int mSize = 4; // size
    private int mInterval = 0; // Interval distance
    private int firstMarginLeft = 0;//first ImageView margin left

    private int mChildWidth = 1250; // child width
    private int mChildHeight = 340; // child height

    private int flipValue = mChildWidth/3;

    private Scroller mScroller;
    private float mLastScrollX;
    private EdgeEffect mEdgeGlowLeft;
    private EdgeEffect mEdgeGlowRight;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手指按下时view.getX();
     */
    private float mXDownView;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 用于判断手指滑动速度的工具类
     */
    private VelocityTracker mVelocityTracker;
    private final float mflipXVelocity = 1400;//达到此速度就翻页
    private float mLastXVelocity;

    private Looper looper;

    private OnPageShowListener mOnPageShowListener;
    private OnItemClickListener mOnItemClickListener;

    public BannerLayout(Context context) {
        super(context);
        init(null, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {
        LogUtil.setLevel(LogUtil.NOTHING);

        setWillNotDraw(false);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BannerLayout, defStyle, 0);

        mSize = a.getInt(
                    R.styleable.BannerLayout_size,
                    mSize);

        mChildWidth = a.getInt(
                R.styleable.BannerLayout_childWidth,
                mChildWidth);

        mChildHeight = a.getInt(
                R.styleable.BannerLayout_childHeight,
                mChildHeight);

        flipValue = mChildWidth/3;

        a.recycle();

        looper = new Looper(this);
        mScroller = new Scroller(getContext());
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        // 获取TouchSlop值
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        invalidateParams();

    }

    void invalidateParams(){
        stop();
        removeAllViews();
        looper.clear();
        for(int i=0;i<mSize;i++){
            RoundedImageView view = new RoundedImageView(getContext());
            if(i!=0){
                view.setBackgroundResource(R.drawable.drop_shadow);
                view.setPadding(0,0,15,0);
            }
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setCornerRadius((float) 10);
            LayoutParams params = new LayoutParams(mChildWidth, mChildHeight);
            view.setLayoutParams(params);
            addView(view);
            Banner banner = new Banner();
            banner.view = view;
            banner.position = i;
            looper.add(banner);
            view.setTag(i);
            view.setOnClickListener(viewClickListener);
        }
        LogUtil.d(TAG, "invalidateParams");

    }

    OnClickListener viewClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer position = (Integer) view.getTag();
            List<Banner> bannerList = looper.bannerList;
            Banner b = looper.get(position);
            LogUtil.d(TAG, "onClick pos:"+b.position);
            if(looper.bannerClick(b)){
                LogUtil.d(TAG, "onItemClick pos:"+b.position);
                if(mOnItemClickListener!=null)
                    mOnItemClickListener.onItemClick(view, b.position);
            }
        }
    };

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for(int i=0;i<getChildCount();i++){
            ImageView child = (ImageView) getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    //stop all animation
    private void abortAnimation(){
        looper.stop();
        mScroller.abortAnimation();
        removeCallbacks(changeRunningMan);
        removeCallbacks(reStart);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        acquireVelocityTracker(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "onInterceptTouchEvent ACTION_DOWN");
                Banner banner = looper.runningMan;
                if(banner!=null){
                    View view = banner.view;
                    mXDownView = view.getX();
                    LogUtil.d(TAG1, "ACTION_DOWN: getX"+ view.getX()+" view.getTranslationX"+view.getTranslationX()+" getScrollX:"+view.getScrollX());
                }
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                //停止动画
                abortAnimation();

                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(TAG, "onInterceptTouchEvent ACTION_MOVE");
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    LogUtil.d(TAG, "diff > mTouchSlop diff:"+diff+" mTouchSlop:"+mTouchSlop);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG, "onInterceptTouchEvent ACTION_UP");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mSize == 1){
            LogUtil.d(TAG, "onTouchEvent mSize == 1");
            return true;
        }
        acquireVelocityTracker(event);
        Banner banner = looper.runningMan;
        View bannerView = banner.view;
        LogUtil.d(TAG, "onTouchEvent runningMan pos:"+banner.position);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d(TAG, "onTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtil.d(TAG, "onTouchEvent ACTION_MOVE");
                if (actionMove(event, banner)) return true;
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d(TAG, "onTouchEvent ACTION_UP");
                actionUp(banner);

                break;
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                releaseEdgeGlow();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        LogUtil.d(TAG1, "computeScroll");
        if(looper.runningMan == null)
            return;
        Banner banner = looper.runningMan;
        View bannerView = banner.view;
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            LogUtil.d(TAG1, "computeScrollOffset mScroller.getCurrX():"+mScroller.getCurrX()+" banner.exitEndX:"+banner.exitEndX);
            bannerView.setX(mScroller.getCurrX());
            invalidate();
            LogUtil.d(TAG1, "computeScrollOffset bannerView.getX():"+bannerView.getX()+" bannerView.getTranslationX():"+bannerView.getTranslationX());
            removeCallbacks(changeRunningMan);
            postDelayed(changeRunningMan, 100);
            removeCallbacks(reStart);
            if(banner.isEntered()||banner.isExited()){
                postDelayed(reStart, Banner.DELAY);
            }else {
                postDelayed(reStart, 100);
            }
            edgeGlowOnAbsorb();
        }
    }

    private void edgeGlowOnAbsorb(){
        int oldX = getScrollX();
        int x = mScroller.getCurrX();
        int y = mScroller.getCurrY();

        if (oldX != x) {
            final int range = getScrollRange();
            final int overscrollMode = getOverScrollMode();
            final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                    (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);

            if (canOverscroll) {
                if (x < 0 && oldX >= 0) {
                    mEdgeGlowLeft.onAbsorb((int) mScroller.getCurrVelocity());
                } else if (x > range && oldX <= range) {
                    mEdgeGlowRight.onAbsorb((int) mScroller.getCurrVelocity());
                }
            }
        }

        if (!awakenScrollBars()) {
           postInvalidate();
        }
    }

    Runnable changeRunningMan = new Runnable() {
        @Override
        public void run() {
            LogUtil.d(TAG1, "pre changeRunningManOnScrollFinished mLastScrollX:"+mLastScrollX+" mChildWidth/2="+mChildWidth/2);
            if(mLastScrollX > flipValue || mLastXVelocity > mflipXVelocity){
                notifyOnPageShow();
                LogUtil.d(TAG1, "changeRunningManOnScrollFinished run");
                looper.changeRunningManOnScrollFinished();
            }
        }

    };

    /**
     * 通知展示的页面切换
     */
    protected synchronized void notifyOnPageShow() {
        if(mOnPageShowListener != null){
            mOnPageShowListener.onPageShow(getShowingPosition());
        }
    }


    Runnable reStart = new Runnable() {
        @Override
        public void run() {
            looper.start();
        }
    };

    private boolean actionMove(MotionEvent event, Banner banner) {
        View bannerView = banner.view;
        mXMove = event.getRawX();
        int scrolledX = (int) (mXLastMove - mXMove);
        Banner bannerNew = looper.changeRunningManByScrollDirection(scrolledX);
        if(banner.position!=bannerNew.position){
            banner = bannerNew;
            bannerView = banner.view;
            mXDownView = bannerView.getX();
        }

        LogUtil.d(TAG, "onTouchEvent scrolledX:"+scrolledX + " bannerView.getScrollX():"+bannerView.getScrollX()+" banner.exitEndX:"+banner.exitEndX+" banner.enterEndX:"+banner.enterEndX + " bannerView.getWidth():"+bannerView.getWidth());
        LogUtil.d(TAG, "onTouchEvent bannerView.getTranslationX():"+bannerView.getTranslationX()+" bannerView.getX():"+bannerView.getX());
        if (bannerView.getX()-scrolledX < banner.exitEndX) { //左滑到底
            bannerView.setX(banner.exitEndX);
            edgeGlowOnPull(event);
            return true;
        } else if (bannerView.getX()-scrolledX > banner.enterEndX ) { //右滑到底
            bannerView.setX(banner.enterEndX);
            edgeGlowOnPull(event);
            return true;
        }

        LogUtil.d(TAG, "滑动 bannerView.offsetLeftAndRight:"+-scrolledX);
        bannerView.offsetLeftAndRight(-scrolledX);
        LogUtil.d(TAG1,  " bannerView.getScrollX():"+bannerView.getScrollX()+" bannerView.getTranslationX():"+bannerView.getTranslationX()+" bannerView.getX():"+bannerView.getX()+" bannerView.getLeft():"+bannerView.getLeft());

        mXLastMove = mXMove;
        return false;
    }

    private void actionUp(Banner banner) {
        View bannerView = banner.view;
        //设置单位，1000 表示每秒多少像素（pix/second),1代表每微秒多少像素（pix/millisecond)。
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        LogUtil.d(TAG2, "the x velocity is "+mVelocityTracker.getXVelocity());

        // 当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
        int dx = 0;
        float scrollX = (int) (mXLastMove - mXDown);
        float absScrollX = Math.abs(scrollX);
        int flipValue = this.flipValue;

        if(mXDownView == banner.enterEndX || State.Entering.equals(banner.getState())){
            //Entered or Entering
            if(absScrollX>=flipValue || xVelocity< -mflipXVelocity){
                dx = (int) (banner.exitEndX-bannerView.getX());
                banner.setState(State.Exiting);
            }else if(absScrollX < flipValue){
                dx = (int) (banner.enterEndX-bannerView.getX());
                banner.setState(State.Entering);
            }
        }else if(mXDownView == banner.exitEndX || State.Exiting.equals(banner.getState()) ) {
            //Exited or Exiting
            if(absScrollX>=flipValue || xVelocity> mflipXVelocity){
                dx = (int) (banner.enterEndX-bannerView.getX());
                banner.setState(State.Entering);
            }else if(absScrollX<flipValue){
                dx = (int) (banner.exitEndX-bannerView.getX());
                banner.setState(State.Exiting);
            }
        }

        mLastScrollX = absScrollX;
        mLastXVelocity = Math.abs(xVelocity);
        LogUtil.d(TAG, "ACTION_UP dx:"+dx + " bannerView.getScrollX():"+bannerView.getScrollX()+" bannerView.getTranslationX():"+bannerView.getTranslationX()+" bannerView.getX():"+bannerView.getX());
        // 第二步，调用startScroll()方法来初始化滚动数据并刷新界面
        mScroller.startScroll((int) bannerView.getX(), 0, dx, 0);
        invalidate();
        releaseVelocityTracker();

        releaseEdgeGlow();
    }

    private void releaseEdgeGlow() {
        if (mEdgeGlowLeft != null) {
            mEdgeGlowLeft.onRelease();
            mEdgeGlowRight.onRelease();
        }
    }

    /**
     *
     * @param event 向VelocityTracker添加MotionEvent
     *
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if(null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 释放VelocityTracker
     */
    private void releaseVelocityTracker() {
        if(null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }


    public List<ImageView> getAllImageView(){
        List<ImageView> list = new ArrayList<>();
        for(int i=0;i<getChildCount();i++){
            ImageView child = (ImageView) getChildAt(i);
            list.add(child);
        }
        return list;
    }

    //----------用户函数-----------
    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
        invalidateParams();
    }


    public int getInterval() {
        return mInterval;
    }

    public int getChildWidth() {
        return mChildWidth;
    }

    public void setChildWidth(int width) {
        mChildWidth = width;
        invalidateParams();
    }

    public int getChildHeight() {
        return mChildHeight;
    }

    public void setChildHeight(int height) {
        mChildHeight = height;
        invalidateParams();
    }

    public synchronized int getShowingPosition(){
        Banner showingBanner = looper.getShowingBanner();
        if(showingBanner==null)return getChildCount()-1;
        return showingBanner.position;
    }


    public void stop(){
        if(mSize == 1)return;
        abortAnimation();
    }

    public void start(){
        if(mSize == 1)return;
        //此方法只调用一次，其原理是将自定义的runnable放入到消息队列的尾部，当looper调用到它时，view已经初始化完成了
        post(new Runnable() {
            @Override
            public void run() {
                looper.start();
            }
        });
    }

    public void setBannerDisplayDuration(int duration){
        Banner.DELAY = duration;
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnPageShowListener(OnPageShowListener listener){
        this.mOnPageShowListener = listener;
    }

    public interface OnPageShowListener{
        void onPageShow(int position);
    }

    //----------用户函数-----------end


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if( MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.UNSPECIFIED ){
            throw new RuntimeException("BannerLayout: child MeasureSpec mode undefined!");
        }

        //得到ViewGroup的初始宽高
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //
        heightMeasureSpec =  MeasureSpec.makeMeasureSpec(mChildHeight, MeasureSpec.EXACTLY);
        //算出间隔
        firstMarginLeft = width - mChildWidth;
        if(mSize-1 > 0){
            mInterval = (width - mChildWidth) / (mSize-1);
        }else if(mSize-1 == 0){
            mInterval = 0;
            firstMarginLeft = 0;
            View child = getChildAt(0);
            if(child!=null){
                LayoutParams lp = child.getLayoutParams();
                lp.width = width;
                lp.height = mChildHeight;
                child.setLayoutParams(lp);
                int wSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
                int hSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
                child.measure(wSpec, hSpec);
            }
        }

        if(mSize > 1){
            final int count = getChildCount();
            //计算每一个子View的尺寸,并算出ViewGroup的高度
            for (int i = 0; i < count; i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    final LayoutParams lp = child.getLayoutParams();
                    //算出子View宽的MeasureSpec值
                    int wSpec = MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY);
                    //算出子View高的MeasureSpec值
                    int hSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
                    //函数传入的就是这里算出来的这两个值
                    child.measure(wSpec, hSpec);
                }
            }
        }


        //设置ViewGroup宽高值
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtil.d(TAG, "onLayout");
        final int count = getChildCount();
        int xpos = getPaddingLeft() + firstMarginLeft;
        int ypos = getPaddingTop();
        if(count == 1){
            View child = getChildAt(0);
            child.layout(xpos, ypos, xpos + getWidth(), getHeight() );
            Banner banner = looper.get(0);
            banner.enterEndX = xpos;
            banner.exitEndX = xpos;
            return;
        }
        //设置每一个子View的位置,左上角xy坐标与右下角xy坐标确定View的位置
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                child.layout(xpos, ypos, xpos + mChildWidth, ypos + mChildHeight );
                Banner banner = looper.get(i);
                banner.enterEndX = xpos;
                banner.exitEndX = xpos - child.getWidth() + mInterval + Banner.TRIM;
                if(i == 0){
                    banner.exitEndX = xpos;
                    banner.nextEnter = looper.get(1);
                    banner.nextExit = looper.get(getChildCount()-1);
                }
                LogUtil.d(TAG, "child position:"+banner.position + " banner.enterEndX:"+banner.enterEndX+" banner.exitEndX:"+banner.exitEndX);
                xpos -= mInterval;
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

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void edgeGlowOnPull(MotionEvent ev) {
        Log.d(TAG, "edgeGlowOnPull");
        int mActivePointerId = ev.getPointerId(0);
        final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
        if (activePointerIndex == -1) {
            LogUtil.e(TAG, "Invalid pointerId=" + mActivePointerId + " in onTouchEvent");
            return;
        }

        final int x = (int) ev.getX(activePointerIndex);
        int deltaX = (int) (mXLastMove - mXMove);
        final int oldX = getScrollX();
        final int oldY = (int) ev.getY();
        final int range = getScrollRange();
        final int overscrollMode = getOverScrollMode();
        final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);
        if (canOverscroll) {
            final int pulledToX = oldX + deltaX;
            if (pulledToX < 0) {
//                mEdgeGlowLeft.onPull((float) deltaX / getWidth());
                mEdgeGlowLeft.onPull((float) deltaX / getWidth(),
                        1.f - ev.getY(activePointerIndex) / getHeight());
                if (!mEdgeGlowRight.isFinished()) {
                    mEdgeGlowRight.onRelease();
                }
            } else if (pulledToX > range) {
//                mEdgeGlowRight.onPull((float) deltaX / getWidth());
                mEdgeGlowRight.onPull((float) deltaX / getWidth(),
                        ev.getY(activePointerIndex) / getHeight());
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
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);

//        canvas.rotate(10);
//        canvas.translate(getWidth()/3, 50);
//        canvas.rotate(270);
        // 创建画笔
//        Paint p = new Paint();
//        p.setColor(Color.RED);// 设置红色
//
//        canvas.drawText("画圆：", 10, 20, p);// 画文本
//        canvas.drawCircle(60, 20, 10, p);// 小圆
//        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了
//        canvas.drawCircle(120, 20, 20, p);// 大圆

        if (mEdgeGlowLeft != null) {
//            final int scrollX = mScrollX;
            final int scrollX = getScrollX();
            if (!mEdgeGlowLeft.isFinished()) {
                final int restoreCount = canvas.save();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

                canvas.rotate(270);
                canvas.translate(-height + getPaddingTop(), Math.min(0, scrollX));
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

                canvas.rotate(90);
                canvas.translate(-getPaddingTop(),
                        -(Math.max(getScrollRange(), scrollX) + width));
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
}
