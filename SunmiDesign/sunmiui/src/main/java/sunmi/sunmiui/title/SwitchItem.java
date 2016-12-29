package sunmi.sunmiui.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;

import sunmi.sunmiui.R;
import sunmi.sunmiui.SettingItemView;
import sunmi.sunmiui.button.ButtonSwitch;

/**
 * Created by 47673 on 2016/7/7.
 */
public class SwitchItem extends SettingItemView{

    protected ButtonSwitch buttonSwitch;
    public SwitchItem(Context context) {
        super(context);
    }

    public SwitchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.view_switch_9_16, this);
        buttonSwitch = (ButtonSwitch) view.findViewById(R.id.set_switch);
        return view;
    }





    /**
     * 为buttonSwitch设置OnCheckedChangeListener
     * @param changeListener OnCheckChangeListener
     */
    public void setOncheckChangeListener(CompoundButton.OnCheckedChangeListener changeListener){
        buttonSwitch.setOnCheckedChangeListener(changeListener);
    }

    /**
     * 返回ButtonSwitch是否选中
     * @return
     */
    public boolean isChecked(){
        return buttonSwitch.isChecked();
    }

    /**
     * 设置ButtonSwitch是否选中
     * @param isCheck
     */
    public void setChecked(boolean isCheck){
        buttonSwitch.setOnCheck(isCheck);
    }


}
