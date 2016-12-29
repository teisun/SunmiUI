package sunmi.sunmiui.button;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import sunmi.sunmiui.R;

/**
 * Created by 47673 on 2016/7/6.
 */
public class ButtonSwitch extends FrameLayout{
    protected Resources resources;
    protected Switch mSwitch;
    public ButtonSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    public ButtonSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ButtonSwitch(Context context) {
        super(context);
        init();
    }

    protected View createView() {
        View view = View.inflate(getContext(), R.layout.button_switch_9_16, null);

        return view;
    }


    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
        mSwitch.setOnCheckedChangeListener(listener);
    }
    public void setOnCheck(boolean isCheck){
        mSwitch.setChecked(isCheck);
    }

    public boolean isChecked(){
        return mSwitch.isChecked();
    }

    public void setWidth(int width){
        ViewGroup.LayoutParams layoutParams = mSwitch.getLayoutParams();
        layoutParams.width = width;
        mSwitch.setLayoutParams(layoutParams);
    }

    public void setHeight(int height){
        ViewGroup.LayoutParams layoutParams = mSwitch.getLayoutParams();
        layoutParams.height = height;
        mSwitch.setLayoutParams(layoutParams);
    }

    void init(){
        resources = getResources();
        View view = createView();
        this.addView(view);
        mSwitch = (Switch) view.findViewById(R.id.set_switch);
    }

    public void setOnTouch(OnTouchListener onTouchListener){
        mSwitch.setOnTouchListener(onTouchListener);
    }
}
