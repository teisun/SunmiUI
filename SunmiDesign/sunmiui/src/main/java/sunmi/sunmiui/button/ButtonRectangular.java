package sunmi.sunmiui.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import sunmi.sunmiui.R;
import sunmi.sunmiui.TitleView;

/**
 * 直角button
 * Created by Administrator on 2016/6/16.
 */
public class ButtonRectangular extends TitleView {

    private Drawable bgEnabled;
    private Drawable bgDisenabled;

    private int colorEnabled;
    private int colorDisenabled;

    public ButtonRectangular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ButtonRectangular(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ButtonRectangular(Context context) {
        super(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.button_rectangular_9_16, this);
        colorEnabled = getResources().getColor(R.color.FF3C00);
        colorDisenabled = getResources().getColor(R.color.AEAEAE);
        bgEnabled = getResources().getDrawable(R.drawable.ripple_bg_item, null);
        bgDisenabled = getResources().getDrawable(R.color.EEEEEE, null);
        return view;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled){
            bg.setBackground(bgEnabled);
            textView.setTextColor(colorEnabled);
        }else{
            bg.setBackground(bgDisenabled);
            textView.setTextColor(colorDisenabled);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundEnabled(Drawable drawable){
        this.bgEnabled = drawable;
        if(isEnabled()){
            bg.setBackground(bgEnabled);
        }
    }

    public void setTextColorEnabled(int color){
        this.colorEnabled = color;
        if(isEnabled()){
            textView.setTextColor(colorEnabled);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundDisenabled(Drawable drawable){
        this.bgDisenabled = drawable;
        if(!isEnabled()){
            bg.setBackground(bgDisenabled);
        }
    }

    public void setTextColorDisennabled(int color){
        this.colorDisenabled = color;
        if(!isEnabled()){
            textView.setTextColor(colorDisenabled);
            RippleDrawable rd = null;
        }
    }

}
