package sunmi.ui.wheelview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class WheelItemLayout extends RelativeLayout {
    private int h = 0;
    private float maxScale = 1f;
    public WheelItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
    }


    public void setParentHeight(int height) {
        h = height;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int top = getTop();
        float scale = calcuylateScale(top, h);
        super.dispatchDraw(canvas);
        setScaleX(scale);
        setScaleY(scale);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private float calcuylateScale(int top, int h) {
        float result = 0f;
//        result = (1f - 1f/2f* Math.abs((top - h / 2f)) / (h / 2f)) * maxScale;
        int height = getHeight();
        result = (1f - 1f/2f* Math.abs((top + height/2 - h / 2f)) / (h / 2f)) * maxScale;
        return result;
    }

}
