package sunmi.sunmiui.title;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import sunmi.sunmiui.R;
import sunmi.sunmiui.TitleView;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CategoryTitle extends TitleView {


    public CategoryTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public CategoryTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CategoryTitle(Context context) {
        super(context);
    }

    @Override
    protected View createView() {
        View view = View.inflate(getContext(), R.layout.title_category_9_16, this);
        return view;
    }

}
