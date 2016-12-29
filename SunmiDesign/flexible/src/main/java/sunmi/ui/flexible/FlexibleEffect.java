package sunmi.ui.flexible;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;


/**
 * Created by longtao.li on 2016/11/29.
 */
public class FlexibleEffect {

    private static final String TAG = "FlexibleEffect";

    private static final int STATE_IDLE = 0;
    private static final int STATE_PULL = 1;
    private static final int STATE_ABSORB = 2; //吸附状态

    private View view;
    private Flexible flexibleView;

    private int mState = STATE_IDLE;

    private  float maxScaleY = 1.5f;
    private final float minScaleY = 1f;
    private float currentScaleY = 1f;

    ObjectAnimator absorbAnimator = new ObjectAnimator();
    ObjectAnimator flingAnimator = new ObjectAnimator();
    private long mDuration;

    // Minimum velocity that will be absorbed
    private static final int MIN_VELOCITY = 100;
    // Maximum velocity, clamps at this value
    private static final int MAX_VELOCITY = 10000;

    private float resistance = 5f;//滑动时受到的阻力

    private static final long MAX_DURATION = 300l;

    private boolean topFlexible = true;

    public FlexibleEffect(View view){
        this.view = view;
        flexibleView = (Flexible) view;
    }

    public void setMaxScaleY(int scaleY){
        this.maxScaleY = scaleY;
    }

    public boolean isFinished(){
        return mState == STATE_IDLE;
    }

    public boolean isPulling(){
        return mState == STATE_PULL;
    }

    public boolean isAbsorbing(){
        return mState == STATE_ABSORB;
    }

    public void finish() {
        absorbAnimator.cancel();
        mState = STATE_IDLE;
    }

    /**
     * 设置滑动时的阻力
     * @param resistance
     */
    public void setResistance(float resistance){
        if(resistance<=0){
            resistance = 1;
        }
        this.resistance = resistance;
    }

    public synchronized void onPull(float scaleYIncrement) {
        scaleYIncrement = scaleYIncrement/resistance;//模拟阻力
        Log.d(TAG, "onPull scaleYIncrement:"+scaleYIncrement);
        if(flexibleView.isTop()){
            if(!topFlexible){
                return;
            }
            currentScaleY -=  scaleYIncrement;
            view.setPivotY(0);

        }else if(flexibleView.isBottom()){
            currentScaleY +=  scaleYIncrement;
            view.setPivotY(view.getHeight());
        }
        if(currentScaleY>maxScaleY){
            currentScaleY = maxScaleY;
        }else if(currentScaleY<minScaleY){
            currentScaleY = minScaleY;
        }

//        LogUtil.d(TAG, "currentScaleY="+currentScaleY+" minScaleY="+minScaleY);
        if(currentScaleY<=maxScaleY&&currentScaleY>=minScaleY) {
            mState = STATE_PULL;
            view.setScaleY(currentScaleY);
            if(currentScaleY == minScaleY){
                onRelease();
            }
        }

    }

    public void onRelease() {
        mState = STATE_IDLE;
        currentScaleY = minScaleY;
    }

    public void onAbsorb(long duration) {
        if(flexibleView.isTop()&&!topFlexible){
            return;
        }
        absorbAnimator.cancel();
        mState = STATE_ABSORB;
        if(duration == -1){

            mDuration = (long) (currentScaleY / maxScaleY * MAX_DURATION);
            Log.d(TAG, "onAbsorb1 mDuration:"+ mDuration);
        }else {
            Log.d(TAG, "onAbsorb2");
            mDuration = duration;
        }
        absorbAnimator = ObjectAnimator.ofFloat(view, "scaleY", minScaleY)
                .setDuration(mDuration);

        absorbAnimator.setInterpolator(new OvershootInterpolator());

        absorbAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onRelease();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        absorbAnimator.start();
    }


    public void onFling(float velocity) {
        Log.d(TAG, "onFling velocity:"+velocity+" "+!isFinished()+" "+!flexibleView.isBottom()+" "+!flexibleView.isTop());
        if(!isFinished()||(!flexibleView.isBottom()&&!flexibleView.isTop()))return;
        if(flexibleView.isTop()){
            if(!topFlexible){
                return;
            }
            view.setPivotY(0);
        }else if(flexibleView.isBottom()){
            view.setPivotY(view.getHeight());
        }
        velocity = Math.min(Math.max(MIN_VELOCITY, Math.abs(velocity)), MAX_VELOCITY);
        flingAnimator.cancel();
        mState = STATE_PULL;
        mDuration = (long) (20 + (velocity * 0.02));
        float scaleY = ((float)(mDuration) / (float)MAX_DURATION)*(maxScaleY-minScaleY);
        scaleY = scaleY*0.2f;
        Log.d(TAG, "onFling mDuration="+mDuration+" scaleY:"+scaleY);
        flingAnimator = ObjectAnimator.ofFloat(view, "scaleY", minScaleY+scaleY)
                .setDuration(mDuration);

        flingAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onAbsorb(MAX_DURATION);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        flingAnimator.start();
    }

    public void setTopFlexible(boolean topFlexible) {
        this.topFlexible = topFlexible;
    }
}
