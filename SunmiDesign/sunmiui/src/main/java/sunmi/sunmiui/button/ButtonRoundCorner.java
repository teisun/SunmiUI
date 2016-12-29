package sunmi.sunmiui.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import sunmi.sunmiui.R;
import sunmi.sunmiui.TitleView;

/**
 * 圆角button
 * Created by Administrator on 2016/6/16.
 */
public class ButtonRoundCorner extends TitleView {

    public ButtonRoundCorner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ButtonRoundCorner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ButtonRoundCorner(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.button_rounded_corrner_9_16, this);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundColor(int colorPressed, int colorNormal){
        StateListDrawable sd = new StateListDrawable();
        int[] state_pressed = new int[]{android.R.attr.state_pressed};
        int[] state_normal = new int[]{android.R.attr.state_enabled};

        GradientDrawable pressed = new GradientDrawable();
        pressed.setColor(colorPressed);
        pressed.setCornerRadius(10);

        GradientDrawable enable = new GradientDrawable();
        enable.setColor(colorNormal);
        enable.setCornerRadius(10);

        sd.addState(state_pressed, pressed);
        sd.addState(state_normal, enable);
        bg.setBackground(sd);
    }

    /**
     * invalid
     * @param drawable
     */
    @Deprecated
    public void setBackground(Drawable drawable) {
    }



}
