package sunmi.sunmidesign.transitions;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;

import sunmi.sunmidesign.R;

public class CustomSharedTransitionActivity  extends Activity {

    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_transition);
        show = (TextView) findViewById(R.id.show);
        //定义ArcMotion
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(80f);
        arcMotion.setMinimumVerticalAngle(80f);
        Interpolator interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setInterpolator(interpolator);
        changeBounds.setDuration(1000);
        changeBounds.setPathMotion(arcMotion);
        //将切换动画应用到当前的Activity的进入和返回
        getWindow().setSharedElementEnterTransition(changeBounds);
        Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
        sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                performCircularReveal();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        getWindow().setSharedElementReturnTransition(changeBounds);
        Transition sharedElementReturnTransition = getWindow().getSharedElementReturnTransition();
        sharedElementReturnTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                show.setBackgroundResource(R.drawable.circle_shape);
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void performCircularReveal() {
        show.setBackgroundColor(0xffff0000);
        ViewCompat.setTranslationY(show, 0);
        ViewCompat.setTranslationX(show, 0);
        show.getLayoutParams().height = 500;
        show.getLayoutParams().width = 1920;
        show.requestLayout();
        int centerX = (show.getLeft() + show.getRight()) / 2;
        int centerY = (show.getTop() + show.getBottom()) / 2;
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                show, centerX, centerY, 0, finalRadius);
        mCircularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
        mCircularReveal.setDuration(500);
        mCircularReveal.start();
    }
}
