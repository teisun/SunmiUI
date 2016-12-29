package sunmi.sunmiui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/16.
 */
public abstract class TitleView extends FrameLayout {

    protected TextView textView;
    protected View bg;

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleView(Context context) {
        super(context);
        init();
    }

    void init() {
        View view = createView();
        textView = (TextView) view.findViewById(R.id.text);
        bg = view.findViewById(R.id.bg);

    }

    protected abstract View createView();

    public void setTitleText(String titleStr) {
        textView.setText(titleStr);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackground(Drawable drawable) {
        bg.setBackground(drawable);
    }

    public void setBackgroundColor(int color) {
        bg.setBackgroundColor(color);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setTextSize(float size) {
        textView.setTextSize(size);
    }

    public void setWidth(int width) {
        ViewGroup.LayoutParams layoutParams = bg.getLayoutParams();
        layoutParams.width = width;
        bg.setLayoutParams(layoutParams);
    }

    public void setHeight(int height) {
        ViewGroup.LayoutParams layoutParams = bg.getLayoutParams();
        layoutParams.height = height;
        bg.setLayoutParams(layoutParams);
    }


    /**
     * 设置文字位置
     *
     * @param verb RelativeLayout.CENTER_HORIZONTAL 水平居中；RelativeLayout.CENTER_IN_PARENT 居中于父容器；RelativeLayout.CENTER_VERTICAL 垂直居中；
     */

    public void addTitleRule(int verb) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(verb);
        textView.setLayoutParams(lp);
    }
}
