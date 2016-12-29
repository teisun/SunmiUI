package sunmi.sunmiui.window;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by xurong on 2016/11/2.
 */
public class VersionView {

    private Context mContext;
    private WindowManager windowManager;// 用于可拖动的浮动窗口
    private WindowManager.LayoutParams windowParams;// 浮动窗口的参数
    private TextView mText;
    //是否被添加过
    private boolean isAdded = false;

    public VersionView(Context mContext) {
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        mText = new TextView(mContext);
        mText.setText("release");
        mText.setTextColor(Color.parseColor("#ffffff"));
        mText.setWidth(80);
        mText.setHeight(50);
        mText.setGravity(Gravity.CENTER);
        mText.setBackgroundColor(Color.parseColor("#cc000000"));
        if (windowManager == null) {
            setWindowParams(0, 0);
            windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        }
    }

    /**
     * 显示
     */
    public void showInfo(String info) {
        mText.setText("" + info);
        if (!isAdded) {
            windowManager.addView(mText, windowParams);
            isAdded = true;
        }
    }

    /**
     * 隐藏
     */
    public void hide() {
        if (windowManager != null && mText != null && isAdded) {
            windowManager.removeView(mText);
            isAdded = false;
        }
    }

    private void setWindowParams(int x, int y) {
        // 建立item的缩略图
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;// 这个必须加
        // 得到preview左上角相对于屏幕的坐标
        windowParams.x = x;
        windowParams.y = y;
        // 设置宽和高
        windowParams.width = 80;
        windowParams.height = 50;
        windowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
    }
}
