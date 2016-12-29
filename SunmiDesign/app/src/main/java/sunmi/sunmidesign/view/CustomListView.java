package sunmi.sunmidesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by longx on 2016/11/15.
 */

public class CustomListView extends ListView {

    private OnSlideListener mOnSlideListener;
    private static final int DISTANCE = 50;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float downY; // 按下时y轴的偏移量

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.d("lxy", "ACTION_DOWN");
                downY = ev.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d("lxy", "ACTION_MOVE");
                if(downY-ev.getY()>DISTANCE){
                    Log.d("lxy", "↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
                    if (mOnSlideListener != null) {
                        mOnSlideListener.up();
                    }
                }else if(ev.getY()-downY>DISTANCE){
                    Log.d("lxy", "↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
                    if (mOnSlideListener != null) {
                        mOnSlideListener.down();
                    }
                }
                break;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.mOnSlideListener = onSlideListener;
    }

    public interface OnSlideListener {
        void up();

        void down();
    }
}
