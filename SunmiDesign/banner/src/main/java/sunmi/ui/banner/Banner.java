package sunmi.ui.banner;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.widget.ImageView;


/**
 * Created by tomcat on 2016/10/25.
 */
class Banner {

    private final static String TAG = "Banner";

    final static int DURATION = 1000;
    static int DELAY = DURATION *2;
    final static int TRIM = 35;

    int position;
    int enterEndX = 0;
    int exitEndX;

    Looper looper;

    Banner nextExit;
    Banner nextEnter;

    ObjectAnimator animator = new ObjectAnimator();
    ImageView view;

    private State state ;

    public Banner(){

    }

    public void exit(int delay, final boolean notify){
        animator.cancel();
//        exitEndX = -view.getWidth() + interval + TRIM;
        animator = ObjectAnimator.ofFloat(view, "x", exitEndX)
                .setDuration(DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                setState(State.Exiting);
                if(notify)stateChange(State.Exiting);
                LogUtil.d(TAG, "exit AnimationStart: getX"+ view.getX()+" view.getTranslationX"+ view.getTranslationX()+" getScrollX:"+ view.getScrollX()+" pos:"+position);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setState(State.Exited);
                if(notify)stateChange(State.Exited);
                LogUtil.d(TAG, "exit AnimationEnd: getX"+ view.getX()+" view.getTranslationX"+ view.getTranslationX()+" getScrollX:"+ view.getScrollX()+" pos:"+position);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                LogUtil.d(TAG, "exit anim cancel position:"+position);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setStartDelay(delay);
        setState(State.PreExit);
        if(notify)stateChange(State.PreExit);
        animator.start();
    }

    public void enter( int delay, final boolean notify){
        animator.cancel();
        animator = ObjectAnimator.ofFloat(view, "x",  enterEndX)
                .setDuration(DURATION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                setState(State.Entering);
                if(notify)
                    stateChange(State.Entering);

                LogUtil.d(TAG, "enter AnimationStart: getX"+ view.getX()+" view.getTranslationX"+ view.getTranslationX()+" getScrollX:"+ view.getScrollX()+" pos:"+position);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setState(State.Entered);
                if(notify)
                    stateChange(State.Entered);

                LogUtil.d(TAG, "enter AnimationEnd: getX"+ view.getX()+" view.getTranslationX"+ view.getTranslationX()+" getScrollX:"+ view.getScrollX()+" pos:"+position);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                LogUtil.d(TAG, "enter anim cancel position:"+position);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.setStartDelay(delay);
        setState(State.PreEnter);
        if(notify) stateChange(State.PreEnter);
        animator.start();
    }

    public void cancelAnim(){
        animator.cancel();
    }

    void stateChange(State state){
        if(looper.getAnimCancel())
                return;
        synchronized (this){
//            this.state = state;
            looper.changeRunningManAuto(Banner.this, state);
        }
    }

    public State getState(){
        synchronized (this){
            return state;
        }
    }

    public void setState(State state){
        synchronized (this){
            this.state = state;
        }
    }

    public boolean isExited(){
        return view.getX() == exitEndX;
    }

    public boolean isEntered(){
        return view.getX() == enterEndX;
    }

    public boolean isOrigin(){
        return position == 0;
    }

    public boolean isEnterHead(){
        return nextExit == null;
    }

    public boolean isExitHead(){
        return nextEnter == null;
    }

}
