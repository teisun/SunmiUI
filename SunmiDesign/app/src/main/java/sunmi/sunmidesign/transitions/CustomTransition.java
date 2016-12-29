package sunmi.sunmidesign.transitions;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import sunmi.sunmidesign.R;
import sunmi.sunmiui.utils.LogUtil;

/**
 * Created by KenMa on 2016/12/5.
 */
public class CustomTransition extends Transition {

    private static final String TAG = "CustomTransition";
    //可参考 http://blog.csdn.net/qibin0506/article/details/53248597
    private final String RECT = "rect";

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);
        transitionValues.values.put(RECT, rect);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect();
        view.getHitRect(rect);
        transitionValues.values.put(RECT, rect);
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues == null || endValues == null) return null;
        final View startView = startValues.view;
        final View endView = endValues.view;

        final Rect startRect = (Rect) startValues.values.get(RECT);
        final Rect endRect = (Rect) endValues.values.get(RECT);

        LogUtil.d(TAG,startRect.toString());
        LogUtil.d(TAG,endRect.toString());
        int centerX = (endRect.left + endRect.right) / 2;
        int centerY = (endRect.bottom + endRect.top) / 2;

        int startWidth = startRect.right - startRect.left;
        int startHeight = startRect.bottom - startRect.top;
        ViewCompat.setTranslationY(endView, startRect.top);
        ViewCompat.setTranslationX(endView, startRect.left);
        endView.getLayoutParams().height = startHeight;
        endView.getLayoutParams().width = startWidth;
        endView.requestLayout();
        endView.setBackgroundResource(R.drawable.circle_shape);

        ValueAnimator yAnimator = ValueAnimator.ofInt(startRect.top, centerY);

        yAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationY(endView, current);
            }

        });

        yAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                endView.setBackgroundColor(0xffff0000);
                ViewCompat.setTranslationY(endView, endRect.top);
                endView.getLayoutParams().height = endRect.bottom - endRect.top;
                endView.requestLayout();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ValueAnimator xAnimator = ValueAnimator.ofInt(startRect.left, centerX);
        xAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int current = (int) valueAnimator.getAnimatedValue();
                ViewCompat.setTranslationX(endView, current);
            }
        });
        xAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ViewCompat.setTranslationX(endView, endRect.left);
                endView.getLayoutParams().width = endRect.right - endRect.left;
                endView.requestLayout();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        float finalRadius = (float) Math.hypot((double) centerX, (double) centerY);
        float startRadius = (float) Math.hypot((double) startWidth, (double) startHeight);
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                endView, centerX, centerY, startRadius, finalRadius);


        AnimatorSet set = new AnimatorSet();
        set.play(xAnimator).with(yAnimator).before(mCircularReveal);

        return set;

    }
}
