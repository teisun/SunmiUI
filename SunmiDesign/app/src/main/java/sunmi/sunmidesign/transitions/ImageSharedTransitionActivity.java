package sunmi.sunmidesign.transitions;

import android.animation.Animator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import sunmi.sunmidesign.R;

public class ImageSharedTransitionActivity extends Activity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        Slide slideIn = new Slide();
        slideIn.setDuration(500);
        //设置为进入
        slideIn.setMode(Visibility.MODE_IN);
        //设置从右边进入
        slideIn.setSlideEdge(Gravity.RIGHT);
        getWindow().setEnterTransition(new ChangeImageTransform().setDuration(3000));
//        getWindow().setEnterTransition(new Explode().setDuration(2000));
//        getWindow().setExitTransition(new Explode().setDuration(2000));
        Slide slideReturn =new Slide();
        slideReturn.setDuration(500);
        slideReturn.setSlideEdge(Gravity.LEFT);
        slideReturn.setMode(Visibility.MODE_OUT);
        getWindow().setReturnTransition (slideReturn );
        setContentView(R.layout.activity_img_transition);
        mImageView = (ImageView) findViewById(R.id.img);

//        Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();

    }

    private void performCircularReveal(View show) {
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
