package sunmi.sunmiui.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import sunmi.sunmiui.R;
import sunmi.sunmiui.SettingItemView;

/**
 * Created by 47673 on 2016/7/8.
 */
public class EnterItem extends SettingItemView {
    public EnterItem(Context context) {
        super(context);
    }

    public EnterItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EnterItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.view_setting_9_16,this);
        return view;
    }
}
